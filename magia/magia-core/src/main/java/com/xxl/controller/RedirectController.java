package com.xxl.controller;

import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.HttpUtil;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.config.MaigaShareNacosConfiguration;
import com.xxl.entity.Account;
import com.xxl.entity.po.GetUserInfoRsp;
import com.xxl.entity.po.LoginRsp;
import com.xxl.entity.po.PlayUrlRsp;
import com.xxl.entity.vo.*;
import com.xxl.service.IMagiaService;
import com.xxl.service.RedisService;
import com.xxl.util.DateUtil;
import com.xxl.util.HttpRequestUtil;
import com.xxl.util.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public class RedirectController {

    @Autowired
    RedisService redisService;

    @Autowired
    IMagiaService magiaService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MaigaShareNacosConfiguration maigaShareNacosConfiguration;


    /**
     * @param requestSourceType 资源类型
     * @param requestUserId     用户的userId
     * @param requestPassword   用户的密码
     * @param requestResourceId 需要播放的资源
     * @return 用户请求示例如下所示：http://127.0.0.1:8080/live/81034/123456/p1_curta_hd_264、http://127.0.0.1:8080/vod/81034/123456/7BB5690310A24865A7220589DF83DA9E
     */
    @RequestMapping(value = "/{requestSourceType}/{requestUserId}/{requestPassword}/{requestResourceId}", method = RequestMethod.GET)
    public Object RedirectCon(@PathVariable @NotBlank String requestSourceType, @PathVariable @NotBlank String requestUserId, @PathVariable @NotBlank String requestPassword, @PathVariable @NotBlank String requestResourceId) {
        // 标识开始时间
        String returnStr = null;
        try {
            String sourceType = requestSourceType;
            String userId = requestUserId;
            String password = requestPassword;
            // 根据和cdn人员的确定，这里的sourceId就是mediaCode
            String sourceId = requestResourceId;

            // 首先只有这两种资源类型的时候才做处理
            if (CommonConsts.LIVE_STR.equalsIgnoreCase(requestSourceType) || CommonConsts.VOD_STR.equalsIgnoreCase(requestSourceType)) {
                // 对账号和密码做校验
                Account account = redisService.accountSelectByUserIdFromRedis(userId);
                // 先判断用户是否是存在的
                if (null == account) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // 如果用户是存在的，那需要判断用户的密码是否正确
                String otherPwd = account.getOtherPwd();
                // 如果两个密码是不相同的，那么返回
                if (!password.equalsIgnoreCase(otherPwd)) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                //  这里还需要判断当前是否是免费模式，如果是免费模式，不用判断授权，如果不是免费模式，那么需要判断用户授权时间
                String appModel = maigaShareNacosConfiguration.getAppModel();
                if (!CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                    // 如果这个不是免费模式，那么就需要判断用户的授权是否大于当前时间
                    Date authInvalidTime = account.getAuthInvalidTime();
                    // 如果授权时间为空，或者当前时间大于授权到期时间，则说明授权已到期
                    if (null == authInvalidTime || new Date().after(authInvalidTime)) {
                        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                    }
                }

                // 如果都符合了要求，那么需要往CDN发送请求去获取播放连接了
                String slbPlayUrl = maigaShareNacosConfiguration.getSlbPlayUrl();
                if (StringUtil.isEmpty(slbPlayUrl)) {
                    log.error("未配置properties.slbPlayUrl的地址信息，请配置后重试");
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // 组装过期时间，这里设置为默认为4个小时后
                long expired = System.currentTimeMillis() / 1000 + maigaShareNacosConfiguration.getExpiresMinut() * 60;

                // 开始发送http请求
                PlayUrlReq playUrlReq = PlayUrlReq.builder().userId(userId).mediaCode(sourceId).business(sourceType).expires(expired).build();
                String data = HttpRequestUtil.getInstance().executePostByStream(slbPlayUrl, JsonUtil.writeObject2JSON(playUrlReq));
                // 如果返回的為空，相當於異常
                if (StringUtil.isEmpty(data)) {
                    log.error("slb请求异常");
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // 如果请求返回是正常的，那么需要组装数据
                PlayUrlRsp result = (PlayUrlRsp) JsonUtil.writeJSON2Object(data, PlayUrlRsp.class);
                String playUrl = result.getPlayUrl();
                returnStr = CommonConsts.REDIRECT_STR + playUrl;
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return returnStr;
    }

}
