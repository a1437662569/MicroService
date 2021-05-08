package com.xxl.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMsgStatusVo {
    /**
     * 用户唯一ID
     */
    @NotEmpty
    private String userId;

    /**
     * 消息唯一ID，UUID,当messageType为1时，该字段填空字符串
     */
    private String messageId;

    /**
     * 消息类型 1 系统消息 2 活动消息
     */
    @NotEmpty
    private String messageType;

    /**
     * 消息状态设置为已读接口，status传1即可
     */
    @NotEmpty
    private String status;

    /**
     * 用户token值，暂时可以为空
     */
    private String token;

    /**
     * 应用ID
     */
    @NotEmpty
    private String appId;

}
