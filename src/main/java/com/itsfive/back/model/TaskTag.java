package com.itsfive.back.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "task_tag")
public class TaskTag extends DateAudit{
	 @EmbeddedId
	 TaskTagKey id;
	 
	 @ManyToOne
	 @MapsId("task_id")
	 @JoinColumn(name = "task_id")
	 Task task;

	public TaskTagKey getId() {
		return id;
	}

	public void setId(TaskTagKey id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
