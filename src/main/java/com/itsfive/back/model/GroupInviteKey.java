package com.itsfive.back.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupInviteKey implements Serializable{
	    @Column(name = "group_id")
	    Long groupId;
	    
	    @Column(name = "itoken")
	    String itoken;
	    
	    public GroupInviteKey(Long groupId,String itoken) {
	    	this.groupId = groupId;
	    	this.itoken = itoken;
	    }
	    
	    public GroupInviteKey() {
	    	
	    }

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public String getItoken() {
			return itoken;
		}

		public void setItoken(String itoken) {
			this.itoken = itoken;
		}
	    
	    
}
