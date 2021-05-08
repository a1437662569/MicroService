package com.xxl.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Configuration
public class RedisTemplateConfiguration {
    @Autowired
    RedisConfiguration1 redisConfig1;
    @Autowired
    RedisConfiguration2 redisConfig2;


    /**
     * 配置lettuce连接池
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(redisConfig1.getMaxIdle());
        config.setMaxIdle(redisConfig1.getMaxIdle());
        config.setMaxTotal(redisConfig1.getMaxActive());
        config.setMaxWaitMillis(redisConfig1.getMaxWait());
        return config;
        //return new GenericObjectPoolConfig<>();
    }

    /**
     * 配置第一个数据源的
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisStandaloneConfiguration redisConfig() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisConfig1.getHost(),redisConfig1.getPort());
        redisConfig.setPassword(RedisPassword.of(redisConfig1.getPassword()));
        redisConfig.setDatabase(redisConfig1.getDatabase());
        return redisConfig;
    }

    /**
     * 配置第二个数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis2")
    public RedisStandaloneConfiguration redisConfig2() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisConfig2.getHost(),redisConfig2.getPort());
        redisConfig.setPassword(RedisPassword.of(redisConfig2.getPassword()));
        redisConfig.setDatabase(redisConfig2.getDatabase());
        return redisConfig;
    }

    /**
     * 配置第一个数据源的连接工厂
     * 这里注意：需要添加@Primary 指定bean的名称，目的是为了创建两个不同名称的LettuceConnectionFactory
     *
     * @param config
     * @param redisConfig
     * @return
     */
    @Bean("redisConfig1Factory")
    @Primary
    public LettuceConnectionFactory factory(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }

    @Bean("redisConfig2Factory")
    public LettuceConnectionFactory factory2(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfig2) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig2, clientConfiguration);
    }

    /**
     * 配置第一个数据源的RedisTemplate
     * 注意：这里指定使用名称=factory 的 RedisConnectionFactory
     * 并且标识第一个数据源是默认数据源 @Primary
     *
     * @param factory
     * @return
     */
    @Bean("redisTemplate1")
    @Primary
    public RedisTemplate<String, String> redisTemplate(@Qualifier("redisConfig1Factory") RedisConnectionFactory factory) {
        /*RedisTemplate<String, Object> redisTemplate;
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        //设置序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;*/


        RedisTemplate<String, String> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(redisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(redisSerializer);
        //key haspmap序列化
        template.setHashKeySerializer(redisSerializer);
        //
        return template;
    }

    /**
     * 配置第一个数据源的RedisTemplate
     * 注意：这里指定使用名称=factory2 的 RedisConnectionFactory
     *
     * @param factory
     * @return
     */
    @Bean("redisTemplate2")
    public RedisTemplate<String, String> redisTemplate2(@Qualifier("redisConfig2Factory") RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        //Jackson2JsonRedisSerializer redisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(redisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(redisSerializer);
        //key haspmap序列化
        template.setHashKeySerializer(redisSerializer);
        //
        return template;
        //return getStringStringRedisTemplate(factory2);
    }

    /**
     * 设置序列化方式 （这一步不是必须的）
     *
     * @param factory
     * @return
     */
    /*private RedisTemplate<String, String> getStringStringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        return template;
    }*/
}
