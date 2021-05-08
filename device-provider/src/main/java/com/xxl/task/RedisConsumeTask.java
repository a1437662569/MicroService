package com.xxl.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.common.enums.CommonConsts;
import com.xxl.common.enums.RedisEnum;
import com.xxl.common.util.JsonUtil;
import com.xxl.common.util.StringUtil;
import com.xxl.model.DeviceTokenModel;
import com.xxl.service.DeviceTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class RedisConsumeTask {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    DeviceTokenService deviceTokenService;
    @Scheduled(initialDelay=5000,fixedDelay = 15000)
    public void redisDeviceToken2DB(){
        while (true){
            try{
                String value = redisTemplate.opsForList().rightPop(RedisEnum.DEVICE_DEVICE_TOKEN_ASYNC_2DB_LIST.getKey());

                if(StringUtil.isEmpty(value)){
                    log.info(StringUtil.redisTaskLog(RedisEnum.DEVICE_DEVICE_TOKEN_ASYNC_2DB_LIST,"暂无消息"));
                    return;
                }
                Date now = new Date();
                DeviceTokenModel deviceTokenModel = (DeviceTokenModel)JsonUtil.writeJSON2Object(value,DeviceTokenModel.class);
                LambdaQueryWrapper<DeviceTokenModel> queryWrapper = new QueryWrapper<DeviceTokenModel>()
                        .lambda()
                        .eq(DeviceTokenModel::getUserId,deviceTokenModel.getUserId())
                        .select(DeviceTokenModel::getDeviceToken,DeviceTokenModel::getId);
                DeviceTokenModel dbModel = deviceTokenService.getOne(queryWrapper);

                boolean updateOrInsert = false;

                if (null==dbModel) {
                    deviceTokenModel.setCreateTime(now);
                    deviceTokenModel.setUpdateTime(now);
                    updateOrInsert = deviceTokenService.save(deviceTokenModel);
                    log.info("UserId:{},插入Token结果:{},token值:{}", deviceTokenModel.getUserId(), updateOrInsert, deviceTokenModel.getDeviceToken());
                } else if(!StringUtil.equals(deviceTokenModel.getDeviceToken(),dbModel.getDeviceToken())) {
                    deviceTokenModel.setId(dbModel.getId());
                    deviceTokenModel.setUpdateTime(now);
                    updateOrInsert = deviceTokenService.updateById(deviceTokenModel);
                    log.info("UserId:{},更新Token结果:{},token值:{}", deviceTokenModel.getUserId(), updateOrInsert, deviceTokenModel.getDeviceToken());
                }else{
                    log.info("UserId:{},Token一致,DB token值:{},参数 token值:{}", deviceTokenModel.getUserId(), dbModel.getDeviceToken(), deviceTokenModel.getDeviceToken());
                }
            }catch (Exception e){
                log.error(CommonConsts.LOG_ERROR_STR, StringUtil.exception2JSON(e));
                return;
            }
        }
    }
}
