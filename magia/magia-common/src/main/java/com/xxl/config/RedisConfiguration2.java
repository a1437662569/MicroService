package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 引入了配置文件中redis2中的配置信息，为什么要配置一个redis2暂时不知
 *
 *     @Autowired
 *     @Qualifier("redisTemplate2")
 *     RedisTemplate redisTemplateLong;
 *     应该是架构上面的考虑，所以需要注入两个redis，我的项目暂时不需要，所以先将这个redisTemplate2注释掉了。
 *     根据用法，如果开启后需要使用到这个redis，只需要使用上面的注入方式即可获取到redisTemplate
 *
 *
 *
 */
//@Data
//@Component
//@Service
public class RedisConfiguration2 {

//    @Value("${spring.redis2.host:}")
//    private String host;
//    @Value("${spring.redis2.port:}")
//    private int port;
//    @Value("${spring.redis2.password:}")
//    private String password;
//    @Value("${spring.redis2.timeout:}")
//    private int timeout;
//    @Value("${spring.redis2.database:}")
//    private int database;
//
//    //pool映射
//    @Value("${spring.redis2.lettuce.pool.max-active}")
//    private int maxActive;
//    @Value("${spring.redis2.lettuce.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis2.lettuce.pool.min-idle}")
//    private int minIdle;
//    @Value("${spring.redis2.lettuce.pool.max-wait}")
//    private long maxWait;

}
