package com.ultrashare.component.vo;

import java.text.SimpleDateFormat;

import com.ultrashare.model.Upload;

public final class SearchVO {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private final String fileName;

	private final String fileSize;

	private final String creationDate;

	private final String link;

	public SearchVO(Upload upload) {
		fileName = upload.getFileName();
		fileSize = upload.getFileSizeAsString();
		creationDate = DATE_FORMAT.format(upload.getCreationDate().getTime());
		link = "/download/request/" + upload.getId() + "/" + upload.getConfirmationCode();
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getLink() {
		return link;
	}
}
