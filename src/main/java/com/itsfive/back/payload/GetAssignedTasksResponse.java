package com.itsfive.back.payload;

import com.itsfive.back.model.UserTask;

public class GetAssignedTasksResponse {
	
	private long groupId;
	
	private UserTask task;

	public GetAssignedTasksResponse(long groupId, UserTask task) {
		super();
		this.groupId = groupId;
		this.task = task;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public UserTask getTask() {
		return task;
	}

	public void setTask(UserTask task) {
		this.task = task;
	}
	
	

}
