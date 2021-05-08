package com.xxl.listener;

import com.xxl.common.enums.CommonConsts;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.LogUtils;
import com.xxl.common.util.StringUtil;
import com.xxl.model.BoxMessageInfo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 监听kafka集群中topic为message_box，获取到数据后，将数据插入到mongodb中
 */
@Component
public class ConsumerListener {
    private Logger logger = LoggerFactory.getLogger(ConsumerListener.class);

    @Resource
    private MongoTemplate mongoTemplate;


    //批量消息
    @KafkaListener(id = "12", groupId = "JavaGroup", topics = CommonConsts.MESSAGE_BOX_STR, containerFactory = "batchFactory")
    public void consumerBatch(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        try {
            LogUtils.getPlatformLogger().info("从kafka的topic：message_box中获取到的消息的数量为"+ records.size());
            List<BoxMessageInfo> boxMessageInfoList = new ArrayList<>();
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();
                LogUtils.getPlatformLogger().info("接受到的消息为:" + value);
                BoxMessageInfo boxMessageInfo = (BoxMessageInfo) JsonUtil.writeJSON2Object(value, BoxMessageInfo.class);
                boxMessageInfoList.add(boxMessageInfo);
            }
            Collection<BoxMessageInfo> boxMessageInfos = mongoTemplate.insertAll(boxMessageInfoList);
            LogUtils.getPlatformLogger().info("插入mongodb数据库成功，数量为:" + boxMessageInfos.size());
        } catch (Exception e) {
            LogUtils.getExceptionLogger().error(StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }
        ack.acknowledge();
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory;
        factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(10);
        factory.getContainerProperties().setPollTimeout(1500);
        //设置为批量消费，每个批次数量在Kafka配置参数中设置    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);//设置手动提交ackMode　　return factory; }
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}