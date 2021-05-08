package com.xxl.service.impl;

import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.*;
import com.xxl.config.MaigaShareNacosConfiguration;
import com.xxl.entity.*;
import com.xxl.entity.po.*;
import com.xxl.entity.vo.*;
import com.xxl.mapper.master.WAccountMapper;
import com.xxl.mapper.read.RAccountMapper;
import com.xxl.service.IIptvService;
import com.xxl.service.IMagiaService;
import com.xxl.service.RedisService;
import com.xxl.util.HttpRequestUtil;
import com.xxl.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("iptvService")
public class IptvServiceImpl implements IIptvService {

    private GeoipUtil geoipUtil = new GeoipUtil();

    @Autowired
    private MaigaShareNacosConfiguration maigaShareNacosConfiguration;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    RedisService redisService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public R authenticateService(String username, String password) {
        R result = R.ok();
        try {
            // 1、这里的userName是userId，这里的password是other_pwd
            // 先根据userId去数据库查询到关联的用户信息
            Account account = redisService.accountSelectByUserIdFromRedis(username);
            if (null == account) {
                return null;
            }

            // 如果用户是存在的，那么需要判断密码是否正确
            if (!password.equalsIgnoreCase(account.getOtherPwd())) {
                return null;
            }
            String exp_date = CommonConsts.EMPTY_STR;
            Date authInvalidTime = account.getAuthInvalidTime();
            // 获取到用户的过期时间，如果是免费模式，统一设置为30天后，否则设置为
            String appModel = maigaShareNacosConfiguration.getAppModel();
            if (!CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                // 如果是收费模式且授权到期时间是存在的，则需要做一个转换
                if (null != authInvalidTime) {
                    exp_date = Long.toString(authInvalidTime.getTime());
                }
            }
            // 如果是免费模式，那么统一将这个时间设置为当前时间的后30天
            else {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 30);
                exp_date = Long.toString(calendar.getTime().getTime());
            }

            // 创建时间的时间戳
            String created_at = CommonConsts.EMPTY_STR;
            created_at = Long.toString(account.getCreateTime().getTime());

            // 允许的视频格式
            String allowedOutputFormats = maigaShareNacosConfiguration.getAllowedOutputFormats();
            String[] allowedOutputFormatsList = allowedOutputFormats.split(CommonConsts.COMMA);

            // 如果用户存在，而且密码是正确的，那么需要返回用户信息了
            IptvUserInfoRsp userInfoRsp = IptvUserInfoRsp.builder().username(username).password(password).message("").auth(CommonConsts.NUMBER_ONE)
                    .status(CommonConsts.ACTIVE_STR).exp_date(exp_date).is_trial(CommonConsts.NUMBER_ONE_STR).active_cons(CommonConsts.NUMBER_ZERO_STR)
                    .created_at(created_at).max_connections(CommonConsts.NUMBER_ONE_STR).allowed_output_formats(allowedOutputFormatsList)
                    .build();

            // 获取配置参数信息
            String iptvUrl = maigaShareNacosConfiguration.getIptvUrl();
            String iptvPort = maigaShareNacosConfiguration.getIptvPort();
            String iptvHttpsPort = maigaShareNacosConfiguration.getIptvHttpsPort();
            String iptvServerProtocol = maigaShareNacosConfiguration.getIptvServerProtocol();
            String iptvRtmpPort = maigaShareNacosConfiguration.getIptvRtmpPort();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time_now = df.format(new Date());

            // 还需要组装server_info
            IptvServerInfoRsp iptvServerInfoRsp = IptvServerInfoRsp.builder().url(iptvUrl).port(iptvPort).https_port(iptvHttpsPort)
                    .server_protocol(iptvServerProtocol).rtmp_port(iptvRtmpPort).timezone("America Sao_Paulo").timestamp_now(new Date().getTime())
                    .time_now(time_now).process(true).build();

            // 设置最终返回结果
            IptvLoginRsp loginRsp = IptvLoginRsp.builder().user_info(userInfoRsp).server_info(iptvServerInfoRsp).build();
            result.setData(loginRsp);

        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public Object commonAuthenticateService(String username, String password) {
        // 标识开始时间
        String returnStr = null;
        try {
            // 1、这里的userName是userId，这里的password是other_pwd
            // 先根据userId去数据库查询到关联的用户信息
            Account account = redisService.accountSelectByUserIdFromRedis(username);
            // 找到iptv对应的错误码或者http状态码
            if (null == account) {
                log.error(CodeMessage.USER_NOT_EXITS + username);
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }

            // 如果用户是存在的，那么需要判断密码是否正确
            // 找到iptv对应的错误码或者http状态码
            if (!password.equalsIgnoreCase(account.getOtherPwd())) {
                log.error(CodeMessage.USER_NAMEORPWD_ERROR + username);
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }

            // 判断当前的模式和判断用户的授权是否是存在的
            String appModel = maigaShareNacosConfiguration.getAppModel();
            // 如果不是免费模式，那么需要判断授权是否存在
            if (!CommonConsts.FREE_STR.equalsIgnoreCase(appModel)) {
                Date authInvalidTime = account.getAuthInvalidTime();
                if (null == authInvalidTime || new Date().after(authInvalidTime)) {
                    log.error(CodeMessage.AUTH_EXPIRED + username);
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
            }


        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return returnStr;
    }

    @Override
    public R getLiveCategoriesService(String username, String password) {
        R result = R.ok();
        try {
            List<LiveCategory> liveStreamsList = mongoTemplate.findAll(LiveCategory.class);
            result.setData(liveStreamsList);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R getVodCategoriesService(String username, String password) {
        R result = R.ok();
        try {
            List<VodCategory> vodCategoryList = mongoTemplate.findAll(VodCategory.class);
            result.setData(vodCategoryList);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R getLiveStreamService(String username, String password, String category_id) {
        R result = R.ok();
        try {
            List<LiveStreams> liveStreamsList = mongoTemplate.find(new Query(Criteria.where(CommonConsts.CATEGORY_ID_STR).is(category_id)), LiveStreams.class);
            result.setData(liveStreamsList);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }

    @Override
    public R getVodStreamService(String username, String password, String category_id) {
        R result = R.ok();
        try {
            List<VodStreams> vodStreams = mongoTemplate.find(new Query(Criteria.where(CommonConsts.CATEGORY_ID_STR).is(category_id)), VodStreams.class);
            result.setData(vodStreams);
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
            result = R.error();
        }
        return result;
    }
}
