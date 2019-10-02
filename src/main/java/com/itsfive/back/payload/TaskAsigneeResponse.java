package com.itsfive.back.payload;

public class TaskAsigneeResponse {
	private String fname;
	
	private String lname;
	
	private String propic;
	
	private String emailHash;
	
	private long userId;

	public TaskAsigneeResponse(String fname, String propic,String emailHash,String lname, long userId) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.propic = propic;
		this.emailHash = emailHash;
		this.userId = userId;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public String getPropic() {
		return propic;
	}

	public void setPropic(String propic) {
		this.propic = propic;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
