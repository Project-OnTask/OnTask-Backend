package com.itsfive.back.payload;

public class AddNoticeRequest {
	private long userId;
	
	private long groupId;
	
	private String content;

	public AddNoticeRequest(long userId, long groupId, String content) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.content = content;
	}

	public AddNoticeRequest() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
