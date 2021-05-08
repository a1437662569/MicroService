package com.xxl.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinReq implements Serializable {

    private String ipAddr;

    private String mac;

    private String lang = "pt";

}