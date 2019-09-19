package com.itsfive.back.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "user_education")
public class UserEducation extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne
    @JoinColumn
    private User user;
	
	private String institute;
	
	@Lob
	private String description;
	
    @Temporal(TemporalType.DATE)
	private Date startDate;
	
    @Temporal(TemporalType.DATE)
	private Date endDate;

	private boolean isPresent;
	
	public UserEducation() {
		super();
	}
	
	public UserEducation(User user, String institute, Date startDate, Date endDate,boolean isPresent) {
		super();
		this.user = user;
		this.institute = institute;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isPresent = isPresent;
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

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
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

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}
	
	
}
