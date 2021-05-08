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
public class IptvServerInfoRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    // 服务器地址
    private String url;

    //服务端口
    private String port;

    //https端口
    private String https_port;

    //服务器协议
    private String server_protocol;

    //rtmp端口
    private String rtmp_port;

    //时区
    private String timezone;

    //当前时间（时间戳）
    private Long timestamp_now;

    //当前时间（标准时间）
    private String time_now;

    //是否正在服务
    private boolean process;


}