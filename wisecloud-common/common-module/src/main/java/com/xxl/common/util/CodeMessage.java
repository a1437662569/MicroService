package com.xxl.common.util;

/**
 * 消息信息类
 *
 * @author xiaohui
 * @version 2016年12月11日
 * @see CodeMessage
 * @since
 */
public class CodeMessage {
    public static final String CHECK_INVALID = "防伪码已过期";
    public static final String CHECK_FAIL = "校验失败";
    public static final String PASSWORD_ERROR = "原密码不正确";

    public static final String PACKAGE_ERROR = "应用包名已经存在";

    public static final String DELETE_APP_LAN = "应用至少应该有一种语言信息";

    public static final String STRATEGY_ID_NOT_EXIST = "策略id不存在";

    public static final String STRATEGY_NAME_EXIST = "策略名称已存在";

    public static final String REQUEST_ERROR = "请求参数错误";

    public static final String CONFIT_ERROR = "配置文件异常";

    public static final String USER_NOT_EXITS = "用户id不存在";

    public static final String TOP_APP_ID_NOT_EXISTS = "榜单id不存在";

    public static final String APP_EXITS = "榜单表中已存在该应用";

    public static final String TERMINAL_SN_NOT_EXITS = "终端SN不存在";

    public static final String APP_ID_NOT_EXIST = "应用id不存在";
    /**
     * 分类关联应用
     */
    public static final String TYPE_RE_APP = "此分类下存在关联应用，无法删除";
    /**
     * 分类id不存在
     */
    public static final String TYPE_ID_NOT_EXITS = "分类id不存在";
    /**
     * 分类名称已存在
     */
    public static final String TYPE_NAME_EXITS = "分类名称已存在";
    /**
     *
     */
    public static final String TYPE_NAME_ERROR = "分类名称不能出现/和空格";
    /**
     * 成功
     */
    public static final String IS_SUCCESS = "成功";

    /**
     * 网络异常
     */
    public static final String NETWORK_ANOMALIES = "网络异常";

    /**
     * 参数异常
     */
    public static final String PARAM_ANOMALIES = "请求参数异常！";

    /**
     * 没有权限
     */
    public static final String NO_ACCESS = "没有访问权限";

    /**
     * 系统内部错误（未知错误）
     */
    public static final String SYSTEM_ERROR = "系统内部错误（未知错误）";

    /**
     * 系统过载保护
     */
    public static final String OVERLOAD_PROTECTION = "系统过载保护";

    /**
     * 数据库错误
     */
    public static final String DATABASE_ERROR = "数据库错误";

    public static final String USER_STATUS_ERROR = "用户已被禁用";

    public static final String USER_NAMEORPWD_ERROR = "用户名或者密码错误";

    /**
     * 用户名已存在
     */
    public static final String USER_NAME_EXSIST = "用户名已存在";

    /**
     * 用户名或密码设置太短
     */
    public static final String USER_NAME_TOOSHORT = "用户名或密码设置太短";

    public static final String TOKEN_VALID = "token已失效，请重新登陆！";

    public static final String RELATION_EXIST = "关联关系已存在";

    public static final String DICT_NAME_EXIST = "该分类下字典Key已存在";

    public static final String GROUP_IS_REF_DELIVER = "分组存在关联决策关系";

    public static final String TMS_URL_NOT_EXIST = "请求TMS认证的url地址不存在";

    /**
     * 终端认证信息错误
     */
    public static final String TMS_TERMINAL_ERROR = "终端认证信息错误";

    /**
     * 请求TMS失败
     */
    public static final String REQUEST_TMS_FAIL = "请求TMS失败";
    /**
     * 请求TAS失败
     */
    public static final String REQUEST_TAS_FAIL = "请求TAS失败";

    public static final String AUTH_EXPIRED = "授权已过期（非免费模式）";
}
