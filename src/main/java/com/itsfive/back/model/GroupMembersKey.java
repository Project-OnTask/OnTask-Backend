package com.itsfive.back.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupMembersKey implements Serializable{
	 @Column(name = "user_id")
	    Long userId;
	 
	    @Column(name = "group_id")
	    Long groupId;
}
