package com.ultrashare.component.vo;

import java.text.SimpleDateFormat;

import com.ultrashare.model.Upload;

public final class DownloadConfirmVO {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private final String fileName;

	private final String fileSize;

	private final String uploadDate;

	private final String uploaderName;

	private final String uploaderEmail;

	private final String id;

	private final String confirmationCode;

	private final String downloadLink;

	public DownloadConfirmVO(Upload upload) {
		fileName = upload.getFileName();
		fileSize = upload.getFileSizeAsString();
		uploadDate = DATE_FORMAT.format(upload.getCreationDate().getTime());
		uploaderName = upload.getSenderName();
		uploaderEmail = upload.getSenderEmail();
		id = upload.getId().toString();
		confirmationCode = upload.getConfirmationCode().toString();
		downloadLink = "http://ultrashare.valvezon.com/download/request/" + id + "/" + confirmationCode;
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

	public String getDownloadLink() {
		return downloadLink;
	}

}
