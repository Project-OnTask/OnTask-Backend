package com.itsfive.back.payload;

public class AddTaskAsigneeRequest {
	private long taskId;
	
	private long userId;
	
	private long addedById;

	public AddTaskAsigneeRequest(long taskId, long userId, long addedById) {
		super();
		this.taskId = taskId;
		this.userId = userId;
		this.addedById = addedById;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getAddedById() {
		return addedById;
	}

	public void setAddedById(long addedById) {
		this.addedById = addedById;
	}
}
