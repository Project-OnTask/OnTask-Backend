package com.itsfive.back.payload;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {
	@NotBlank
    private String password;
	
	@NotBlank
    private String token;

	public PasswordResetRequest(@NotBlank String password, @NotBlank String token) {
		super();
		this.password = password;
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	


}
