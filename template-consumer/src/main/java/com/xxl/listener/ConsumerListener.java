package com.xxl.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @ClassName: ConsumerListener
 * @Description:消费者监听
 * @author: niugang
 * @date: 2018年10月21日 下午2:05:21
 * @Copyright: 863263957@qq.com. All rights reserved.
 */
@Component
public class ConsumerListener {
    private Logger logger = LoggerFactory.getLogger(ConsumerListener.class);
/*

    @KafkaListener(id = "1", groupId = "JavaGroup", topics = "JavaProduct")
    public void listen1(String key,String foo) throws Exception {
        logger.info("消息1,key:[{}],内容:[{}]", key,foo);
    }
*/


    //批量消息
    @KafkaListener(id = "12", groupId = "JavaGroup", topics = "JavaTopicACK", containerFactory = "batchFactory")
    public void consumerBatch(List<ConsumerRecord> records, Acknowledgment ack) {
        logger.info("接收到消息数量：{}", records.size());     //手动提交
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