package com.itsfive.back.payload;

public class BasicUserInfoUpdateReq {
	
	private String fname;
	
	private String lname;
	
	private String username;
	
	private String bio;

	public BasicUserInfoUpdateReq(String fname, String lname, String username, String bio) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.bio = bio;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
