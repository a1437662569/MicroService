package com.xxl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Data
@ConfigurationProperties(prefix = "url.apk.download")
public class URLNacosConfiguration {

//
//    /**
//     * UMS:根据sn获取客户的URL
//     **/
//    @Value("${url.getCustomerBySn.ums}")
//    private String umsGetCustomerBySnURL;
//
//    /**
//     * AAA:根据sn获取客户的URL
//     **/
//    @Value("${url.getCustomerBySn.aaa}")
//    private String aaaGetCustomerBySnURL;
//
//    /**
//     * TMS授权:主域名
//     **/
//    @Value("${url.auth.tms.primary}")
//    private String tmsAuthPrimaryURL;
//    /**
//     * TMS授权:主域名
//     **/
//    @Value("${url.auth.tms.spare}")
//    private String tmsAuthSpareURL;
//
//    /**
//     * TMS校验:主域名
//     **/
//    @Value("${url.validate.tms.primary}")
//    private String tmsValidatePrimaryURL;
//    /**
//     * TMS校验:备域名
//     **/
//    @Value("${url.validate.tms.spare}")
//    private String tmsValidateSpareURL;
//
//    /**
//     * 官网地址
//     **/
//    @Value("${url.website}")
//    private String websiteURL;


}
