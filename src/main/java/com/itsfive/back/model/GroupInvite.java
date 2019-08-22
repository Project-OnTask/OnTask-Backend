package com.itsfive.back.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name="group_invite")
public class GroupInvite extends DateAudit{
	 @EmbeddedId
	 GroupInviteKey id;
	
	 //@ManyToOne
	 //@MapsId("created_by")
	 //@JoinColumn(name = "created_by")
	    @ManyToOne
	private User createdBy;
	 
		@ManyToOne
		 @MapsId("group_id")
		 @JoinColumn(name = "group_id")
		 private Group group;
		 

	 public GroupInvite(GroupInviteKey id,User createdBy){
		 this.id = id;
		 this.createdBy = createdBy;
		 
	 }
	 
	 public GroupInvite() {
		 
	 }
	 
	 public GroupInviteKey getId() {
		return id;
	}

	public void setId(GroupInviteKey id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
