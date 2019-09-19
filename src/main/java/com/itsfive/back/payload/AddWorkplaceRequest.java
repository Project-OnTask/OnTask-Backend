package com.itsfive.back.payload;

import java.time.Instant;
import java.util.Date;

public class AddWorkplaceRequest {
	private long userId;
	
	private String title;
	
	private String w_place;
	
	private Date from;
	
	private Date to;
	
	private String description;

	private boolean isWorking;
	
	public AddWorkplaceRequest() {
		super();
	}

	public AddWorkplaceRequest(long userId, String title, String place, Date from, Date to,String description,boolean isWorking) {
		super();
		this.userId = userId;
		this.title = title;
		this.w_place = place;
		this.from = from;
		this.to = to;
		this.description = description;
		this.isWorking = isWorking;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getW_place() {
		return w_place;
	}

	public void setW_place(String w_place) {
		this.w_place = w_place;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return w_place;
	}

	public void setCompany(String company) {
		this.w_place = company;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
