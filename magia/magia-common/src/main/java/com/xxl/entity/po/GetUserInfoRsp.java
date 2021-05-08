package com.xxl.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserInfoRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String email;
    private String otherPwd;
    private String playlistUrl;
    private String dns;
    private String parentalPwd;
    private String authInvalidTime;

}