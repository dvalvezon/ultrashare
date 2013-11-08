package com.ultrashare.component.vo;

public final class MailVO {

	private final String[] recipients;
	private final String subject;
	private final String text;

	public MailVO(String[] recipients, String subject, String text) {
		this.recipients = recipients;
		this.subject = subject;
		this.text = text;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
}
