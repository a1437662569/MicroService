package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@ConfigurationProperties(prefix="spring.redis")
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
