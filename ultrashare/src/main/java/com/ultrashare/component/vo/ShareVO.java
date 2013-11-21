package com.ultrashare.component.vo;

import com.ultrashare.model.Upload;

public class ShareVO {

	private final String[] recipients;
	private final String sharerName;
	private final Upload upload;

	public ShareVO(String[] recipients, String sharerName, Upload upload) {
		this.recipients = recipients;
		this.sharerName = sharerName;
		this.upload = upload;
	}

	public String[] getRecipients() {
		return recipients.clone();
	}

	public String getSharerName() {
		return sharerName;
	}

	public Upload getUpload() {
		return (Upload) upload.clone();
	}
}
