package com.ultrashare.component;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.model.Upload;

public class UploadProcessVO {

	private final UploadedFile uploadedFile;
	private final Upload uploadEntity;

	public UploadProcessVO(UploadedFile uploadedFile, Upload uploadEntity) {
		this.uploadedFile = uploadedFile;
		this.uploadEntity = uploadEntity;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public Upload getUploadEntity() {
		return uploadEntity;
	}
}
