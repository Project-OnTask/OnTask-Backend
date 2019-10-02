package com.itsfive.back.payload;

import java.util.Date;

public class EditTaskDueDateRequest {
	private long editedBy;
		
	private Date date;

	public EditTaskDueDateRequest(long editedBy, Date date) {
		super();
		this.editedBy = editedBy;
		this.date = date;
	}

	public long getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(long editedBy) {
		this.editedBy = editedBy;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
