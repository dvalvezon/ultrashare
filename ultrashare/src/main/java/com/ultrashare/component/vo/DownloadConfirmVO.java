package com.ultrashare.component.vo;

import java.text.SimpleDateFormat;

import com.ultrashare.model.Share;

public final class DownloadConfirmVO {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private final String fileName;

	private final String fileSize;

	private final String uploadDate;

	private final String uploaderName;

	private final String uploaderEmail;

	private final String id;

	private final String confirmationCode;

	public DownloadConfirmVO(Share share) {
		fileName = share.getSharedUpload().getFileName();
		fileSize = share.getSharedUpload().getFileSizeAsString();
		uploadDate = DATE_FORMAT.format(share.getCreationDate().getTime());
		uploaderName = share.getSharedUpload().getSenderName();
		uploaderEmail = share.getSharedUpload().getSenderEmail();
		id = share.getId().toString();
		confirmationCode = share.getConfirmationCode().toString();
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public String getUploaderName() {
		return uploaderName;
	}

	public String getUploaderEmail() {
		return uploaderEmail;
	}

	public String getId() {
		return id;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

}
