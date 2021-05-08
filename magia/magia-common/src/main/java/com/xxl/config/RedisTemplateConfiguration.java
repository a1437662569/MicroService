package com.xxl.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 这个配置的意义是什么？
 * 看代码是想给RedisConfiguration1、RedisConfiguration2增加一些额外的处理
 * 使用redis的连接池吗？
 * <p>
 * 个人想法：
 * 因为我们在配置文件中引入了redis和redis2的两个配置信息，而为了性能方面的考虑，需要使用到redis连接池。
 * 而下面是对着两个连接分别创建了连接池。
 * 从项目启动后，查看redis的连接数可知，确实启动了连接池的功能。
 */

@Configuration
public class RedisTemplateConfiguration {
    @Autowired
    RedisConfiguration1 primaryConfig;
//    @Autowired
//    RedisConfiguration2 redisConfig2;

    /**
     * spring.redis.cluster.max-redirects= # 集群从节点转发的数量.
     * spring.redis.cluster.nodes= # 集群节点，逗号分隔.
     * spring.redis.database=0 # 使用的缓存索引编号.
     * spring.redis.url= # 连接URL，如: redis://user:password@example.com:6379 spring.redis.host=localhost # 主机.
     * <p>
     * spring.redis.jedis.pool.max-active=8 # 连接池的最大活动连接数量.
     * spring.redis.jedis.pool.max-idle=8 # 连接池的最大空闲连接数量.
     * spring.redis.jedis.pool.max-wait=-1ms # 连接池分配连接的等待时间.
     * spring.redis.jedis.pool.min-idle=0 # 最小空闲连接数量.
     * <p>
     * spring.redis.lettuce.pool.max-active=8 # 连接池最大活动连接数量.
     * spring.redis.lettuce.pool.max-idle=8 # 连接池最大空闲连接数量，负数表示不限制.
     * spring.redis.lettuce.pool.max-wait=-1ms # 连接池分配连接的最大等待时间，负数表示无限等待不超时.
     * spring.redis.lettuce.pool.min-idle=0 # 连接池最小空闲连接数量. spring.redis.lettuce.shutdown-timeout=100ms # 关机超时时间.
     * <p>
     * spring.redis.password= # Redis服务器的密码
     * spring.redis.port=6379 # Redis服务器端口.
     * <p>
     * spring.redis.sentinel.master= # Redis主服务器地址.
     * spring.redis.sentinel.nodes= # 逗号分隔的键值对形式的服务器列表.
     * <p>
     * spring.redis.ssl=false # 是否启用SSL连接.
     * spring.redis.timeout= # 连接超时
     *
     * @return
     */
    @Bean
    public RedisStandaloneConfiguration primaryRedisConfig() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(primaryConfig.getHost(), primaryConfig.getPort());
        redisConfig.setPassword(RedisPassword.of(primaryConfig.getPassword()));
        redisConfig.setDatabase(primaryConfig.getDatabase());
        return redisConfig;
    }

    @Bean(value = "primaryPool")
    //@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig primaryPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(primaryConfig.getMaxIdle());
        config.setMaxIdle(primaryConfig.getMaxIdle());
        config.setMaxTotal(primaryConfig.getMaxActive());
        config.setMaxWaitMillis(primaryConfig.getMaxWait());
        return config;
    }

    @Bean("primaryFactory")
    @Primary
    public LettuceConnectionFactory primaryFactory() {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .clientName("PrimaryLectureFactory")
                .commandTimeout(Duration.ofMillis(primaryConfig.getTimeout()))
                .poolConfig(primaryPoolConfig()).build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(primaryRedisConfig(), clientConfiguration);
        return lettuceConnectionFactory;
    }

    @Bean("redisTemplate1")
    @Primary
    public RedisTemplate<String, String> redisTemplate(@Qualifier("primaryFactory") RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }


//    @Bean
//    public RedisStandaloneConfiguration redisConfig2() {
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisConfig2.getHost(), redisConfig2.getPort());
//        redisConfig.setPassword(RedisPassword.of(redisConfig2.getPassword()));
//        redisConfig.setDatabase(redisConfig2.getDatabase());
//        return redisConfig;
//    }

//    @Bean
//    public GenericObjectPoolConfig poolConfig2() {
//        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        config.setMinIdle(redisConfig2.getMaxIdle());
//        config.setMaxIdle(redisConfig2.getMaxIdle());
//        config.setMaxTotal(redisConfig2.getMaxActive());
//        config.setMaxWaitMillis(redisConfig2.getMaxWait());
//        return config;
//    }

//    @Bean("lectureFactory2")
//    public LettuceConnectionFactory factory2() {
//        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
//                .clientName("lectureFactory2")
//                .commandTimeout(Duration.ofMillis(redisConfig2.getTimeout()))
//                .poolConfig(primaryPoolConfig()).build();
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfig2(), clientConfiguration);
//        return lettuceConnectionFactory;
//    }

//    @Bean("redisTemplate2")
//    public RedisTemplate<String, String> redisTemplate2(@Qualifier("lectureFactory2") RedisConnectionFactory factory) {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        template.setConnectionFactory(factory);
//        template.setKeySerializer(redisSerializer);
//        template.setValueSerializer(redisSerializer);
//        template.setHashValueSerializer(redisSerializer);
//        template.setHashKeySerializer(redisSerializer);
//        return template;
//    }
}
