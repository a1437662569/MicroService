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
public class RegisterReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户邮箱
     */
    @NotBlank
    private String email;

    /**
     * 用户密码，传递的是加密后的密文密码
     */
    @NotBlank
    private String password;

    @NotBlank
    private String verificationCode;

    private String lang = "en";

    private String ipAddr;

}