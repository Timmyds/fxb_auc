package com.fxb.work.util;


public class APIResultCode {

	
	/**
	 * 操作成功
	 */
	public static String RESULT_SUCCESS = "0:操作成功";
	/**
	 * 操作失败
	 */
	public static String RESULT_FAIL = "1:操作失败";
	/**
	 * 参数错误
	 */
	public static String RESULT_FAIL_PARAM_ERROR = "2:参数错误";
	/**
	 * 用户名已经存在
	 */
	public static String RESULT_FAIL_NAME_EXIST = "3:用户名已经存在";
	/**
	 * 用户名或密码错误
	 */
	public static String RESULT_FAIL_LOGIN_FAILED = "4:用户名或密码错误";
	/**
	 * 用户名不存在 
	 */
	public static String RESULT_FAIL_USER_NOT_EXIST = "5:用户名或密码错误";
	/**
	 * 参数不能为空
	 */
	public static String RESULT_FAIL_PARAM_NULL = "6:参数不能为空";
	/**
	 * 用户角色不存在
	 */
	public static String RESULT_FAIL_ROLE_NOT_EXIST = "7:角色不存在";
	/**
	 * 查询结果为空
	 */
	public static String RESULT_FAIL_QUERY_NULL = "8:查询结果为空";
	
	/**
	 * 账户被冻结
	 */
	public static String RESULT_FAIL_FREEZE = "9:账户已被冻结";

}
