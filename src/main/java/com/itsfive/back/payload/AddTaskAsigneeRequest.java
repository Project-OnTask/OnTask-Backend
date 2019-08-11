package com.itsfive.back.payload;

public class AddTaskAsigneeRequest {
	private long taskId;
	
	private String username;
	
	private long addedById;

	public AddTaskAsigneeRequest(long taskId, String username, long addedById) {
		super();
		this.taskId = taskId;
		this.username = username;
		this.addedById = addedById;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getAddedById() {
		return addedById;
	}

	public void setAddedById(long addedById) {
		this.addedById = addedById;
	}
}
