package com.itsfive.back.payload;

public class EditTaskDescRequest {
	private long editedBy;
	
	private Long taskId;
	
	private String description;

	
	public EditTaskDescRequest(Long editedBy,Long groupId, String description) {
		super();
		this.editedBy = editedBy;
		this.taskId = groupId;
		this.description = description;
	}

	public long getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(long editedBy) {
		this.editedBy = editedBy;
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
