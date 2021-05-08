package com.xxl.common.util;

/**
 * 消息碼
 * 
 * @author xiaohui
 * @version 2016年12月11日
 * @see CodeNum
 * @since
 */
public class CodeNum {
	/**
	 * 防伪码已过期
	 */
	public static final String CHECK_INVALID = "022";
	/**
	 * 防伪码校验失败
	 */
	public static final String CHECK_FAIL = "021";
	/**
	 * 分类关联应用
	 */
	public static final String PASSWORD_ERROR = "020";

	public static final String STRATEGY_ID_NOT_EXIST = "016";

	public static final String STRATEGY_NAME_EXIST = "015";

	public static final String REQUEST_ERROR = "001";

	public static final String CONFIT_ERROR = "019";

	public static final String PACKAGE_ERROR = "009";

	public static final String DELETE_APP_LAN = "020";

	public static final String USER_NOT_EXITS = "014";

	public static final String TYPE_RE_APP = "017";

	public static final String APP_ID_NOT_EXIST = "010";

	public static final String APP_EXITS = "018";
	/**
	 * SN不存在
	 */
	public static final String TERMINAL_SN_NOT_EXITS = "004";
	/**
	 * 分类id不存在
	 */
	public static final String TYPE_ID_NOT_EXITS = "003";
	/**
	 * 分类名称已存在
	 */
	public static final String TYPE_NAME_EXITS = "002";

	public static final String TOP_APP_ID_NOT_EXISTS = "005";
	/**
	 * 类别名不符合要求
	 */
	public static final String TYPE_NAME_ERROR = "018";
	/**
	 * 成功
	 */
	public static final String IS_SUCCESS = "0";

	/**
	 * 网络异常
	 */
	public static final String NETWORK_ANOMALIES = "-1";

	/**
	 * 参数异常
	 */
	public static final String PARAM_ANOMALIES = "1";

	/**
	 * 没有权限
	 */
	public static final String NO_ACCESS = "403";

	/**
	 * 系统内部错误（未知错误）
	 */
	public static final String SYSTEM_ERROR = "500";

	/**
	 * 系统过载保护
	 */
	public static final String OVERLOAD_PROTECTION = "800";

	/**
	 * 数据库错误
	 */
	public static final String DATABASE_ERROR = "1000";

	/**
	 * 用户已被禁用
	 */
	public static final String USER_STATUS_ERROR = "ams00010";

	/**
	 * 用户名或者密码错误
	 */
	public static final String USER_NAMEORPWD_ERROR = "ams00011";

	/**
	 * 用户名已存在
	 */
	public static final String USER_NAME_EXSIST = "ums00011";

	/**
	 * 用户名已存在
	 */
	public static final String USER_NAME_TOOSHORT = "ums00012";

	/**
	 * token已失效，请重新登陆！
	 */
	public static final String TOKEN_VALID = "ams00001";

	/**
	 * 关联关系已存在
	 */
	public static final String RELATION_EXIST = "ams00601";

	/**
	 * 数据字典该分类下字典Key已存在
	 */
	public static final String DICT_NAME_EXIST = "ams00601";
	
	/**
	 * 分组Code已经存在
	 */
	public static final String GROUP_CODE_EXIST = "ams00602";
	/**
	 * 存在关联决策关系
	 */
	public static final String GROUP_IS_REF_DELIVER = "ams00603";
	/**
	 * 请求TAS失败
	 */
	public static final String REQUEST_TAS_FAIL = "TAS00001";
}
