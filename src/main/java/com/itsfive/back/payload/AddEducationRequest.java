package com.itsfive.back.payload;

import java.util.Date;

public class AddEducationRequest {
	private long userId;

	private String institute;

	private String description;

	private Date from;

	private Date to;

	private boolean isStudying;

	public AddEducationRequest(long userId, String institute, String description, Date from, Date to,
			boolean isStudying) {
		super();
		this.userId = userId;
		this.institute = institute;
		this.description = description;
		this.from = from;
		this.to = to;
		this.isStudying = isStudying;
	}

	public boolean isStudying() {
		return isStudying;
	}

	public void setStudying(boolean isStudying) {
		this.isStudying = isStudying;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
