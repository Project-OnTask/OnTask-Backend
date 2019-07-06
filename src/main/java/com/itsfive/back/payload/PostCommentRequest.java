package com.itsfive.back.payload;

public class PostCommentRequest {
	private long userId;
	
	private long taskId;
	
	private String content;

	public PostCommentRequest(long userId, long taskId, String content) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
