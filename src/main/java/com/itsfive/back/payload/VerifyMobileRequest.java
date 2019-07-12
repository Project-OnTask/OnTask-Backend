package com.itsfive.back.payload;

public class VerifyMobileRequest {
	private Long userId;
	
	private String code;
	
	private String requestId;

	public VerifyMobileRequest(Long userId, String code, String requestId) {
		super();
		this.userId = userId;
		this.code = code;
		this.requestId = requestId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}
