package com.xxl.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.common.api.R;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.vo.DeviceTokenVO;
import com.xxl.mapper.DeviceTokenMapper;
import com.xxl.model.DeviceTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service("deviceTokenService")
public class DeviceTokenServiceImpl extends ServiceImpl<DeviceTokenMapper, DeviceTokenModel> implements DeviceTokenService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @DS("read")
    @Override
    public R<DeviceTokenVO> getOneByUserId(String userId) {
        QueryWrapper<DeviceTokenModel> queryWrapper = new QueryWrapper<DeviceTokenModel>();
        queryWrapper.eq("user_id",userId);
        DeviceTokenModel one = this.getOne(queryWrapper);
        if(null!=one){
            DeviceTokenVO deviceTokenVO = new DeviceTokenVO();
            BeanUtils.copyProperties(one,deviceTokenVO);

            String key = String.format(RedisEnum.DEVICE_DEVICE_TOKEN_$USERID_STR.getKey(), deviceTokenVO.getUserId());
            int expire = ThreadLocalRandom.current().nextInt( 7 , 15);
            String value = JsonUtil.writeObject2JSON(deviceTokenVO);
            redisTemplate.opsForValue().set(key,value,expire, TimeUnit.DAYS);
            log.info("缓存userId:{}的deviceToken",userId);
            return new R<DeviceTokenVO>().ok(deviceTokenVO);
        }
        return R.notFound();
    }


}
