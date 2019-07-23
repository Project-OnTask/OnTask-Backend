package com.itsfive.back.payload;

import java.time.Instant;
import java.util.Date;

public class TaskResourceResponse {
	
	private long taskResId;
	
	private long userId;
	
	private String fname;
	
	private Instant addedOn;

	private String uri;

	public TaskResourceResponse(long taskResId, long userId, String fname, Instant instant, String uri) {
		super();
		this.taskResId = taskResId;
		this.userId = userId;
		this.fname = fname;
		this.addedOn = instant;
		this.uri = uri;
	}

	public long getTaskResId() {
		return taskResId;
	}

	public void setTaskResId(long taskResId) {
		this.taskResId = taskResId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return fname;
	}

	public void setUsername(String fname) {
		this.fname = fname;
	}

	public Instant getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Instant addedOn) {
		this.addedOn = addedOn;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
