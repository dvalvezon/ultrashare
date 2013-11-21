package com.ultrashare.model;

import java.util.Calendar;

import javax.persistence.Column;
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

	@ManyToOne(optional = false)
	private Upload referredUpload;

	@Column(nullable = false)
	private Calendar shareDate;

	@Column(nullable = false)
	private String sharerName;

	@Column(nullable = false)
	private String recipients;

	public Share() {

	}

	public Share(Upload referredUpload, String sharerName, String recipients) {
		this.referredUpload = referredUpload;
		this.sharerName = sharerName;
		this.recipients = recipients;
		this.shareDate = Calendar.getInstance();
	}

	@Override
	public Object clone() {
		Share clonedShare = new Share();
		clonedShare.setId(id);
		clonedShare.setRecipients(recipients);
		clonedShare.setReferredUpload((Upload) referredUpload.clone());
		clonedShare.setShareDate((Calendar) shareDate.clone());
		clonedShare.setSharerName(sharerName);
		return clonedShare;
	}

	@Override
	public String toString() {
		return "(ref=" + super.toString() + ",id=" + id + ",referredUpload=" + referredUpload + ",shareDate=" + shareDate + ",sharerName=" + sharerName
				+ ",recipients=<" + recipients + ">)";
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

	public Calendar getShareDate() {
		return shareDate;
	}

	public void setShareDate(Calendar shareDate) {
		this.shareDate = shareDate;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getSharerName() {
		return sharerName;
	}

	public void setSharerName(String sharerName) {
		this.sharerName = sharerName;
	}
}
