package com.fxb.world.service.util;

/**
 * 存储常量字段的
 *
 */
public class UserConstants {
	
	
	/************************************ 公共部分 *************************************/
	public static String RESULT_ILLEGAL_ARGUMENT_FORMAT = "101:{key}参数类型不正确"; 
	public static String RESULT_LASTEST_VERSION = "100:当前版本已经是最新版本";
	public static String RESULT_ILLEGAL_ARGUMENT_LEN = "102:{key}参数长度不正确";
	
	public static final String CHARSET_UTF8 = "UTF-8";
	public static String PHONE_PREFIX = "phone_tsh_"; // 手机短信key
	
	public static String SECURITY_QUESTION_PREFIX = "security_question_tsh_"; // 密保key
	
	public static String AUC_IMAGE_TSH_CODE = "image_tsh_code"; // 手机短信key
	public static  final Integer PHONE_EXPIRE_TIME = 60 * 5; // 验证码时间2分钟

	public static final String REDIS_KEY_LEAF_LOGIN_ERROR_ID = "login_error_";
	public static final Integer LOGIN_CODE_EXPIRE_TIME = 60 * 60 * 24; // 错误密码时间间隔
	
	public static final String TSH_MACHINE_WHITE_LIST = "tsh_machine_white_list_";//序列号白名单key
	
	public static final String TSH_USER_LOGIN_PROTECTION = "tsh_user_login_protection_";//登录保护key
	
	public static final int PWD_ERROR_COUNT_MIX = 3;//密码错误次数
	public static final int PWD_ERROR_COUNT_MAX = 10;//密码错误
	
	public static  final Integer PHONE_EXPIRE_TIME_USER = 60 * 10; // 设置密保验证码时间5分钟
	
	public static final String REDIS_KEY_LEAF_LOGIN_TIME_STAMP = "login_time_stamp_";//登录是时间戳
	
	public static final Integer REDIS_KEY_LEAF_LOGIN_TIME_STAMP_VALID =60*60;//时间有效时间
	
	public static final String REDIS_KEY_LEAF_FORGETPWD_ERROR = "forgetPwd_error_";//密保找回密码错误次数
	public static final int FORGETPWD_ERROR_COUNT_MAX = 5;//密保找回密码错误次数超过5次该账户锁定

	public static final String NEW_SYSTEM_DATA = "2016-06-15";//系统上线时间
}
