package com.itsfive.back.payload;

public class GetGroupAdminResponse {
	
	private Long userId;
	
	private String fname;
	
	private String lname;
	
	private String emailHash; 
	
	private String propicURL;

	public GetGroupAdminResponse(Long userId,String fname, String lname, String emailHash,String propicURL) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.emailHash = emailHash;
		this.propicURL = propicURL;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
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

	public String getPropicURL() {
		return propicURL;
	}

	public void setPropicURL(String propicURL) {
		this.propicURL = propicURL;
	}
	
	
}
