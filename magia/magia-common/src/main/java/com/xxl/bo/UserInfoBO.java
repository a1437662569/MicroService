package com.xxl.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoBO {
    private String sn;
    private String desSn;

    private String appId;
    private String versionCode;

    private String isp;
    private String country;
    private String city;
    private String lang;

    private String typeId;

    private String coustomer;
    private String snGroupCode;

    private String userId;
    private String userIdGroupCode;
}
