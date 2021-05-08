package com.xxl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;


import java.util.stream.Collectors;

@EnableDiscoveryClient
// 主程序注解
@SpringBootApplication
@EnableDubbo
public class FcmConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(FcmConsumerApplication.class, args);
    }


    /**
     * 服务调用者：
     * Openfeign接口404
     * No qualifying bean of type 'org.springframework.boot.autoconfigure.http.HttpMessageConverters'
     */

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

}
