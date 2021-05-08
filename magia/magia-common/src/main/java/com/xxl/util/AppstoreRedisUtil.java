package com.xxl.util;

import com.xxl.common.util.RedisUtil;
import org.springframework.stereotype.Component;

@Component
public class AppstoreRedisUtil extends RedisUtil {

    public void bind() {
        getRedisTemplate().opsForValue().set("666", "666");
    }
}
