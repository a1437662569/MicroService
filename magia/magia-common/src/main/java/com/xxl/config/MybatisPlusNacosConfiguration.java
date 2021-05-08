package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Data
public class MybatisPlusNacosConfiguration {


    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis-plus.configuration.log-impl:}")
    private String logImpl;

    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private Boolean mapUnderscore2CamelCase;

    @Value("${mybatis-plus.configuration.call-setters-on-nulls}")
    private Boolean callSettersOnNulls;

    @Value("${mybatis-plus.global-config.banner}")
    private Boolean banner;

    @Value("${mybatis-plus.global-config.db-config.id-type}")
    private String idType;
}
