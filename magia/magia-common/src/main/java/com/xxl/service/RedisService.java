package com.xxl.service;

import com.xxl.entity.Account;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public interface RedisService {
    /**
     * @param email 用户的email
     * @return 这个email对应的用户信息
     */
    Account accountSelectByEmailFromRedis(String email);

    /**
     * @param userId 用户的userId
     * @return 这个userId对应的用户信息
     */
    Account accountSelectByUserIdFromRedis(String userId);

    /**
     * @param account 清理掉redis中该用户的缓存信息
     */
    void cleanAccountRedis(Account account);
}
