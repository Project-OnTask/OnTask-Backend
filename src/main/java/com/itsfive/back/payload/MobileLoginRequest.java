package com.itsfive.back.payload;

public class MobileLoginRequest {

	private long userId;
	
	private String deviceId;
	
	private String token;

	public MobileLoginRequest(long userId, String token,String deviceId) {
		super();
		this.userId = userId;
		this.deviceId = deviceId;
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
