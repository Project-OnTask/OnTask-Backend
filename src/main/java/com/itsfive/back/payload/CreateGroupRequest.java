package com.itsfive.back.payload;

public class CreateGroupRequest {
	private Long userId;
	
	private String name;
	
	private String description;
	
	private Long members[];
	
	public CreateGroupRequest(Long userId, String name, String description,Long[] members) {
		super();
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.members = members;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long[] getMembers() {
		return members;
	}

	public void setMembers(Long[] members) {
		this.members = members;
	}
}
