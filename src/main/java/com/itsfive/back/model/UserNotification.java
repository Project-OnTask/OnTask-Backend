package com.itsfive.back.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_notifications")
public class UserNotification {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
		
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_activity_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private GroupActivity groupActivity;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private User user;
	
	private boolean seen;

	public UserNotification(GroupActivity groupActivity, User user) {
		super();
		this.groupActivity = groupActivity;
		this.user = user;
		this.seen = false;
	}
	
	public UserNotification() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GroupActivity getGroupActivity() {
		return groupActivity;
	}

	public void setGroupActivity(GroupActivity groupActivity) {
		this.groupActivity = groupActivity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
}
