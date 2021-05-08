package com.xxl;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
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
@SpringBootApplication
@EnableDubbo
@MapperScan("com.xxl.mapper")
public class TemplateProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateProviderApplication.class, args);
    }

    /**
     * MybatisPlus:驼峰转下划线,适用于 mapper.selectMaps(queryWrapper)
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return i -> i.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }

    /**
     * 服务调用者：
     *     Openfeign接口404
     *     No qualifying bean of type 'org.springframework.boot.autoconfigure.http.HttpMessageConverters'
     */

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

}
