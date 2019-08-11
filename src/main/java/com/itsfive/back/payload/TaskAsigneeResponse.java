package com.itsfive.back.payload;

public class TaskAsigneeResponse {
	private String fname;
	
	private String lname;
	
	private long userId;

	public TaskAsigneeResponse(String fname, String lname, long userId) {
		super();
		this.fname = fname;
		this.lname = lname;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
