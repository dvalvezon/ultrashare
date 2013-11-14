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
import com.ultrashare.dao.DownloadDAO;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Upload;

@Resource
public class DownloadController {

	private static Logger logger = Logger.getLogger(DownloadController.class);

	private Result result;

	private UploadDAO uploadDao;

	private DownloadDAO downloadDao;

	public DownloadController(Result result, UploadDAO uploadDao, DownloadDAO downloadDao) {
		this.result = result;
		this.uploadDao = uploadDao;
		this.downloadDao = downloadDao;
	}

	@Get
	@Path("/download/request/{id}/{confirmationCode}")
	public void request(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			Upload upload = uploadDao.find(Long.valueOf(id));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				result.redirectTo(this).confirm(upload);
				return;
			}
		}
		result.redirectTo(UploadController.class).form();
	}

	public DownloadConfirmVO confirm(Upload upload) {
		upload = new Upload("Danilo Valvezon", "danilovalvezon@hotmail.com", "hosts", "application/text", 8000L);
		upload.setId(1L);
		return new DownloadConfirmVO(upload);
	}

	// pi -> Share id, pc -> Share confirmationCode
	@Post
	public DownloadConfirmVO start(String pi, String pc) {
		if (!Validate.ifAnyStringIsNullOrEmpty(pi, pc) && !Validate.ifAnyStringIsNotNumeric(pi, pc)) {
			Upload upload = uploadDao.find(Long.valueOf(pi));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(pc))) {
				// TODO - Implement validation token
				return new DownloadConfirmVO(upload);
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
			Upload upload = uploadDao.find(Long.valueOf(pid));
			logger.debug("submitting validation: id=" + pid + " code=" + pcon);
			logger.debug(upload.getConfirmationCode().equals(Long.valueOf(pcon)));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(pcon))) {
				downloadDao.save(new com.ultrashare.model.Download(upload));
				logger.debug("passed.");
				return new InputStreamDownload(FTPHandler.processFTPAction(new FTPRetrieveAction(upload.getId().toString())), "application/octet-stream",
						upload.getFileName(), false, upload.getFileSize());
			}
			logger.debug("verification failure. share code=" + upload.getConfirmationCode());
		}
		result.forwardTo(UploadController.class).form();
		return null;
	}
}
