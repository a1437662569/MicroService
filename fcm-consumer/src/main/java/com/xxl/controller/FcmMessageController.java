package com.xxl.controller;


import com.alibaba.fastjson.JSON;
import com.xxl.common.api.R;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.enums.URIConsts;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.LogUtils;
import com.xxl.common.util.ParamUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.common.vo.FcmMessageInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(URIConsts.API_FCM)
public class FcmMessageController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * Fcm-consumer的作用：
     * 1、对外提供接口，当有消息推送的时候，外部网元调用该接口
     * 2、fcm先将消息写入到redis的队列中
     * 3、fcm本地开启一个线程，去消费redis队列中的数据，获取到数据中的userId，然后调用devicetoken-provider，获取到deviceToken
     * 4、然后将devicetoken拼装好了以后，写入到对应的kafka的topic中去。
     *
     * @param fcmMessageInfoVO 请求参数
     * @return result
     */
    @PostMapping(value = "addMessage", produces = "application/json;charset=UTF-8")
    public R<String> addBoxMsg(@Validated @RequestBody FcmMessageInfoVO fcmMessageInfoVO) {
        String requestURI = request.getRequestURI();
        long start = System.currentTimeMillis();
        R result = R.error();
        try {
            LogUtils.getPlatformLogger().info("请求参数信息为" + JSON.toJSONString(fcmMessageInfoVO));
            // 使用leftPush的方式
            Long aLong = redisTemplate.boundListOps(RedisEnum.FCM_MESSAGE_INFO.getKey()).leftPush(JSON.toJSONString(fcmMessageInfoVO));
            LogUtils.getPlatformLogger().info("请求参数信息为" + JSON.toJSONString(fcmMessageInfoVO) + "，将数据添加到redis中的结果为" + aLong);
            result = R.ok();
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(CommonConsts.LOG_ERROR_STR, StringUtil.exception2JSON(e));
        }
        // 记录返回信息
        try {
            LogUtils.getBussinessLogger().info(CommonConsts.LOG_FORMAT, ParamUtil.getIpAddr(request),
                    System.currentTimeMillis() - start, requestURI,
                    JsonUtil.writeObject2JSON(fcmMessageInfoVO), ParamUtil.writeObject2JSON(result));
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        return result;
    }
}
