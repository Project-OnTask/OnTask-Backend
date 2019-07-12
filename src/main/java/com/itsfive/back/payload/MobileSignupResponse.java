package com.itsfive.back.payload;

public class MobileSignupResponse {
	private long userId;
	
	private String requestId;
	
	private boolean success;
	
	private String message;

	public MobileSignupResponse(long userId,String requestId, boolean success, String message) {
		super();
		this.userId = userId;
		this.requestId = requestId;
		this.success = success;
		this.message = message;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
