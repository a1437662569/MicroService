package com.xxl.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号方式
     * 1：ID账号登录
     * 2：邮箱账号登录
     * 3：手机号码登录
     * 4：短信快捷方式登录
     * 5：短信token方式登录
     */
    @NotBlank
    private String accountType;


    /**
     * 登录用户名
     * 当accountType为“1”时，此参数值为ID账号
     * 当accountType为“2”时，此参数值为邮箱账号
     * 当accountType为“3”时，此参数值为手机号码
     * 当accountType为“4”时，此参数值为手机号码
     * 当accountType为5时，该值为qrAuthCodeToken的值
     */
    @NotBlank
    private String userName;

    /**
     * 账号密码
     * 当accountType为“1”、“2”、“3”时，此参数为必选
     */
    private String password;

    /**
     * 国家区号
     * 当accountType为“3”、“4”时，此参数值必选
     */
    private String areaCode;

    /**
     * 验证码
     * 当accountType为4时，此参数为必选
     */
    private String verificationCode;

    /**
     * 验证码token
     * 当accountType为5时，此参数为必选
     */
    private String verificationToken;

    private String lang = "en";

    private String ipAddr;

}