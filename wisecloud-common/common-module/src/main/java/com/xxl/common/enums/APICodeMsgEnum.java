package com.xxl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * 只允许新增
 * 请勿修改删除
 */
public enum APICodeMsgEnum {

    /**
     * 公共网元状态码
     */
    PARAM_ERROR("1", "参数错误"),
    CONFIG_ERROR("2", "数据配置错误"),
    REQUEST_FAIL("3", "请求失败"),
    NO_CONDITION("4", "条件不满足"),
    NOT_FOUND("5", "数据不存在"),
    SYS_BUSY("6", "系统繁忙，请稍后再试"),
    NOT_IN_DATE_RANGE("7", "不在时间范围内"),
    DB_SOLR_REDIS_ERROR("8", "DB或Solr或Redis错误"),
    NO_BALANCE("9", "余额不足"),
    DB_ERROR("10", "数据库操作失败"),
    USERNAME_PWD_ERROR("11", "账号或密码错误"),
    USER_NOT_EXITS("12", "账号不存在"),
    TOTAL("13", "账户总数"),
    DATA_EXISTS("16", "数据已存在"),
    DATA_REF_NOT_REF_GROUP("14", "数据没有关联分组"),
    DATA_DUPLICATE("15", "数据重复"),
    DATA_REF_APP("17", "数据关联了App"),
    SN_REF_GROUP("18", "sn已经关联了分组"),
    UID_REF_GROUP("19", "uid已经关联了分组"),
    TOKEN_ERROR("20", "token异常"),
    EMAIL_SUFFIX_ERROR("21", "email后缀错误"),
    EMAIL_FORMAT_ERROR("22", "email格式错误"),
    EMAIL_REGISTERED_ERROR("23", "邮箱已注册"),
    SEND_EMAIL_LIMIT_ERROR("24", "邮件发送超过每日最大限制"),
    SEND_EMAIL_180S_LIMIT_ERROR("25", "180s内无法再次发送邮件"),
    VERIFICATION_CODE_ERROR("26", "验证码无效"),
    EMAIL_NO_REGISTERED_ERROR("27", "邮箱未注册"),
    USER_NOT_EXITS_ERROR("28", "用户不存在"),
    PASSWORD_ERROR("29", "用户名密码错误"),
    FREE_TRAIL_ERROR("30", "已经加入过免费试用了，不能重复加入"),

    /**
     * HTTP通用状态码
     */
    SUCCESS("0", "请求成功"),
    SYSTEM_ERROR("500", "系统内部错误");



    private String code;
    private String msg;


}
