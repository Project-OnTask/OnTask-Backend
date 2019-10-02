package com.itsfive.back.payload;

public class RemoveTaskAsigneeRequest {
	private long removedBy;
	
	private long taskId;

	public RemoveTaskAsigneeRequest(long removedBy, long taskId) {
		super();
		this.removedBy = removedBy;
		this.taskId = taskId;
	}

	public long getRemovedBy() {
		return removedBy;
	}

	public void setRemovedBy(long removedBy) {
		this.removedBy = removedBy;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	

}
