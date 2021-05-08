package com.xxl.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 发送优惠券消息的demo
 * 队列名称 ：coupon_appId   appId是变量
 * {"deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_money":"100","_count":"3"},"lang":"pt","messageType":"2","type":"1","userId":"3445543"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 * <p>
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 * <p>
 * <p>
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 */

/**
 * 订单变更paycore调用的demo
 * 队列名称 ：order_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"1","replace":{"_price":"$10"},"type":"2","orderId":"22222"}
 *
 */

/**
 * paycore返现调用demo
 * 队列名称 ：crashback_appId   appId是变量
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","replace":{"_changeAmount":40},"lang":"pt","messageType":"1","type":"4","avaliableCoin":"100","totalCoin":"500"}
 */

/**
 * 好友邀请调用demo
 * {"userId":"3445543","deviceToken":"xxxxxx","messageId":"abc","appId":"com.test.com","createTime":"2020-01-01 00:00:00","lang":"pt","messageType":"2","type":"5"}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcmMessageInfoVO {

    /**
     * 发送给kafka的topic名
     COUPON_$APPID("coupon_%s", "优惠券赠送 TOPIC"),
     ORDER_$APPID("order_%s", "订单成功/失败 TOPIC"),
     CRASHBACK_$APPID("cashback_%s", "获得返利 TOPIC"),
     FRIEND_$APPID("friend_%s", "邀请好友 TOPIC"),
     */
    @NotEmpty
    private String topicName;
    /**
     * 设备的deviceToken值。请求时为空
     */
    private String deviceToken;

    /**
     * 消息的唯一ID，UUID
     *
     */
    @NotEmpty
    private String messageId;
    /**
     * 引用唯一ID
     */
    private String appId;

    /**
     * 创建时间：yyyy-MM-dd HH:mm:ss
     */
    @NotEmpty
    private String createTime;
    /**
     * 需要替换的，如{"_money":"100","_count":"3"}，表示将面板中
     * _money的值替换为100
     */
    private Object replace;
    /**
     * apk系统语言 pt、en、es、zh。默认为pt
     */
//    @NotEmpty
    private String lang = "pt";
    /**
     * 消息类型
     *1系统消息 2活动消息
     */
    @NotEmpty
    private String messageType;
    /**
     * 类型
     * 1 优惠券赠送 2 订单成功 3订单失败 4获得返现 5好友邀请
     */
    @NotEmpty
    private String type;
    /**
     * 用户唯一ID
     */
    @NotEmpty
    private String userId;
    /**
     * 订单号  type为3的时候必选
     */
    private String orderId;
    /**
     * type 为4的时候必选
     */
    private String avaliableCoin;
    /**
     * type 为4的时候必选，最低提现bcon金额
     */
    private String minCoin;
}
