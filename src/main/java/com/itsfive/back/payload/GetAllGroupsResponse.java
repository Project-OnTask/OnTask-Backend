package com.itsfive.back.payload;

public class GetAllGroupsResponse {
	private long groupId;
	
	private String name;
	
	private String role;

	public GetAllGroupsResponse(long groupId, String name, String role) {
		super();
		this.groupId = groupId;
		this.name = name;
		this.role = role;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
