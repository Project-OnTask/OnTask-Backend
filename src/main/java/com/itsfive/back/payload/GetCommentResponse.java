package com.itsfive.back.payload;

import java.time.Instant;

public class GetCommentResponse {

    private Long id;
	
	private String username;
	
	private Instant createdAt;
	
    private String content;

	public GetCommentResponse(Long id, String username, Instant createdAt, String content) {
		super();
		this.id = id;
		this.username = username;
		this.createdAt = createdAt;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
