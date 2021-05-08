package com.xxl.common.enums;

public class CommonConsts {
    public static final String LOG_FORMAT = "[{\"ip\":\"{}\",\"executeTime\":\"{}\",\"uri\":\"{}\",\"data\":{\"request\":{},\"response\":{}}}]";
    public static final String JSON_SYSTEM_ERROR = "{\"returnCode\":\"500\",\"errorMessage\":\"系统内部错误（未知错误）\"}";

    public static final String MESSAGE_BOX_STR = "message_box";
    public static final String FCMMESSAGEINFO_STR = "fcmMessageInfo";

    public static final Integer SECOND_1800 = 1800;

    public static final String OPEN = "open";
    public static final String TESTKEY = "TestKey1";
    /**
     * 用户名
     */
    public static final String PARAM_USER_NAME = "userName";

    /**
     * 用户token
     */
    public static final String PARAM_USER_TOKEN = "userToken";

    public static final String REQUEST_PARAM_ERROR = "请求参数异常";
    public static final String USER_ID_STR = "userId";
    public static final String MESSAGE_TYPE_STR = "messageType";
    public static final String APP_ID_STR = "appId";
    public static final String STATUS_STR = "status";
    public static final String MESSAGE_ID_STR = "messageId";
    public static final String UPDATE_TIME_STR = "updateTime";
    public static final String CREATE_TIME_STR = "createTime";
    public static final int NUMBER_ZERO = 0;
    public static final String NUMBER_ZERO_STR = "0";
    public static final int NUMBER_ONE = 1;
    public static final int NUMBER_TWO = 2;
    public static final int NUMBER_THREE = 3;
    public static final int NUMBER_FOUR = 4;
    public static final String NUMBER_ONE_STR = "1";
    public static final String NUMBER_TWO_STR = "2";
    public static final String NUMBER_THREE_STR = "3";
    public static final String NUMBER_FOUR_STR = "4";
    public static final String NUMBER_NINE_STR = "9";

    /**
     * 3000 字符
     */
    public static final String THREE_THOUSAND_STR = "3000";

    /**
     * utf-8常量
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 30000 字符
     */
    public static final String THIRTRY_THOUSAND_STR = "30000";

    public static final int NUMBER_TEN = 10;
    public static final String LOG_ERROR_STR = "[\"{}\"]";

    public static final String TASK_KEY_MSG = "RedisKey:%s,msg:%s,状态:%s";
    public static final String LOG_TO_KAFKA_STYLE_STR_NEW_SN = "[{\"ip\":\"{}\",\"executeTime\":\"{}\",\"uri\":\"{}\",\"action\":\"{}\",\"sn\":\"{}\",\"desSn\":\"{}\",\"appId\":\"{}\",\"appVersion\":\"{}\",\"appLanguage\":\"{}\",\"data\":{\"request\":{},\"response\":{}}}]";

    //防伪码相关
    public static final String API_DYNAMIC_CODE6 = "APPSTORE-API_DYNAMIC_CODE6_";

    //缓存相关
    public static final String API_SYS_CONFIG_DICTIONARY = "APPSTORE-API_SYS_CONFIG_DICTIONARY";
    public static final String REDIS_DOMAIN_SERVER = "Redis_Domain_Server";
    public static final String API_SYSPACKAGE_CHECKUPDATE = "sysPackageCheckUpdate";
    public static final String API_GETSTARAPPS = "getStarApps";
    public static final String API = "/api";
    public static final String MARKETSERVER = "/MarketServer";
    public static final String MAGIA = "/api/magia";
    public static final String MARKETSERVER_UPDATE = "/update";
    public static final String MARKETSERVER_APPT = "/appt";
    public static final String API_CHECKUPDATE = "checkUpdate";
    public static final String API_GETAPPSBYTYPE = "getAppsByType";
    public static final String API_GETAPPDETAIL = "getAppDetail";
    public static final String APPSTORE_API_DOWNLOAD_TIMES_REDIS_PREFIX = "APPSTORE-API_DOWNLOAD_TIMES_*";
    public static final String APPSTORE_API_DOWNLOAD_TIMES_$APPID_STR = "APPSTORE-API_DOWNLOAD_TIMES_%s";
    public static final String APPSTORE_API_STORAGE_USERIDGROUPBO_HASH = "APPSTORE_API_STORAGE_USERIDGROUPBO_HASH";
    public static final String APPSTORE_API_STORAGE_USERIDGROUPBO_$GROUPCODE_HASH = "APPSTORE-API_STORAGE_USERIDGROUPBO_%s_HASH";


