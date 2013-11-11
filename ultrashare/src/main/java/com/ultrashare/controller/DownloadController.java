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
import com.ultrashare.component.vo.DownloadConfirmVO;
import com.ultrashare.dao.ShareDAO;
import com.ultrashare.model.Share;

@Resource
public class DownloadController {

	private static Logger logger = Logger.getLogger(DownloadController.class);

	private static final long MIN_WAIT = 10000L;

	private static final long MAX_WAIT = 120000L;

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

	public DownloadConfirmVO confirm(Share share) {
		return new DownloadConfirmVO(share);
	}

	// pi -> Share id, pc -> Share confirmationCode
	@Post
	public DownloadConfirmVO start(String pi, String pc) {
		if (!Validate.ifAnyStringIsNullOrEmpty(pi, pc) && !Validate.ifAnyStringIsNotNumeric(pi, pc)) {
			Share share = shareDao.find(Long.valueOf(pi));
			if (share != null && share.getConfirmationCode().equals(Long.valueOf(pc))) {
				share.addDownloadAttempt();
				share.generateTimeToken();
				shareDao.update(share);
				return new DownloadConfirmVO(share);
			}
		}
		result.redirectTo(UploadController.class).form();
		return null;
	}

	@Post
	public Download download(String pid, String pcon) {
		logger.debug("download method called...");
		if (!Validate.ifAnyStringIsNullOrEmpty(pid, pcon) && !Validate.ifAnyStringIsNotNumeric(pid, pcon)) {
			logger.debug("Params ok...");
			Share share = shareDao.find(Long.valueOf(pid));
			// Long diffTime;
			logger.debug("submitting validation: id=" + pid + " code=" + pcon);
			logger.debug(share.getConfirmationCode().equals(Long.valueOf(pcon)));
			if (share != null && share.getConfirmationCode().equals(Long.valueOf(pcon))) {
				logger.debug("passed.");
				// && (diffTime = System.currentTimeMillis() -
				// share.getLastTimeToken()) >= MIN_WAIT && diffTime <=
				// MAX_WAIT) {
				return new InputStreamDownload(FTPHandler.processFTPAction(new FTPRetrieveAction(share.getSharedUpload().getFileName())),
						"application/octet-stream", share.getSharedUpload().getFileName(), false, share.getSharedUpload().getFileSize());
			}
			logger.debug("verification failure. share code=" + share.getConfirmationCode());
		}
		result.forwardTo(UploadController.class).form();
		return null;
		// return new InputStreamDownload(FTPHandler.processFTPAction(new
		// FTPRetrieveAction(fileName)), "application/octet-stream",
		// "test.txt");
	}

	// @Get
	// @Path("/download/download/{pi}/{pc}")
	// private Download download(String pi, String pc) {
	// logger.debug("download method called...");
	// if (!Validate.ifAnyStringIsNullOrEmpty(pi, pc) &&
	// !Validate.ifAnyStringIsNotNumeric(pi, pc)) {
	// logger.debug("Params ok...");
	// Share share = shareDao.find(Long.valueOf(pi));
	// // Long diffTime;
	// logger.debug("submitting validation: id=" + pi + " code=" + pc);
	// logger.debug(share.getConfirmationCode().equals(Long.valueOf(pc)));
	// if (share != null &&
	// share.getConfirmationCode().equals(Long.valueOf(pc))) {
	// logger.debug("passed.");
	// // && (diffTime = System.currentTimeMillis() -
	// // share.getLastTimeToken()) >= MIN_WAIT && diffTime <=
	// // MAX_WAIT) {
	// return new InputStreamDownload(FTPHandler.processFTPAction(new
	// FTPRetrieveAction(share.getSharedUpload().getFileName())), share
	// .getSharedUpload().getFileContentType(),
	// share.getSharedUpload().getFileName(), false,
	// share.getSharedUpload().getFileSize());
	// }
	// logger.debug("verification failure. share code=" +
	// share.getConfirmationCode());
	// }
	// result.forwardTo(UploadController.class).form();
	// return null;
	// }
}
