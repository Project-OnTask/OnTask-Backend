package com.itsfive.back.payload;

public class AddTaskAsigneeRequest {
	private long taskId;
	
	private long groupId;
	
	private long userId;
	
	private long addedById;

	public AddTaskAsigneeRequest(long taskId, long groupId, long userId, long addedById) {
		super();
		this.taskId = taskId;
		this.groupId = groupId;
		this.userId = userId;
		this.addedById = addedById;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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
