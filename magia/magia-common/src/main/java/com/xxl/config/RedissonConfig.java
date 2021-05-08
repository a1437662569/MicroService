package com.xxl.config;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这里的主要作用是实现redis分布式锁的redisson的配置信息：
 * 其中
 * RedisProperties redisProperties;
 * 会自动获取到配置文件中的spring.redis的配置！
 *
 * 这里只是使用了单机的redis来实现分布式锁！
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setLockWatchdogTimeout(600000);
        if (redisProperties.getCluster() != null) {
            // 集群模式配置
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().stream().toArray(String[]::new))
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        } else if (redisProperties.getSentinel() != null) {
            //添加哨兵配置
            config.useMasterSlaveServers().setMasterAddress(redisProperties.getSentinel().getMaster())
                    .setDatabase(redisProperties.getDatabase())
                    .addSlaveAddress(redisProperties.getSentinel().getNodes().stream().toArray(String[]::new))
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        } else {
            //单节点
            config.useSingleServer().setDatabase(redisProperties.getDatabase())
                    .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setPassword(StringUtils.isBlank(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
