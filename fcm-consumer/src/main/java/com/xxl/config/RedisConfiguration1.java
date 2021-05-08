package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 1、由于项目中需要使用到redis，所以需要导入redis
 * 2、由于项目集成了nacos，所以会从nacos的配置中心去获取配置信息
 *
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
@Service
public class RedisConfiguration1 {

    @Value("${spring.redis.host:}")
    private String host;
    @Value("${spring.redis.port:}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.timeout:}")
    private int timeout;
    @Value("${spring.redis.database:}")
    private int database;

    //pool映射
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;

}