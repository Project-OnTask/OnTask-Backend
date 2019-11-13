package com.itsfive.back.payload;

public class SendInvitationEmailRequest {
	private long createdById;
	
	private long receiverId;
	
	private long groupId;

	public SendInvitationEmailRequest(long createdById, long receiverId, long groupId) {
		super();
		this.createdById = createdById;
		this.receiverId = receiverId;
		this.groupId = groupId;
	}

	public long getcreatedById() {
		return createdById;
	}

	public void setcreatedById(long createdById) {
		this.createdById = createdById;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	

}
