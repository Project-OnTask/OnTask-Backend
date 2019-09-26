package com.itsfive.back.payload;

public class addSubtaskRequest {
	long addedBy;
	
	private long taskId;
	
	private String name;

	public addSubtaskRequest(long addedBy,long taskId, String name) {
		super();
		this.addedBy = addedBy;
		this.taskId = taskId;
		this.name = name;
	}

	public long getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(long addedBy) {
		this.addedBy = addedBy;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
