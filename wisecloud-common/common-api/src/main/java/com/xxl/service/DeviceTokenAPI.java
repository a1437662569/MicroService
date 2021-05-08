package com.xxl.service;


import com.xxl.common.enums.URIConsts;
import com.xxl.common.vo.DeviceTokenVO;
import com.xxl.common.api.R;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




/**
 * DeviceToken服务提供者API
 *
 * @author executor
 * @email 2605766001@qq.com
 * @date 2020-10-20 13:35:57
 */
@RestController
@RequestMapping(URIConsts.API_DEVICE)
public interface DeviceTokenAPI {

    /**
     * 根据userId查询deviceToken
     * @param userId
     * @return
     */
    @GetMapping(value = "getByUserId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R<DeviceTokenVO> getOne(@RequestParam String userId);

    /**
     * 更新或插入DeviceToken信息
     */
    @PutMapping(value = "updateOrInsert", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    R updateOrInsert(@Validated @RequestBody DeviceTokenVO deviceTokenVO);



}
