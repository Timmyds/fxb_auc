/*package com.fxb.work.util.exception;

import com.fxb.work.util.Constants;
import com.fxb.work.util.bean.Result;


@SuppressWarnings("rawtypes")
public class CompositeException extends Exception {
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	public Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	*//**
	 * Creates object.
	 *//*
	public CompositeException() {
		super(MessageResource.SYSTEM_ERROR);
	}

	@SuppressWarnings("deprecation")
	public CompositeException(Result result, Throwable e) {
		super(e);
		result.setCode(Constants.FAIL);
		result.setMsg(Constants.FAIL_MSG);
		if (e instanceof FunctionException) {
			result.error(result.getStack(e));
			this.result = ((FunctionException) e).getResult();
		} else if (e instanceof CompositeException) {
			result.error(result.getStackTrace(e));
			this.result = ((CompositeException) e).getResult();
		} else {
			result.error(result.getStackTrace(e));
			this.result = result;
		}
	}

	@SuppressWarnings("deprecation")
	public CompositeException(Result result, Throwable e, String message) {
		super(message, e);
		result.setCode(Constants.FAIL);
		result.setMsg(Constants.FAIL_MSG);
		if (e instanceof FunctionException) {
			result.error(result.getStack(e));
			this.result = ((FunctionException) e).getResult();
		} else if (e instanceof CompositeException) {
			result.error(result.getStackTrace(e));
			this.result = ((CompositeException) e).getResult();
		} else {
			result.error(result.getStackTrace(e));
			this.result = result;
		}
	}

}
*/