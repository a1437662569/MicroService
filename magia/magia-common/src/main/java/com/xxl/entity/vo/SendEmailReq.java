package com.xxl.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendEmailReq implements Serializable {

    private String ipAddr;

    /**
     * 请求发送验证码类型 枚举值
     * 1：内容更新通知
     * 2：URL/DNS变更通知
     * 3：官网地址变更通知
     */
    @NotBlank
    private String type;

    /**
     * 发送的类型
     * 1:指定用户发送
     * 2:所有已加入免费试用的用户发送
     * 3：所有用户都发送
     */
    @NotBlank
    private String sendType;

    /**
     * 为了安全起见，这里是秘钥，与相关人员联系获取
     */
    @NotBlank
    private String secretKey;

    /**
     * 当sendType为1的时候必选 需要发送的邮件的集合
     */
    private List<String> emailList;

    private String lang = "pt";
}