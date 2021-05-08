package com.xxl.common.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoxMessageInfoPO implements Serializable {

    /**
     * 用户唯一ID
     */
    private String userId;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息类型 1.系统消息 2.活动消息
     */
    private String messageType;
    /**
     * 类型 1.优惠券赠送 2.订单成功 3.订单失败 4.返现 5.好友邀请
     */
    private String type;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 内容，里面是个Json字符串
     */
    private Object content;
    /**
     * 已读状态 0 未读 1 已读
     */
    private String status;
    /**
     * 创建时间，日期格式为：yyyy-MM-dd HH:mm:ss.SSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;
    /**
     * 更新时间，日期格式为：yyyy-MM-dd HH:mm:ss.SSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updateTime;
}
