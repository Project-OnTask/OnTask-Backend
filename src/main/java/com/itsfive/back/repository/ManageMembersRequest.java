package com.itsfive.back.repository;

public class ManageMembersRequest {
	private long addedById;
	
	private long groupId;
	
	private long userId;

	
	public ManageMembersRequest(long addedById,long groupId, long userId) {
		super();
		this.addedById = addedById;
		this.groupId = groupId;
		this.userId = userId;
	}

	public long getAddedById() {
		return addedById;
	}

	public void setAddedById(long addedById) {
		this.addedById = addedById;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
