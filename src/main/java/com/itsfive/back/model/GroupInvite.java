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
	 GroupMembersKey id;
	
	 @ManyToOne
	 @MapsId("user_id")
	 @JoinColumn(name = "user_id")
	 User user;
	 
	 public GroupInvite(GroupMembersKey id){
		 this.id = id;
	 }
	 
	 public GroupInvite() {
		 
	 }
	 
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

	public String getIToken() {
		return itoken;
	}

	public void setIToken(String iToken) {
		itoken = iToken;
	}

	@ManyToOne
	 @MapsId("group_id")
	 @JoinColumn(name = "group_id")
	 Group group;
	 
	 String itoken;

}
