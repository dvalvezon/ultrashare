package com.ultrashare.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Share {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String recipientEmail;

	@ManyToOne(cascade = CascadeType.ALL)
	private Upload sharedUpload;

	private Calendar creationDate;

	private Long uploadTimeInMillis;

	private Integer downloadAttempts;

	public Share() {

	}

	public Share(Upload sharedUpload, String recipientEmail) {
		this.sharedUpload = sharedUpload;
		this.recipientEmail = recipientEmail;
		this.creationDate = Calendar.getInstance();
		this.uploadTimeInMillis = creationDate.getTimeInMillis();
		this.downloadAttempts = 0;
	}

	public Long getConfirmationCode() {
		return uploadTimeInMillis + sharedUpload.getConfirmationCode() + recipientEmail.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public Upload getSharedUpload() {
		return sharedUpload;
	}

	public void setSharedUpload(Upload sharedUpload) {
		this.sharedUpload = sharedUpload;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Long getUploadTimeInMillis() {
		return uploadTimeInMillis;
	}

	public void setUploadTimeInMillis(Long uploadTimeInMillis) {
		this.uploadTimeInMillis = uploadTimeInMillis;
	}

	public Integer getDownloadAttempts() {
		return downloadAttempts;
	}

	public void setDownloadAttempts(Integer downloadAttempts) {
		this.downloadAttempts = downloadAttempts;
	}
}
