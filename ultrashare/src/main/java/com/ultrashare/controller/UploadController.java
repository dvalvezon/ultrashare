package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.component.business.ConfirmationProcessor;
import com.ultrashare.component.business.UploadProcessor;
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
		if (Validate.ifAnyObjectIsNull(userFile) || Validate.ifAnyStringIsNullOrEmpty(userName, userMail, friendsMails)) {
			logger.debug("Redirecting to upload form. Parameters were missing...");
			result.redirectTo(this).form();
		} else {
			// TODO - Implement Logging...
			logger.debug(userFile + " | " + userName + " | " + userMail + " | " + friendsMails);
			logger.debug("File size = " + userFile.getSize() / (1024 * 1024) + "MB");
			Upload createdUpload = persistUpload(userFile.getFileName(), userFile.getContentType(), userFile.getSize(), userName, userMail, friendsMails);
			uploadProcessor.process(new UploadProcessVO(userFile, createdUpload));
			result.redirectTo(this).success(createdUpload);
		}
	}

	@Get
	@Path("/upload/confirm/{id}/{confirmationCode}")
	public void confirm(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			Upload upload = uploadDao.find(Long.valueOf(id));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				for (String recipient : upload.getRecipients().split(",")) {
					upload.getShares().add(new Share(upload, recipient));
				}
				upload.setIsAlreadyConfirmed(true);
				uploadDao.update(upload);
				confirmationProcessor.process(new ConfirmationVO(upload.getShares()));
				result.redirectTo(this).success(upload);
				return;
			}
		}
		result.redirectTo(this).form();
	}

	public Upload success(Upload upload) {
		if (upload == null || Validate.ifAnyStringIsNullOrEmpty(upload.getFileName(), upload.getRecipients(), upload.getSenderEmail(), upload.getSenderName())) {
			result.redirectTo(this).form();
			return null;
		}
		return upload;
	}

	private Upload persistUpload(String fileName, String fileContentType, Long fileSize, String userName, String userMail, String friendsMails) {
		Upload upload = new Upload(userName, userMail, fileName, fileContentType, fileSize, friendsMails);
		uploadDao.save(upload);
		return upload;
	}
}
