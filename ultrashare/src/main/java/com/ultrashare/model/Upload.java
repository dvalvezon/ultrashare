package com.ultrashare.model;

import java.text.DecimalFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "UPLOADS_BY_FILE_NAME", query = "select u from Upload u where u.fileName like :fileName") })
public class Upload {

	public final static String UPLOADS_BY_FILE_NAME = "UPLOADS_BY_FILE_NAME";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String senderName;

	@Column(nullable = false)
	private String senderEmail;

	@Column(nullable = false)
	private String fileName;

	@Column(nullable = false)
	private String fileContentType;

	@Column(nullable = false)
	private Long fileSize;

	@Column(nullable = false)
	private Calendar creationDate;

	@Column(nullable = false)
	private Long creationTimeInMillis;

	public Upload() {

	}

	public Upload(String senderName, String senderEmail, String fileName, String fileContentType, Long fileSize) {
		this.senderName = senderName;
		this.senderEmail = senderEmail;
		this.fileName = fileName;
		this.fileContentType = fileContentType;
		this.fileSize = fileSize;
		this.creationDate = Calendar.getInstance();
		this.creationTimeInMillis = creationDate.getTimeInMillis();
	}

	public Long getConfirmationCode() {
		return creationTimeInMillis + senderName.hashCode() + senderEmail.hashCode() + fileName.hashCode() + fileContentType.hashCode() + fileSize.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreationTimeInMillis() {
		return creationTimeInMillis;
	}

	public void setCreationTimeInMillis(Long creationTimeInMillis) {
		this.creationTimeInMillis = creationTimeInMillis;
	}

	public String getFileSizeAsString() {
		DecimalFormat df = new DecimalFormat("#.##");
		Double size = this.fileSize.doubleValue();
		if (size < 1024) {
			return size + " Bytes";
		} else {
			size = size / 1024;
			if (size < 1024) {
				return df.format(size) + " KB";
			} else {
				size = size / 1024;
				if (size < 1024) {
					return df.format(size) + " MB";
				} else {
					return df.format((size / 1024)) + " GB";
				}
			}
		}
	}

	@Override
	public Object clone() {
		Upload clonedUpload = new Upload();
		clonedUpload.setCreationDate((Calendar) creationDate.clone());
		clonedUpload.setCreationTimeInMillis(creationTimeInMillis);
		clonedUpload.setFileContentType(fileContentType);
		clonedUpload.setFileName(fileName);
		clonedUpload.setFileSize(fileSize);
		clonedUpload.setId(id);
		clonedUpload.setSenderEmail(senderEmail);
		clonedUpload.setSenderName(senderName);
		return clonedUpload;
	}

	@Override
	public String toString() {
		return "(ref=" + super.toString() + ",id=" + id + ",senderName=" + senderName + ",senderEmail=" + senderEmail + ",fileName=" + fileName
				+ "fileContentType=" + fileContentType + ",fileSize=" + fileSize + ",creationDate=" + creationDate + ",creationTimeInMillis="
				+ creationTimeInMillis + ",confirmationCode=" + getConfirmationCode() + ")";
	}
}
