package com.itsfive.back.payload;

import java.util.Date;

public class CreateTaskRequest {
	private Long createdBy;

	private String name;
	
	private String description;
	
	private Date dueDate;
	
	private Long groupId;

	public CreateTaskRequest(Long createdBy, String name, String description, Date dueDate, Long groupId) {
		super();
		this.createdBy = createdBy;
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.groupId = groupId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}	
