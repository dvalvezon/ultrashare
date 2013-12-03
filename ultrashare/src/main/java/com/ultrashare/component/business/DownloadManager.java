package com.ultrashare.component.business;

import java.io.IOException;
import java.io.InputStream;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.ultrashare.component.facilities.FTPHandler;
import com.ultrashare.component.facilities.FTPRetrieveAction;
import com.ultrashare.model.Upload;

@Component
@ApplicationScoped
public class DownloadManager {

	private UploadRetainer uploadRetainer;

	public DownloadManager(UploadRetainer uploadRetainer) {
		this.uploadRetainer = uploadRetainer;
	}

	public Download createUserDownload(Upload upload) throws IOException {
		return new InputStreamDownload(getInputStream(upload.getId()), "application/octet-stream", upload.getFileName(), false, upload.getFileSize());
	}

	private InputStream getInputStream(Long uploadId) throws IOException {
		InputStream inputStream = null;
		if ((inputStream = uploadRetainer.getProvisoryStream(uploadId)) == null) {
			inputStream = FTPHandler.processFTPAction(new FTPRetrieveAction(uploadId.toString()));
		}
		return inputStream;
	}
}
