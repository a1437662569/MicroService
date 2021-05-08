package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedisConfiguration2 {

    @Value("${spring.redis2.host:}")
    private String host;
    @Value("${spring.redis2.port:}")
    private int port;
    @Value("${spring.redis2.password:}")
    private String password;
    @Value("${spring.redis2.timeout:}")
    private int timeout;
    @Value("${spring.redis2.database:}")
    private int database;

    //pool映射
    @Value("${spring.redis2.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis2.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis2.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis2.lettuce.pool.max-wait}")
    private long maxWait;

}
