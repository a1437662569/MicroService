package com.xxl.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxl.common.api.R;
import com.xxl.common.vo.DeviceTokenVO;
import com.xxl.model.DeviceTokenModel;

/**
 * 设备token表
 *
 * @author executor
 * @email 2605766001@qq.com
 * @date 2020-10-20 13:35:57
 */
public interface DeviceTokenService extends IService<DeviceTokenModel> {
    R<DeviceTokenVO> getOneByUserId(String userId);
}

