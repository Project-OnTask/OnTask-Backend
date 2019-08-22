package com.itsfive.back.payload;

import java.time.Instant;

public class GetCommentResponse {

    private Long id;
	
	private String fname;
	
	private Instant createdAt;
	
    private String content;

	public GetCommentResponse(Long id, String fname, Instant createdAt, String content) {
		super();
		this.id = id;
		this.fname = fname;
		this.createdAt = createdAt;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFName() {
		return fname;
	}

	public void setFName(String fname) {
		this.fname = fname;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
