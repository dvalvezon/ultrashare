package com.ultrashare.type;

public enum PropertyType {
	MAIL_CONFIGS("/email.properties"),
	FTP_CONFIGS("/ftp.properties");

	private String uri;

	private PropertyType(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
}
