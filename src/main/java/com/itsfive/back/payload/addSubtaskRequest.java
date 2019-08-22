package com.itsfive.back.payload;

public class addSubtaskRequest {
	
	private long taskId;
	
	private String name;

	public addSubtaskRequest(long taskId, String name) {
		super();
		this.taskId = taskId;
		this.name = name;
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
