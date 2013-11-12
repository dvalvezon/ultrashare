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

	}

	@Post
	public void upload(UploadedFile userFile, String userName, String userMail, String friendsMails) {
		logger.debug(Log.header("upload", Log.entry("userFile", userFile), Log.entry("userName", userName), Log.entry("userMail", userMail),
				Log.entry("friendsMails", friendsMails)));
		Upload createdUpload = null;
		if (Validate.ifAnyObjectIsNull(userFile) || Validate.ifAnyStringIsNullOrEmpty(userName, userMail, friendsMails)) {
			logger.warn("Invalid parameters detected!");
			result.redirectTo(this).form();
			logger.debug("Request will be redirected to form()");
		} else {
			logger.debug("Valid parameters. Persisting upload...");
			createdUpload = persistUpload(userFile.getFileName(), userFile.getContentType(), userFile.getSize(), userName, userMail, friendsMails);
			logger.debug("Upload persisted");
			logger.info("New upload created. " + Log.parameters(Log.entry("createdUpload", createdUpload)));
			logger.debug("Submitting created download to UploadProcessor.");
			uploadProcessor.process(new UploadProcessVO(userFile, createdUpload));
			logger.debug("Redirecting to success page");
			result.redirectTo(this).success(createdUpload);
		}
		logger.debug(Log.footer("upload", Log.entry("createdUpload", createdUpload)));
	}

	@Get
	@Path("/upload/confirm/{id}/{confirmationCode}")
	public void confirm(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			Upload upload = uploadDao.find(Long.valueOf(id));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				ArrayList<Share> shares = new ArrayList<Share>();
				for (String recipient : upload.getRecipients().split(",")) {
					shares.add(new Share(upload, recipient));
				}
				upload.setIsAlreadyConfirmed(true);
				upload.setShares(shares);
				upload = uploadDao.update(upload);
				confirmationProcessor.process(new ConfirmationVO(shares));
				result.redirectTo(this).confirmed(upload);
				return;
			}
		}
		result.redirectTo(this).form();
	}

	public Upload confirmed(Upload upload) {
		return upload;
	}

	public Upload success(Upload upload) {
		if (upload == null || Validate.ifAnyStringIsNullOrEmpty(upload.getFileName(), upload.getRecipients(), upload.getSenderEmail(), upload.getSenderName())) {
			result.redirectTo(this).form();
			return null;
		}
		return upload;
	}

	private Upload persistUpload(String fileName, String fileContentType, Long fileSize, String userName, String userMail, String friendsMails) {
		logger.trace("Begin of method persistUpload() - Parameters: file");
		Upload upload = new Upload(userName, userMail, fileName, fileContentType, fileSize, friendsMails);
		uploadDao.save(upload);
		return upload;
	}
}
