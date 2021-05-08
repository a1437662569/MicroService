package com.xxl.service.impl;

import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.ParamUtil;
import com.xxl.common.util.RedisUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.entity.Account;
import com.xxl.mapper.read.RAccountMapper;
import com.xxl.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j

public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RAccountMapper rAccountMapper;


    /**
     * @param email 用户的email
     * @return 该email对应的用户信息
     */
    @Override
    public Account accountSelectByEmailFromRedis(String email) {
        Account account = null;
        try {
            // 只有邮箱不为空的时候才处理
            if (!StringUtil.isEmpty(email)) {
                // 通过email从redis查询出来
                String redisEmailKey = String.format(RedisEnum.MAGIA_ACCOUNT_EMAIL_REDIS.getKey(), email);
                account = (Account) redisUtil.getObject(redisEmailKey, Account.class);

                // 如果从redis中获取到的数据为空，那么从数据库里面去获取
                if (null == account) {
                    account = rAccountMapper.selectByEmail(email);
                    // 如果从数据库里面获取到的不是null，那么需要写入到redis中
                    if (null != account) {
                        redisUtil.setEx(redisEmailKey, JsonUtil.writeObject2JSON(account), 60, TimeUnit.MINUTES);
                        redisUtil.setEx(String.format(RedisEnum.MAGIA_ACCOUNT_USERID_REDIS.getKey(), account.getUserId()), JsonUtil.writeObject2JSON(account), 60, TimeUnit.MINUTES);
                    }
                }
            }
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return account;
    }

    /**
     * @param userId 用户的userId
     * @return 该userId对应的用户信息
     */
    @Override
    public Account accountSelectByUserIdFromRedis(String userId) {
        Account account = null;
        try {
            // 只有邮箱不为空的时候才处理
            if (!StringUtil.isEmpty(userId)) {
                // 通过userId从redis查询出来
                String redisUserIdKey = String.format(RedisEnum.MAGIA_ACCOUNT_USERID_REDIS.getKey(), userId);
                account = (Account) redisUtil.getObject(redisUserIdKey, Account.class);

                // 如果从redis中获取到的数据为空，那么从数据库里面去获取
                if (null == account) {
                    account = rAccountMapper.selectByUserId(Integer.parseInt(userId));
                    // 如果从数据库里面获取到的不是null，那么需要写入到redis中
                    if (null != account) {
                        redisUtil.setEx(redisUserIdKey, JsonUtil.writeObject2JSON(account), 60, TimeUnit.MINUTES);
                        redisUtil.setEx(String.format(RedisEnum.MAGIA_ACCOUNT_EMAIL_REDIS.getKey(), account.getEmail()), JsonUtil.writeObject2JSON(account), 60, TimeUnit.MINUTES);
                    }
                }
            }
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }
        return account;
    }

    /**
     * @param account 清理掉redis中该用户的缓存信息
     */
    @Override
    public void cleanAccountRedis(Account account) {
        try {
            String email = account.getEmail();
            Integer userId = account.getUserId();
            if (!StringUtil.isEmpty(email)) {
                redisUtil.delete(String.format(RedisEnum.MAGIA_ACCOUNT_EMAIL_REDIS.getKey(), account.getEmail()));
            }
            redisUtil.delete(String.format(RedisEnum.MAGIA_ACCOUNT_USERID_REDIS.getKey(), userId));
        } catch (Exception e) {
            log.error(StringUtil.printExcetion(e));
        }

    }


}

