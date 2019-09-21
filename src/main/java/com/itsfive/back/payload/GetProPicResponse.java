package com.itsfive.back.payload;

public class GetProPicResponse {
	private String propicURL;
	
	private String emailHash;

	public GetProPicResponse(String propicURL, String emailHash) {
		super();
		this.propicURL = propicURL;
		this.emailHash = emailHash;
	}

	public String getPropicURL() {
		return propicURL;
	}

	public void setPropicURL(String propicURL) {
		this.propicURL = propicURL;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	
	

}
