package com.itsfive.back.payload;

public class GetAllGroupsResponse {
	private long groupId;
	
	private String name;
	
	private String role;
	
	private String lastActivity;

	public GetAllGroupsResponse(long groupId, String name, String role,String lastActivity) {
		super();
		this.groupId = groupId;
		this.name = name;
		this.role = role;
		this.lastActivity = lastActivity;
	}

	public String getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
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
