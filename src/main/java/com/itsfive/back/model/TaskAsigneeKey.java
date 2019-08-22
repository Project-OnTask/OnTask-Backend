package com.itsfive.back.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class TaskAsigneeKey implements Serializable{
	@Column(name = "user_id")
    Long userId;
 
    @Column(name = "task_id")
    Long taskId;

	public TaskAsigneeKey() {
		super();
	}

	public TaskAsigneeKey(Long userId, Long taskId) {
		super();
		this.userId = userId;
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

    
}
