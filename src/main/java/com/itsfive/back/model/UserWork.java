package com.itsfive.back.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "user_work")
public class UserWork extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne
    @JoinColumn
    private User user;
	
	private String title;
	
	private String place;
	
	private Date startDate;
	
	private Date endDate;

	private String description;
	
	private boolean isWorking;
	
	public UserWork() {
		
	}
	
	public UserWork(User user, String title, String place, Date from, Date to,String description,boolean isWorking) {
		super();
		this.user = user;
		this.title = title;
		this.place = place;
		this.startDate = from;
		this.endDate = to;
		this.description = description;
		this.isWorking = isWorking;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return place;
	}

	public void setCompany(String place) {
		this.place = place;
	}

	public Date getFrom() {
		return startDate;
	}

	public void setFrom(Date from) {
		this.startDate = from;
	}

	public Date getTo() {
		return endDate;
	}

	public void setTo(Date to) {
		this.endDate = to;
	}
}
