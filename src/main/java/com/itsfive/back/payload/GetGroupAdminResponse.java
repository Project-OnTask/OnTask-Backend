package com.itsfive.back.payload;

public class GetGroupAdminResponse {
	
	private String fname;
	
	private String lname;
	
	private String propicURL;

	public GetGroupAdminResponse(String fname, String lname, String propicURL) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.propicURL = propicURL;
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
