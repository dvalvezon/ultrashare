package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ultrashare.component.business.ShareProcessor;
import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.component.vo.ShareVO;
import com.ultrashare.dao.ShareDAO;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Share;
import com.ultrashare.model.Upload;

@Resource
public class ShareController {

	private static Logger logger = Logger.getLogger(ShareController.class);

	private Result result;

	private UploadDAO uploadDao;

	private ShareProcessor shareProcessor;

	private ShareDAO shareDao;

	public ShareController(Result result, UploadDAO uploadDao, ShareDAO shareDao, ShareProcessor shareProcessor) {
		this.result = result;
		this.uploadDao = uploadDao;
		this.shareDao = shareDao;
		this.shareProcessor = shareProcessor;
	}

	@Post
	public void share(String sid, String scon, String friendsMails, String sharerName) {
		logger.debug(Log.header("share", Log.entry("sid", sid), Log.entry("scon", scon), Log.entry("friendsMails", friendsMails),
				Log.entry("sharerName", sharerName)));
		logger.debug(Log.message("Validating parameters."));
		if (!Validate.ifAnyStringIsNullOrEmpty(sid, scon, friendsMails) && !Validate.ifAnyStringIsNotNumeric(sid, scon)) {
			logger.debug(Log.message("Parameters validated. Finding upload..."));
			Upload upload = uploadDao.find(Long.valueOf(sid));
			logger.debug(Log.message("Queried for upload.", "Validating upload.", Log.entry("upload", upload)));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(scon))) {
				logger.debug(Log.message("Upload validated. Saving Share..."));
				shareDao.save(new Share(upload, sharerName, friendsMails));
				logger.debug(Log.message("Share saved. Submitting process to ShareProcessor."));
				shareProcessor.process(new ShareVO(friendsMails.split(","), sharerName, upload));
				logger.debug(Log.message("Process submitted."));
				result.redirectTo(UploadController.class).form();
				logger.info(Log.message("Request for upload successful", Log.entry("upload", upload)));
			} else {
				logger.debug(Log.message("Upload not valid...", Log.entry("upload", upload)));
				redirectToForm();
			}
		} else {
			logger.debug(Log.message("Invalid parameters"));
			redirectToForm();
		}
		logger.debug(Log.footer("share"));
	}

	private void redirectToForm() {
		logger.trace(Log.header("redirectToForm"));
		result.redirectTo(UploadController.class).form();
		logger.debug("Request will be redirected to form()");
		logger.trace(Log.footer("redirectToForm"));
	}
}
