package com.itsfive.back.payload;

public class EditGroupDataRequest {
	private long editedBy;
	
	private String name;
	
	private String description;

	public EditGroupDataRequest(long editedBy, String name, String description) {
		super();
		this.editedBy = editedBy;
		this.name = name;
		this.description = description;
	}

	public long getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(long editedBy) {
		this.editedBy = editedBy;
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
	
	

}
