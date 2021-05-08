package com.xxl.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
/**
 *
 * @ClassName:  SenderConttoller
 * @Description:验证发送消息
 * @author: niugang
 * @date:   2018年11月3日 上午9:58:19
 * @Copyright: 863263957@qq.com. All rights reserved.
 *
 */

@RestController
public class SenderConttoller {
    private Logger logger = LoggerFactory.getLogger(SenderConttoller.class);

    @Autowired
    private KafkaTemplate<String, String> template;


    /**
     * 同步发送
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("syncSendMessage")
    public String syncSendMessage() {
        for (int i = 0; i < 2; i++) {
            try {
                /*template.send("JavaProduct", 0,"片区1"+i, "片区1的新测试消息:" + i).get();
                template.send("JavaProduct", 1,"片区2"+i, "片区2的新测试消息:" + i).get();
                template.send("JavaProduct", 2,"片区3"+i, "片区3的新测试消息:" + i).get();*/
                template.send("JavaTopicACK", 0,"片区1"+i, "片区1:新测试消息:" + i).get();
                //template.send("JavaTopicTest", 1,"片区2"+i, "片区2:新测试消息:" + i).get();
                //template.send("JavaTopicTest", 2,"片区3"+i, "片区3:新测试消息:" + i).get();
            } catch (InterruptedException e) {
                logger.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            } catch (ExecutionException e) {
                logger.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            }
        }

        return "success";
    }

    /**
     * 异步发送
     *
     * @return
     */
    @RequestMapping("asyncSendMessage")
    public String sendMessageAsync() {
        for (int i = 0; i < 100; i++) {
            /**
             * <p>
             * SendResult:如果消息成功写入kafka就会返回一个RecordMetaData对象;result.
             * getRecordMetadata() 他包含主题信息和分区信息，以及集成在分区里的偏移量。
             * 查看RecordMetaData属性字段就知道了
             * </p>
             *
             */
            ListenableFuture<SendResult<String, String>> send = template.send("JavaTopic", "0", "foo" + i);
            send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("async send message success partition [{}]", result.getRecordMetadata().partition());
                    logger.info("async send message success offest[{}]", result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("async send message fail [{}]", ex.getMessage());

                }
            });
        }
        return "success";
    }
}