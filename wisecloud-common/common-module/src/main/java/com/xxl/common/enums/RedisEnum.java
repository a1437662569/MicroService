package com.xxl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 服务名缩写_key_keyType=服务名缩写_key_keyType
 * 服务名缩写_key_$参数_keyType=服务名缩写_key_参数值_keyType
 */
@Getter
@AllArgsConstructor
public enum RedisEnum {
    DEVICE_DEVICE_TOKEN_$USERID_STR("DEVICE_DEVICE_TOKEN_%s_STR","deviceToken"),
    DEVICE_DEVICE_TOKEN_ASYNC_2DB_LIST("DEVICE_DEVICE_TOKEN_ASYNC_2DB_STR","deviceToken异步更新数据库"),
    FCM_MESSAGE_INFO("fcmMessageInfo","fcm消息写入redis队列"),
    APPSTORE_CHECKUPDATE_HASH("checkUpdate", "检测更新"),
    APPSTORE_GETSTARAPPS_HASH("getStarApps", "获取星标APP"),
    APPSTORE_GETAPPSBYTYPE_HASH("getAppsByType", "按类型获取APP-getAppsByType"),
    APPSTORE_GETAPPDETAIL_HASH("getStarApps", "获取APP详情-getAppDetail"),
    APPSTORE_SYSPACKAGE_CHECKUPDATE_HASH("getStarApps", "检查包更新"),

    /**应用市场相关Key**/
    APPSTORE_API_DOMAIN_SERVER_HASH("APPSTORE_API_DOMAIN_SERVER_HASH", "应用市场:域名缓存"),
    APPSTORE_API_DOWNLOAD_TIMES_$APPID_STR("APPSTORE-API_DOWNLOAD_TIMES_%s_STR", "下载次数key"),
    APPSTORE_API_STORAGE_SN_GROUPCODE_HASH("APPSTORE-API_STORAGE_SN_GROUPCODE_HASH", "应用市场:根据sn获取分组code,field:sn"),
    APPSTORE_API_STORAGE_USERID_GROUPCODE_HASH("APPSTORE-API_STORAGE_USERID_GROUPCODE_HASH", "应用市场:根据userId获取分组code,field:userId"),
    APPSTORE_API_STORAGE_APPDETAIL_HASH("APPSTORE-API_STORAGE_APPDETAIL_HASH", "应用市场:应用详情信息(含推荐与星级),field:appPackagename"),
    APPSTORE_API_STORAGE_APP_VERSION_$APPPACKAGENAME_HASH("APPSTORE-API_STORAGE_APP_VERSION_%s_HASH", "应用市场:应用版本信息,field:版本"),
    APPSTORE_API_STORAGE_STRATEGYBLACKWHITEBO_$APPPACKAGENAME_HASH("APPSTORE-API_STORAGE_STRATEGYBLACKWHITEBO_%s_HASH", "应用市场:存储策略信息的redisHash,field:策略id"),
    APPSTORE_API_STORAGE_SN_CUSTOMER_HASH("APPSTORE-API_STORAGE_SN_CUSTOMER_HASH","应用市场:sn与客户的映射,field:sn"),
//    APPSTORE_API_STORAGE_APPID_VERSIONCODE_HASH("APPSTORE_API_STORAGE_APPID_VERSIONCODE_HASH", "应用市场:AppId和版本的映射,filed:versionCode"),

    /**应用市场增量缓存redisKey**/
    APPSTORE_API_APPID_TO_UPDATE_CHECKUPDATEINFO_SET("APPSTORE-API_APPID_TO_UPDATE_CHECKUPDATEINFO_SET","应用市场:检测更新集合"),
    APPSTORE_API_STRATEGY_ID_TO_UPDATE_STRATEGY_NEW_INFO_SET("APPSTORE_API_STRATEGY_ID_TO_UPDATE_STRATEGY_NEW_INFO_SET", "应用市场:更新策略集合"),
    APPSTORE_API_GROUP_CODE_TO_UPDATE_GROUPINFO_SET("APPSTORE_API_GROUP_CODE_TO_UPDATE_GROUPINFO_SET", "应用市场:更新分组信息集合"),

    /**
     * magia缓存redisKey
     */
    REDIS_EMAIL_SEND_TOTLE_COUNT_$EMAIL("MAGIA_REDIS_EMAIL_SEND_TOTLE_COUNT_%s","短信验证码已发送的次数"),
    REDIS_EMAIL_SEND_180_SECOND_LIMIT_$EMAIL("MAGIA_REDIS_EMAIL_SEND_180_SECOND_LIMIT_%s","同一个邮箱180s之内只能发送一封邮件"),
    REDIS_EMAIL_VERIFICODET_$EMAIL_$TYPE("MAGIA_REDIS_EMAIL_VERIFICODE_%s_TYPE_%s","当前邮件需要发送的验证码"),
    MAGIA_MAC_PUSH_LIST("MAGIA_MAC_PUSH_LIST","三方服务处理这个list里面的mac，然后调用三方网站接口下发链接"),

    // 用户相关信息的Redis的key值
    MAGIA_ACCOUNT_EMAIL_REDIS("MAGIA_ACCOUNT_%s_EMAIL","通过email保存用户信息"),
    MAGIA_ACCOUNT_USERID_REDIS("MAGIA_ACCOUNT_%s_USERID","通过userId保存用户信息"),
    ;


    private String key;
    private String msg;
}