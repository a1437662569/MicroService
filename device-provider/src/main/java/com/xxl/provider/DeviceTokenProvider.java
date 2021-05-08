package com.xxl.provider;

        import com.alibaba.fastjson.JSON;
        import com.xxl.common.api.R;
        import com.xxl.common.enums.CommonConsts;
        import com.xxl.common.enums.RedisEnum;
        import com.xxl.common.enums.URIConsts;
        import com.xxl.common.util.JsonUtil;
        import com.xxl.common.util.LogUtils;
        import com.xxl.common.util.ParamUtil;
        import com.xxl.common.util.StringUtil;
        import com.xxl.common.vo.DeviceTokenVO;
        import com.xxl.model.DeviceTokenModel;
        import com.xxl.service.DeviceTokenAPI;
        import com.xxl.service.DeviceTokenService;
        import lombok.extern.slf4j.Slf4j;
        import org.apache.dubbo.config.annotation.DubboService;
        import org.springframework.beans.BeanUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.data.redis.core.RedisTemplate;
        import org.springframework.web.bind.annotation.RestController;

        import javax.servlet.http.HttpServletRequest;
        import java.util.concurrent.ThreadLocalRandom;
        import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@DubboService
public class DeviceTokenProvider implements DeviceTokenAPI {
    @Autowired
    DeviceTokenService deviceTokenService;
    @Qualifier("redisTemplate1")
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    private HttpServletRequest request;

    @Override
    public R<DeviceTokenVO> getOne(String userId) {
        long start = System.currentTimeMillis();
        R r = R.ok();
        try {
            log.info("请求参数:{}", userId);
            String key = String.format(RedisEnum.DEVICE_DEVICE_TOKEN_$USERID_STR.getKey(), userId);
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtil.isNotEmpty(value)) {
                DeviceTokenVO deviceTokenVO = (DeviceTokenVO) JsonUtil.writeJSON2Object(value, DeviceTokenVO.class);
                r =  new R<DeviceTokenVO>().ok(deviceTokenVO);
            } else {
                r =  deviceTokenService.getOneByUserId(userId);
            }
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR, StringUtil.exception2JSON(e));
            return R.error();
        }
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_DEVICE + "getOne",
                    userId, ParamUtil.writeObject2JSON(r));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return r;
    }

    @Override
    public R updateOrInsert(DeviceTokenVO deviceTokenVO) {
        long start = System.currentTimeMillis();
        String key = String.format(RedisEnum.DEVICE_DEVICE_TOKEN_$USERID_STR.getKey(), deviceTokenVO.getUserId());
        R r = R.ok();
        try {
            log.info("请求参数:{}", JSON.toJSONString(deviceTokenVO));
            boolean hasKey = redisTemplate.hasKey(key);
            boolean compareTrue = false;
            if (hasKey) {
                String redisValue = redisTemplate.opsForValue().get(key);
                DeviceTokenVO redisVO = (DeviceTokenVO) JsonUtil.writeJSON2Object(redisValue, DeviceTokenVO.class);
                if (StringUtil.equals(redisVO.getDeviceToken(), deviceTokenVO.getDeviceToken())) {
                    log.info("UserId:{},Token一致,redisToken值:{},参数token值:{}", deviceTokenVO.getUserId(), redisVO.getDeviceToken(), deviceTokenVO.getDeviceToken());
                    compareTrue = true;
                }
            }
            //如果Redis中不存在 / 存在且token不一致,更新RedisToken并台异步写入数据库
            if (!hasKey || !compareTrue) {
                int expire = ThreadLocalRandom.current().nextInt(7, 15);
                String value = JsonUtil.writeObject2JSON(deviceTokenVO);
                DeviceTokenModel model = new DeviceTokenModel();
                BeanUtils.copyProperties(deviceTokenVO, model);
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.DAYS);
                redisTemplate.opsForList().leftPush(RedisEnum.DEVICE_DEVICE_TOKEN_ASYNC_2DB_LIST.getKey(), JsonUtil.writeObject2JSON(deviceTokenVO));
                log.info("UserId:{},token值:{},已缓存到Redis并写入异步队列", deviceTokenVO.getUserId(),  deviceTokenVO.getDeviceToken());
            }
        } catch (Exception e) {
            log.error(CommonConsts.LOG_ERROR_STR, StringUtil.exception2JSON(e));
            r =  R.error();
        }
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, URIConsts.API_DEVICE + "updateOrInsert",
                    JsonUtil.writeObject2JSON(deviceTokenVO), ParamUtil.writeObject2JSON(r));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return r;
    }
}