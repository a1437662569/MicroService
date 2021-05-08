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
public class PasswordResetReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新的账号密码， MD5（pwd+csmagiaplaylist）加密后传输(32位小写)
     */
    @NotBlank
    private String newPwd;

    /**
     * 老的账号密码，用于校验合法性， MD5（pwd+csmagiaplaylist）加密后传输(32位小写)
     */
    @NotBlank
    private String oldPwd;

    private String ipAddr;

}