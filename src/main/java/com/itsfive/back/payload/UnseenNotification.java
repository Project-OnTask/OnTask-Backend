package com.itsfive.back.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itsfive.back.model.GroupActivity;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UnseenNotification {
	private long id;
	
	private GroupActivity activity;

	public UnseenNotification(long id, GroupActivity activity) {
		super();
		this.id = id;
		this.activity = activity;
	}

	public long getN_id() {
		return id;
	}

	public void setN_id(long id) {
		this.id = id;
	}

	public GroupActivity getActivity() {
		return activity;
	}

	public void setActivity(GroupActivity activity) {
		this.activity = activity;
	}
}
