package com.itsfive.back.payload;

public class AddNoticeRequest {
	private long userId;
	
	private long groupId;
	
	private String title;
	
	private String content;

	public AddNoticeRequest(long userId, long groupId, String title,String content) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
