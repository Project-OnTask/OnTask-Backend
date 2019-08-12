package com.itsfive.back.model;

import java.io.Serializable;

import javax.persistence.Column;

public class TaskTagKey implements Serializable{
	@Column(name = "tag")
    String tag;
 
    @Column(name = "task_id")
    Long taskId;

	public TaskTagKey(String tag, Long taskId) {
		super();
		this.tag = tag;
		this.taskId = taskId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
}
