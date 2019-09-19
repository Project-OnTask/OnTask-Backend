package com.itsfive.back.payload;

import com.itsfive.back.model.User;

public class UserWebPresenceUpdateReq {

	private String websiteLink;

	private String twitterLink;

	private String stackOverflowLink;

	private String githubLink;

	private String linkedInLink;

	public UserWebPresenceUpdateReq( String websiteLink, String twitterLink, String stackOverflowLink,
			String githubLink, String linkedInLink) {
		super();
		this.websiteLink = websiteLink;
		this.twitterLink = twitterLink;
		this.stackOverflowLink = stackOverflowLink;
		this.githubLink = githubLink;
		this.linkedInLink = linkedInLink;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getStackOverflowLink() {
		return stackOverflowLink;
	}

	public void setStackOverflowLink(String stackOverflowLink) {
		this.stackOverflowLink = stackOverflowLink;
	}

	public String getGithubLink() {
		return githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

	public String getLinkedInLink() {
		return linkedInLink;
	}

	public void setLinkedInLink(String linkedInLink) {
		this.linkedInLink = linkedInLink;
	}

	
}
