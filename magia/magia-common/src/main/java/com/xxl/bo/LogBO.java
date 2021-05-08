package com.xxl.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogBO {
    //[{"ip":"{}","executeTime":"{}","uri":"{}","action":"{}","sn":"{}","desSn":"{}","appId":"{}","appVersion":"{}","appLanguage":"{}","data":{"request":{},"response":{}}}]
    private String ip;
    private Long executeTime;
    private String uri;
    private String action;
    private String sn;
    private String desSn;
    private String appId;
    private String appVersion;
    private String appLanguage;
    private LogDataBO data;
}
