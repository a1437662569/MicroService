package com.xxl.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmailVerifyCodeReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户邮箱
     */
    @NotBlank
    private String email;

    /**
     * 请求发送验证码类型 枚举值
     * 1：注册邮箱
     * 2：通过邮箱找回密码
     */
    @NotBlank
    private String type;

    private String lang = "en";

    private String ipAddr;

}