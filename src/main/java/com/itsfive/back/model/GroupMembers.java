package com.itsfive.back.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.itsfive.back.model.audit.DateAudit;

@Entity
public class GroupMembers extends DateAudit{
	@EmbeddedId
	GroupMembersKey id;
	
	 @ManyToOne
	 @MapsId("user_id")
	 @JoinColumn(name = "user_id")
	 User user;
	 
	 @ManyToOne
	 @MapsId("group_id")
	 @JoinColumn(name = "group_id")
	 Group group;
	 
	 String role;
}
