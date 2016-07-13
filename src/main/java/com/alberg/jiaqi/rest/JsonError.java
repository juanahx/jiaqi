package com.alberg.jiaqi.rest;

public class JsonError {
	private String message;
	private String errorCode;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public JsonError(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	public JsonError() {
		super();
	}

	
}
