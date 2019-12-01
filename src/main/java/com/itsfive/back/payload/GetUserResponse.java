package com.itsfive.back.payload;

public class GetUserResponse {
	private long userId;
	
	private String fname;
	
	private String lname;
	
	private String email;

	private boolean isAppUser;

	private String propicURL;
	
	private String emailHash;
	
	public GetUserResponse(long userId, String fname, String lname,String email,boolean isAppUser,String propicURL,String emailHash) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.isAppUser = isAppUser;
		this.email = email;
		this.propicURL = propicURL;
		this.emailHash = emailHash;
	}
	
	public GetUserResponse(long userId, String fname, String lname,String email,String propicURL,String emailHash) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.propicURL = propicURL;
		this.emailHash = emailHash;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPropicURL() {
		return propicURL;
	}

	public void setPropicURL(String propicURL) {
		this.propicURL = propicURL;
	}
	
	public boolean isAppUser() {
		return isAppUser;
	}

	public void setAppUser(boolean isAppUser) {
		this.isAppUser = isAppUser;
	}
}
