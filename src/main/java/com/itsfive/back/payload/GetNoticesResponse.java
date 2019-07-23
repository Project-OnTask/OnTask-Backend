package com.itsfive.back.payload;

import java.time.Instant;
import java.util.Date;

public class GetNoticesResponse {
	
	private long id;
	
	private String title;
	
	private Instant date;

	public GetNoticesResponse(long id, String title, Instant instant) {
		super();
		this.id = id;
		this.title = title;
		this.date = instant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}
}
