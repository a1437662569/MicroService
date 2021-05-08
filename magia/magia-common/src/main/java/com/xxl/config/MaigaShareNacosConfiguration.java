package com.xxl.config;

import com.xxl.common.enums.CommonConsts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 这里主要是从nacos上的magia-share-public.yml文件中拿到对应的配置信息
 */
@RefreshScope
@Component
@Data
//@ConfigurationProperties(prefix = "url.apk.download")
public class MaigaShareNacosConfiguration {


    /**
     * 支持的邮箱后缀
     **/
    @Value("${properties.email.suffix}")
    private String emailSuffix = "gmail.com,gmail.com.br,hotmail.com,hotmail.com.br,outlook.com,outlook.com.br,live.com,uol.com.br,bol.com.br,yahoo.com,yahoo.com.br,ymail.com,globomail.com,msn.com,aol.com";

    @Value("${properties.email.sendTotalCount}")
    private Integer sendTotalCount = 50;

    @Value("${properties.email.verifiCodeRedisTime}")
    private Integer verifiCodeRedisTime = 10;

    @Value("${properties.url.noticeCore.emailtempnotice_url}")
    private String emailtempnoticeUrl;

    @Value("${properties.email.subject.register.en}")
    private String subjectRegisterEn;

    @Value("${properties.email.subject.register.pt}")
    private String subjectRegisterPt;

    @Value("${properties.email.subject.register.es}")
    private String subjectRegisterEs;

    @Value("${properties.email.subject.forgetpassword.en}")
    private String subjectForgetpasswordEn;

    @Value("${properties.email.subject.forgetpassword.pt}")
    private String subjectForgetpasswordPt;

    @Value("${properties.email.subject.forgetpassword.es}")
    private String subjectForgetpasswordEs;

    @Value("${properties.appModel}")
    private String appModel = CommonConsts.FREE_STR;

    @Value("${properties.defaultOtherPwd}")
    private String defaultOtherPwd = CommonConsts.DEFAULT_OTHER_PWD;

    @Value("${properties.defaultPlaylistUrl}")
    private String defaultPlaylistUrl = CommonConsts.DEFAULTPLAYLISTURL;

    @Value("${properties.defaultDns}")
    private String defaultDns = CommonConsts.DEFAULTDNS;

    @Value("${properties.defaultParentalPwd}")
    private String defaultParentalPwd = CommonConsts.DEFAULTPARENTALPWD;

    @Value("${properties.email.subject.freetrail.en}")
    private String subjectFreetrailEn;

    @Value("${properties.email.subject.freetrail.pt}")
    private String subjectFreetrailPt;

    @Value("${properties.email.subject.freetrail.es}")
    private String subjectFreetrailEs;


    @Value("${properties.email.subject.contentUpdate.en}")
    private String subjectContentUpdateEn;

    @Value("${properties.email.subject.contentUpdate.pt}")
    private String subjectContentUpdatePt;

    @Value("${properties.email.subject.contentUpdate.es}")
    private String subjectContentUpdateEs;


    @Value("${properties.email.subject.listUpdate.en}")
    private String subjectListUpdateEn;

    @Value("${properties.email.subject.listUpdate.pt}")
    private String subjectListUpdatePt;

    @Value("${properties.email.subject.listUpdate.es}")
    private String subjectListUpdateEs;


    @Value("${properties.email.subject.websiteUpdate.en}")
    private String subjectWebsiteUpdateEn;

    @Value("${properties.email.subject.websiteUpdate.pt}")
    private String subjectWebsiteUpdatePt;

    @Value("${properties.email.subject.websiteUpdate.es}")
    private String subjectWebsiteUpdateEs;

    @Value("${properties.websiteUrl}")
    private String websiteUrl;

    @Value("${properties.slbPlayUrl}")
    private String slbPlayUrl;

    /**
     * 播放串過期時間分鐘
     */
    @Value("${properties.expiresMinute}")
    private Integer expiresMinut = 240;


    @Value("${properties.iptv.allowed_output_formats}")
    private String allowedOutputFormats = "m3u8,ts,rtmp";


    @Value("${properties.iptv.url}")
    private String iptvUrl = "smart.cms-central.ovh";

    @Value("${properties.iptv.port}")
    private String iptvPort = "80";

    @Value("${properties.iptv.https_port}")
    private String iptvHttpsPort = "25463";

    @Value("${properties.iptv.server_protocol}")
    private String iptvServerProtocol = "http";

    @Value("${properties.iptv.rtmp_port}")
    private String iptvRtmpPort = "25462";
}
