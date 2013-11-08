package com.ultrashare.controller;

import java.io.InputStream;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;

import com.ultrashare.component.facilities.FTPHandler;
import com.ultrashare.component.facilities.FTPRetrieveAction;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.model.Share;

@Resource
public class DownloadController {

	private static Logger logger = Logger.getLogger(DownloadController.class);

	private Result result;

	public DownloadController(Result result) {
		this.result = result;
	}

	@Get
	@Path("/upload/confirm/{id}/{confirmationCode}")
	public void request(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			result.redirectTo(this).proceed();
			return;
		}
		result.redirectTo(UploadController.class).form();
	}

	public void proceed() {

	}

	@Get
	@Path("/upload/test/{fileName}")
	public Download test(String fileName) {
		return new InputStreamDownload(FTPHandler.processFTPAction(new FTPRetrieveAction(fileName)), "application/octet-stream", "test.txt");
	}

	public Download download(Share share, InputStream inputStream) {
		return new InputStreamDownload(inputStream, "application/binary", share.getSharedUpload().getFileName(), true, 104857600);
	}
}
