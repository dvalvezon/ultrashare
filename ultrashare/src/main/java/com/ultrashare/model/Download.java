package com.ultrashare.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Download {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Upload referredUpload;

	@Column(nullable = false)
	private Calendar downloadDate;

	public Download() {

	}

	public Download(Upload referredUpload) {
		this.referredUpload = referredUpload;
		this.downloadDate = Calendar.getInstance();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Upload getReferredUpload() {
		return referredUpload;
	}

	public void setReferredUpload(Upload referredUpload) {
		this.referredUpload = referredUpload;
	}

	public Calendar getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Calendar downloadDate) {
		this.downloadDate = downloadDate;
	}

	@Override
	public Object clone() {
		Download clonedDownload = new Download();
		clonedDownload.setDownloadDate((Calendar) downloadDate.clone());
		clonedDownload.setId(id);
		clonedDownload.setReferredUpload((Upload) referredUpload.clone());
		return clonedDownload;
	}

	@Override
	public String toString() {
		return "(ref=" + super.toString() + ",id=" + id + ",referredUpload=" + referredUpload + ",downloadDate=" + downloadDate + ")";
	}
}
