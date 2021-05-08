package com.xxl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaTopicEnum {

    COUPON_$APPID("coupon_%s", "优惠券赠送 TOPIC"),
    ORDER_$APPID("order_%s", "订单成功/失败 TOPIC"),
    CRASHBACK_$APPID("cashback_%s", "获得返利 TOPIC"),
    FRIEND_$APPID("friend_%s", "邀请好友 TOPIC"),
    ;

    private String topic;
    private String msg;

}
