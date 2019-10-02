package com.itsfive.back.payload;

public class UserContactUpdateRequest {
	
	private long userId;
	
	private String mobile;
	
	private String email;

	public UserContactUpdateRequest(long userId, String mobile, String email) {
		super();
		this.userId = userId;
		this.mobile = mobile;
		this.email = email;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
