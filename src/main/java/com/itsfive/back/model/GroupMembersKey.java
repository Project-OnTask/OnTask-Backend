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
	    
	    public GroupMembersKey(Long userId,Long groupId) {
	    	this.userId = userId;
	    	this.groupId = groupId;
	    }
	    
	    public GroupMembersKey() {
	    	
	    }

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}
}
