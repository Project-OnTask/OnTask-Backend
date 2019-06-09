package com.itsfive.back.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.itsfive.back.model.audit.DateAudit;

@Entity
public class GroupMember extends DateAudit{
	 @EmbeddedId
	 GroupMembersKey id;
	
	 @ManyToOne
	 @MapsId("user_id")
	 @JoinColumn(name = "user_id")
	 User user;
	 
	 public GroupMember(GroupMembersKey id){
		 this.id = id;
	 }
	 
	 public GroupMember() {
		 
	 }
	 
	 @ManyToOne
	 @MapsId("group_id")
	 @JoinColumn(name = "group_id")
	 Group group;
	 
	 String role;

	public GroupMembersKey getId() {
		return id;
	}

	public void setId(GroupMembersKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
