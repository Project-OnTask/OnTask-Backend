package com.itsfive.back.model;

public class HTMLMail {
	private String subject;
	private final String to;
	private String content;

    public HTMLMail(String to,String subject,String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getTo() {
        return this.to;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }
}
