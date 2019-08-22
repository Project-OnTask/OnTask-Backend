package com.itsfive.back.model;

import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "task_asignee")
public class TaskAsignee extends DateAudit{
	@EmbeddedId
	TaskAsigneeKey id;	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "added_by", nullable = false)
    @JsonIgnore
	private User addedBy;

    
	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public TaskAsignee() {
		super();
	}
	
	public TaskAsignee(TaskAsigneeKey id) {
		super();
		this.id = id;
	}

	public TaskAsigneeKey getId() {
		return id;
	}

	public void setId(TaskAsigneeKey id) {
		this.id = id;
	}

}
