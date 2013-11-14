package com.ultrashare.component.vo;

import com.ultrashare.model.Upload;

public class ShareVO {

	private final String[] recipients;
	private final String sharerName;
	private final String sharerEmail;
	private final String shareMessage;
	private final Upload upload;

	public ShareVO(String[] recipients, String sharerName, String sharerEmail, String shareMessage, Upload upload) {
		this.recipients = recipients;
		this.sharerName = sharerName;
		this.sharerEmail = sharerEmail;
		this.shareMessage = shareMessage;
		this.upload = upload;
	}

	public String[] getRecipients() {
		return recipients.clone();
	}

	public String getSharerName() {
		return sharerName;
	}

	public String getSharerEmail() {
		return sharerEmail;
	}

	public String getShareMessage() {
		return shareMessage;
	}

	public Upload getUpload() {
		return (Upload) upload.clone();
	}
}
