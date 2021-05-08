package com.xxl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class URIConsts {
    //deviceToken服务提供者模块
    public final static String API_DEVICE = "/api/device/",
    //消息盒子服务提供者模块
    API_BOX = "/api/box/",
    //消息盒子服务提供者模块
    API_FCM = "/api/fcm/",

    API_AMS = "/ams/"
            ;
}
