package com.itsfive.back.payload;

public class UpdatePasswordRequest {
	
	private Long userId;
	private String password;
	private String newPassword;
	
	public UpdatePasswordRequest(Long userId, String password, String newPassword) {
		super();
		this.userId = userId;
		this.password = password;
		this.newPassword = newPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