    public static final String API_UPDATEAPPTIMES = "updateAppTimes";
    public static final String API_AP = "ap";
    public static final String APPSTORE_API_APPID_TO_UPDATE_CHECKUPDATEINFO_SET = "APPSTORE-API_APPID_TO_UPDATE_CHECKUPDATEINFO_SET";
    public static final String APPSTORE_API_GROUP_CODE_TO_UPDATE_GROUPINFO_SET = "APPSTORE_API_GROUP_CODE_TO_UPDATE_GROUPINFO_SET";
    public static final String APPSTORE_API_STRATEGY_ID_TO_UPDATE_STRATEGY_NEW_INFO_SET = "APPSTORE_API_STRATEGY_ID_TO_UPDATE_STRATEGY_NEW_INFO_SET";

    public static final String NULL = "NULL";


    //客户类型
    public static final String API_CUSTOMER_CUSTOMER_TYPE = "APPSTORE-API_CUSTOMER_CUSTOMER_TYPE_";


    /**
     * 域名服务固定HField,固定为UPDATE_TIME.用户对比API缓存与域名缓存各种的更新时间
     */
    public static final String REDIS_DOMAIN_SERVER_DATE = "UPDATE_TIME";
    public static final String REDIS_DOMAIN_SERVER_FIELD = "DOMAIN_SERVER_DATA";
    public static final String DOMAIN_PRIMARY_DAFAULT = "primary_default";

    /**
     * 常量unknown
     */
    public static final String UNKNOWN = "unknown";
    //语言
    public static final String ZH_CN = "zh-CN";
    public static final String EN = "en";
    public static final String PT = "pt";
    public static final String ES = "es";
    public static final String COMMENT_ENTER_STR = "\n";
    public static final String EMPTY_STR = "";


    public static final String ZH_CN_ALL = "全部";
    public static final String ZH_CN_GLOBAL = "全球";
    public static final String ZH_CN_GLOBAL_ALL = "全球_全部";
    public static final String NULL_NULL = "NULL_NULL";

    /**
     * BIZ类型的日志
     */
    public static final String BIZ = "BIZ";
    public static final String RESULT_TYPE_JSON = "json";
    public static final String API_SN_COUSTOMER = "APPSTORE-API_SN_COUSTOMER_";
    public static final String GET_SN_CUSTOMER = "getSnCustomer";
    public static final String API_PACKAGE_AREA_TYPE = "APPSTORE-API_PACKAGE_AREA_TYPE_";
    public static final String API_SN_GROUP_CODE = "APPSTORE-API_SN_GROUP_CODE_";
    public static final String API_SN_DES = "APPSTORE-API_SN_DES_%s";
    /**
     * 下划线连接符
     */
    public static final String DOWN_LINE = "_";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 通配符
     */
    public static final String WILDCARD = "*";


    public static final int NUMBER15000 = 15000;
    public static final int NUMBER20000 = 20000;
    public static final int NUMBER25000 = 25000;
    public static final int NUMBER30000 = 30000;
    public static final int NUMBER35000 = 35000;
    public static final int NUMBER40000 = 40000;
    public static final int NUMBER45000 = 45000;
    public static final int NUMBER50000 = 50000;
    public static final int NUMBER55000 = 55000;
    public static final int NUMBER60000 = 60000;
    public static final int NUMBER1800000 = 1800000;

