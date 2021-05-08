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
public class IptvUserInfoRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户名
    private String username;

    //用户密码
    private String password;

    //用户信息，默认为空
    private String message;

    //不明字段
    private Integer auth;

    //用户状态
    private String status;

    //会员过期时间
    private String exp_date;

    //是否通过认证
    private String is_trial;

    //不明字段
    private String active_cons;

    //用户创建时间（时间戳）
    private String created_at;

    //最大时间
    private String max_connections;

    //允许播放的视频格式
    private String[] allowed_output_formats;


}