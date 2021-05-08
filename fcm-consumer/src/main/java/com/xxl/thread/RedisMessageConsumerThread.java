package com.xxl.thread;

import com.alibaba.fastjson.JSONObject;
import com.xxl.common.api.R;
import com.xxl.common.enums.KafkaTopicEnum;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.LogUtils;
import com.xxl.common.util.ParamUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.common.vo.DeviceTokenVO;
import com.xxl.service.DeviceTokenAPI;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;

import org.springframework.kafka.core.KafkaTemplate;

@Component
public class RedisMessageConsumerThread implements Runnable {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> template;

    @DubboReference
    DeviceTokenAPI deviceTokenAPI;


    @PostConstruct
    public void init() {
        //启动线程实例
        new Thread(this).start();
    }

    @Override
    public void run() {
        synchronized (this) {
            LogUtils.getPlatformLogger().info("后台消费list名为fcmMessageInfo中数据的线程启动了。");
            while (true) {
                try {
                    String messageInfo = (String) redisTemplate.boundListOps(RedisEnum.FCM_MESSAGE_INFO.getKey()).rightPop(Duration.ofSeconds(1));
                    LogUtils.getPlatformLogger().info("从fcmMessageInfo中取出来的数据为:" + messageInfo);
                    if (!StringUtil.isEmpty(messageInfo)) {
                        // 获取到消息
                        JSONObject fcmMessageInfo = JSONObject.parseObject(messageInfo);
                        // 从消息中获取到topicName
                        String topicName = (String) fcmMessageInfo.get("topicName");
                        R<DeviceTokenVO> deviceTokenResult = deviceTokenAPI.getOne(fcmMessageInfo.getString("userId"));
                        if (ParamUtil.isNotNullOK(deviceTokenResult)) {
                            DeviceTokenVO deviceTokenVO = deviceTokenResult.getData();
                            String deviceToken = deviceTokenVO.getDeviceToken();
                            String appId = deviceTokenVO.getAppId();
                            fcmMessageInfo.put("deviceToken", deviceToken);
                            fcmMessageInfo.put("appId", appId);

                            // 因为如果是返现的时候，paycore无法获取到用户的appId，所以传递过来的topicName需要由fcm来做一下特殊处理
                            if (KafkaTopicEnum.CRASHBACK_$APPID.getTopic().equals(topicName)) {
//                                String afterTopicName = "crashback_" + appId;
                                String afterTopicName = String.format(KafkaTopicEnum.CRASHBACK_$APPID.getTopic(),appId);
                                LogUtils.getPlatformLogger().info("当前请求的topic为" + topicName + ",特殊处理后的topicName为" + afterTopicName);
                                topicName = afterTopicName;
                            }
                            // 将fcmMessageInfo转换为字符串，然后发送给kafka
                            String kafkaValue = JsonUtil.writeObject2JSON(fcmMessageInfo);
                            LogUtils.getPlatformLogger().info("发送给kafka的topicName为" + topicName + ",发送的消息为:" + kafkaValue);
                            template.send(topicName, kafkaValue);
                        } else {
                            LogUtils.getPlatformLogger().info("从device-provider获取不到该userId的deviceToken,跳过" + JsonUtil.writeObject2JSON(deviceTokenResult));
                        }
                    } else {
                        LogUtils.getPlatformLogger().info("fcmMessageInfo中的数据为空，不处理。");
                    }
                } catch (Exception e) {
                    LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
                }
            }
        }
    }
}
