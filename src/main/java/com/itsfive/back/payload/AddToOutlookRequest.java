package com.itsfive.back.payload;

import java.util.Date;

public class AddToOutlookRequest {

	private String subject;
	
	private Dates start;
	
	private Dates end;

	public AddToOutlookRequest(String subject, Dates start, Dates end) {
		super();
		this.subject = subject;
		this.start = start;
		this.end = end;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Dates getStart() {
		return start;
	}

	public void setStart(Dates start) {
		this.start = start;
	}

	public Dates getEnd() {
		return end;
	}

	public void setEnd(Dates end) {
		this.end = end;
	}
	
	
}

class Dates{
	private Date dateTime;
	
	private String timeZone;

	public Dates(Date dateTime, String timeZone) {
		super();
		this.dateTime = dateTime;
		this.timeZone = timeZone;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}

