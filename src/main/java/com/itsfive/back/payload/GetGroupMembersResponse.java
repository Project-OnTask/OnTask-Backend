package com.itsfive.back.payload;

public class GetGroupMembersResponse {
	private long userId;
	
	private String fname; 
	
	private String lname;
	
	private String PropicURL;
	
	private String role;

	
	public GetGroupMembersResponse(long userId, String fname, String lname, String propicURL, String role) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		PropicURL = propicURL;
		this.role = role;
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

	public String getPropicURL() {
		return PropicURL;
	}

	public void setPropicURL(String propicURL) {
		PropicURL = propicURL;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