    /**
     * TMS认证开关 0-关闭TMS认证 ；1-开启TMS认证，仅记录认证日志；2-强制认证，如果TMS认证失败则激活失败，TMS认证成功才走激活流程
     */
    public static final String TMS_AUTH_SWITCH = "TMS_auth_switch";

    /**
     * TMS认证接口url主
     */
    public static final String TMS_AUTH_URL_PRIMARY = "TMS_auth_url_primary";
    /**
     * TMS认证接口参数
     */
    public static final String TMS_API_PARAM_V1_V2_V3 = "?sn=%s&code=%s";
    /**
     * TMS认证接口url备
     */
    public static final String TMS_AUTH_URL_SPARE = "TMS_auth_url_spare";
    /**
     * TMS认证接口参数
     */
    public static final String TMS_API_PARAM_V3_PRE = "?sn=%s&pre_code=%s";
    /**
     * TMS认证接口url主
     */
    public static final String TMS_VALIDATE_URL_PRIMARY = "TMS_validate_url_primary";
    /**
     * TMS认证接口url主
     */
    public static final String TMS_VALIDATE_URL_SPARE = "TMS_validate_url_spare";
    /**
     * 失效时长字典Key
     */
    public static final String DYNAMIC_EXPRIES_MINUTE = "DYNAMIC_EXPRIES";
    public static final String API_DYNAMIC_CODE6_HIS = "APPSTORE-API_DYNAMIC_CODE6_HIS_";
    /**
     * H本土官网
     */
    public static final String H_MAINLAND_ADDR = "mainland_H_url";
    /**
     * H移民官网
     */
    public static final String H_IMMIGRANT_ADDR = "immigrant_H_url";

    public static final String DEFAULT_KEY = "2b494e53756c664c2f44465245733572";
    public static final String GMAIL = "@gmail.com";
    public static final String POINT = ".";
    public static final String PLUS = "+";
    public static final String MAGIA_REGISTER = "magia_register";
    public static final String MAGIA_FREETRAIL = "magia_freetrail";
    public static final String MAGIA_FORGETPASSWORD = "magia_forgetpassword";
    public static final String MAGIA_CONTENTUPDATE = "magia_contentupdate";
    public static final String MAGIA_LISTUPDATE = "magia_listupdate";
    public static final String MAGIA_WEBSITEUPDATE = "magia_websiteupdate";
    public static final String FALSE_STR = "false";
    public static final String TRUE_STR = "true";
    public static final String FREE_STR = "free";
    public static final String DEFAULT_OTHER_PWD = "123456";
    public static final String DEFAULTPLAYLISTURL = "http://magia.com";
    public static final String DEFAULTDNS = "8.8.8.8";
    public static final String DEFAULTPARENTALPWD = "9527";
    public static final String DEFAULT_MAGIA_SECRET = "csmagiaplaylist";
    public static final String SLASH_STR = "/";
    public static final String LIVE_STR = "live";
    public static final String VOD_STR = "vod";
    public static final String REDIRECT_STR = "redirect:";
    public static final String ACTIVE_STR = "Active";
    public static final String GET_LIVE_CATEGORIES_STR = "get_live_categories";
    public static final String CATEGORY_ID_STR = "category_id";
    public static final String GET_LIVE_STREAMS_STR = "get_live_streams";
    public static final String GET_VOD_STREAMS_STR = "get_vod_streams";
    public static final String GET_VOD_CATEGORIES_STR = "get_vod_categories";
    public static final String GET_VOD_INFO_STR = "get_vod_info";
    public static final String GET_SERIES_CATEGORIES_STR = "get_series_categories";
    public static final String GET_SERIES_STR = "get_series";
    public static final String GET_SERIES_INFO_STR = "get_series_info";
}
