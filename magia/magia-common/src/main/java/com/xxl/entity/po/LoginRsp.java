package com.xxl.entity.po;

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
public class LoginRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否已同意免费试用
     * true：已同意
     * false：未同意
     */
    private String isJoined;

    /**
     * userToken
     */
    private String userToken;

}