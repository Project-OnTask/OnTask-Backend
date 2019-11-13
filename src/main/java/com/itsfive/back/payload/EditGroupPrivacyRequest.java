package com.itsfive.back.payload;

public class EditGroupPrivacyRequest {

	private long groupId;
	
	private boolean status;
	
	private long changedById;

	public EditGroupPrivacyRequest(long groupId, boolean status, long changedById) {
		super();
		this.groupId = groupId;
		this.status = status;
		this.changedById = changedById;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getChangedById() {
		return changedById;
	}

	public void setChangedById(long changedById) {
		this.changedById = changedById;
	}
	
	
}
