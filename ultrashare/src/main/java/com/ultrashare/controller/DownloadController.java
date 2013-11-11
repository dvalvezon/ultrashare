package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;

import com.ultrashare.component.facilities.FTPHandler;
import com.ultrashare.component.facilities.FTPRetrieveAction;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.dao.ShareDAO;
import com.ultrashare.model.Share;

@Resource
public class DownloadController {

	private static Logger logger = Logger.getLogger(DownloadController.class);

	private Result result;

	private ShareDAO shareDao;

	public DownloadController(Result result, ShareDAO shareDao) {
		this.result = result;
		this.shareDao = shareDao;
	}

	@Get
	@Path("/download/request/{id}/{confirmationCode}")
	public void request(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			Share share = shareDao.find(Long.valueOf(id));
			if (share != null && share.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				result.redirectTo(this).confirm(share);
				return;
			}
		}
		result.redirectTo(UploadController.class).form();
	}

	public Share confirm(Share share) {
		return share;
	}

	// pi -> Share id, pc -> Share confirmationCode
	public void start(String pi, String pc) {
		if (!Validate.ifAnyStringIsNullOrEmpty(pi, pc) && !Validate.ifAnyStringIsNotNumeric(pi, pc)) {
			Share share = shareDao.find(Long.valueOf(pi));
			if (share != null && share.getConfirmationCode().equals(Long.valueOf(pc))) {
				share.addDownloadAttempt();
				shareDao.update(share);
				result.redirectTo(this).download(share);
				return;
			}
		}
		result.redirectTo(UploadController.class).form();
	}

	@Get
	@Path("/download/test/{fileName}")
	public Download test(String fileName) {
		return new InputStreamDownload(FTPHandler.processFTPAction(new FTPRetrieveAction(fileName)), "application/octet-stream", "test.txt");
	}

	@Post
	private Download download(Share share) {
		return new InputStreamDownload(FTPHandler.processFTPAction(new FTPRetrieveAction(share.getSharedUpload().getFileName())), share.getSharedUpload()
				.getFileContentType(), share.getSharedUpload().getFileName(), true, share.getSharedUpload().getFileSize());
	}
}
