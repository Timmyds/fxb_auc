package com.fxb.work.util.exception;

public class MessageResource {

	/**
	 * Create Object.
	 */
	private MessageResource(){
		super();
	}

	/**
	 * System error messages
	 */
	public static final String SYSTEM_ERROR				= "error.efos.systemError";
	/**
	 * Function error messages
	 */
	public static final String FUNCTION_ERROR			= "error.efos.functionError";
	
	
	
	public static final String PARAMETERS_ERROR			= "参数错误";
	
	
	
	public static final String RESULT_NOT_FIND			= "未找到记录";
	
	public static final String UPDATE_FAIL				= "更新失败";
	
	public static final String DELETE_FAIL				= "删除失败";
	
	public static final String ADD_FAIL				= "添加失败";
	/**
	 * Generic application error messages
	 */
	public static final String FAIL_TO_READ_RECORD			= "error.efos.failToReadRecord";
	
	public static final String PASSWORD_ERROR			= "密码错误";
	
	public static final String LOGIN_ACCIONT_IS_NULL = "登录账户为空";
	
	public static final String LOGIN_PASSWORD_IS_NULL = "登录密码为空";
	
	public static final String ACCOUNT_NOT_FIND			= "此用户不存在";
}
