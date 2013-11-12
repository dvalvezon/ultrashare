package com.ultrashare.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.component.business.ConfirmationProcessor;
import com.ultrashare.component.business.UploadProcessor;
import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.component.vo.ConfirmationVO;
import com.ultrashare.component.vo.UploadProcessVO;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Share;
import com.ultrashare.model.Upload;

@Resource
public class UploadController {

	private static Logger logger = Logger.getLogger(UploadController.class);

	private Result result;

	private UploadDAO uploadDao;

	private UploadProcessor uploadProcessor;

	private ConfirmationProcessor confirmationProcessor;

	public UploadController(Result result, UploadDAO uploadDao, UploadProcessor uploadProcessor, ConfirmationProcessor confirmationProcessor) {
		this.result = result;
		this.uploadDao = uploadDao;
		this.uploadProcessor = uploadProcessor;
		this.confirmationProcessor = confirmationProcessor;
	}

	@Get
	public void form() {
		logger.debug(Log.header("form"));
		logger.debug(Log.footer("form"));
	}

	@Post
	public void upload(UploadedFile userFile, String userName, String userMail, String friendsMails) {
		logger.debug(Log.header("upload", Log.entry("userFile", userFile), Log.entry("userName", userName), Log.entry("userMail", userMail),
				Log.entry("friendsMails", friendsMails)));
		Upload createdUpload = null;
		if (Validate.ifAnyObjectIsNull(userFile) || Validate.ifAnyStringIsNullOrEmpty(userName, userMail, friendsMails)) {
			logger.warn(Log.message("Invalid parameters detected!"));
			redirectToForm();
		} else {
			logger.debug(Log.message("Valid parameters. Persisting upload..."));
			createdUpload = persistUpload(userFile.getFileName(), userFile.getContentType(), userFile.getSize(), userName, userMail, friendsMails);
			logger.debug(Log.message("Upload persisted"));
			logger.info(Log.message("New upload created. ", Log.entry("createdUpload", createdUpload)));
			logger.debug(Log.message("Submitting created download to UploadProcessor."));
			uploadProcessor.process(new UploadProcessVO(userFile, createdUpload));
			logger.debug(Log.message("Redirecting to success page"));
			result.redirectTo(this).success(createdUpload);
		}
		logger.debug(Log.footer("upload", Log.entry("createdUpload", createdUpload)));
	}

	@Get
	@Path("/upload/confirm/{id}/{confirmationCode}")
	public void confirm(String id, String confirmationCode) {
		logger.debug(Log.header("confirm", Log.entry("id", id), Log.entry("confirmationCode", confirmationCode)));
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			logger.debug(Log.message("Valid parameters. Querying for upload..."));
			Upload upload = uploadDao.find(Long.valueOf(id));
			logger.debug(Log.message("Query finished. Validating upload...", Log.entry("upload", upload)));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				logger.debug(Log.message("Upload validated. Creating shares."));
				ArrayList<Share> shares = new ArrayList<Share>();
				for (String recipient : upload.getRecipients().split(",")) {
					shares.add(new Share(upload, recipient));
				}
				logger.debug(Log.message("Shares created.", "Updating upload.", Log.entry("shares", shares)));
				upload.setIsAlreadyConfirmed(true);
				upload.setShares(shares);
				upload = uploadDao.update(upload);
				logger.info(Log.message("Upload updated.", Log.entry("upload", upload)));
				logger.debug(Log.message("Submitting created Shares to ConfirmationProcessor."));
				confirmationProcessor.process(new ConfirmationVO(shares));
				logger.debug(Log.message("Redirecting to confirmed page"));
				result.redirectTo(this).confirmed(upload);
			} else {
				logger.debug(Log.message("Upload is not valid."));
				redirectToForm();
			}
		} else {
			logger.warn("Invalid parameters detected!");
			redirectToForm();
		}
		logger.debug(Log.footer("confirm"));
	}

	private void redirectToForm() {
		logger.trace(Log.header("redirectToForm"));
		result.redirectTo(this).form();
		logger.debug("Request will be redirected to form()");
		logger.trace(Log.footer("redirectToForm"));
	}

	public Upload confirmed(Upload upload) {
		logger.debug(Log.header("confirmed", Log.entry("upload", upload)));
		logger.debug(Log.footer("confirmed"));
		return upload;
	}

	public Upload success(Upload upload) {
		logger.debug(Log.header("confirmed", Log.entry("upload", upload)));
		logger.debug(Log.message("Validating parameters..."));
		if (upload == null || Validate.ifAnyStringIsNullOrEmpty(upload.getFileName(), upload.getRecipients(), upload.getSenderEmail(), upload.getSenderName())) {
			logger.warn("Invalid parameters detected!");
			upload = null;
			redirectToForm();
		} else {
			logger.debug(Log.message("Parameters validated."));
		}
		logger.debug(Log.footer("confirmed"));
		return upload;
	}

	private Upload persistUpload(String fileName, String fileContentType, Long fileSize, String userName, String userMail, String friendsMails) {
		logger.trace(Log.header("persistUpload", Log.entry("fileName", fileName), Log.entry("fileContentType", fileContentType),
				Log.entry("fileSize", fileSize), Log.entry("userName", userName), Log.entry("userMail", userMail), Log.entry("friendsMails", friendsMails)));
		Upload upload = new Upload(userName, userMail, fileName, fileContentType, fileSize, friendsMails);
		logger.trace(Log.message("Upload created.", Log.entry("upload", upload)));
		uploadDao.save(upload);
		logger.trace(Log.footer("persistUpload", upload));
		return upload;
	}
}
