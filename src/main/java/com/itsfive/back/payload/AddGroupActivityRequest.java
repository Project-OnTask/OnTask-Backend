package com.itsfive.back.payload;

public class AddGroupActivityRequest {
	private long groupId;
	
	private long added_by;
	
	private String content;

	
	public AddGroupActivityRequest(long groupId, long added_by, String content) {
		super();
		this.groupId = groupId;
		this.added_by = added_by;
		this.content = content;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getAdded_by() {
		return added_by;
	}

	public void setAdded_by(long added_by) {
		this.added_by = added_by;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
