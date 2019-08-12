package com.itsfive.back.payload;

public class EditTaskDescRequest {
	private Long taskId;
	
	private String description;

	
	public EditTaskDescRequest(Long groupId, String description) {
		super();
		this.taskId = groupId;
		this.description = description;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
