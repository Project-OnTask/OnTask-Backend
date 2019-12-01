package com.itsfive.back.payload;

public class CreateGroupRequest {
	private Long userId;
	
	private String name;
	
	private String description;
	
	private boolean isPrivate;

	private Long members[];
	
	public CreateGroupRequest(Long userId, String name, String description,boolean isPrivate,Long[] members) {
		super();
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.members = members;
		this.isPrivate = isPrivate;
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

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public Long[] getMembers() {
		return members;
	}

	public void setMembers(Long[] members) {
		this.members = members;
	}
}
