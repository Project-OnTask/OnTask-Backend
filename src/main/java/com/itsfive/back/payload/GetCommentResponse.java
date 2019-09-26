package com.itsfive.back.payload;

import java.time.Instant;

public class GetCommentResponse {

    private Long id;
	
    private String emailHash;
    
    private String propicURL;
    
	private String fname;
	
	private Instant createdAt;
	
    private String content;

	public GetCommentResponse(Long id, String emailHash,String propicURL,String fname, Instant createdAt, String content) {
		super();
		this.id = id;
		this.emailHash = emailHash;
		this.propicURL = propicURL;
		this.fname = fname;
		this.createdAt = createdAt;
		this.content = content;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public String getPropicURL() {
		return propicURL;
	}

	public void setPropicURL(String propicURL) {
		this.propicURL = propicURL;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
