package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.component.business.UploadProcessor;
import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.component.vo.UploadProcessVO;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Upload;

@Resource
public class UploadController {

	private static Logger logger = Logger.getLogger(UploadController.class);

	private Result result;

	private UploadDAO uploadDao;

	private UploadProcessor uploadProcessor;

	public UploadController(Result result, UploadDAO uploadDao, UploadProcessor uploadProcessor) {
		this.result = result;
		this.uploadDao = uploadDao;
		this.uploadProcessor = uploadProcessor;
	}

	@Get
	public void form() {
		logger.debug(Log.header("form"));
		logger.debug(Log.footer("form"));
	}

	@Post
	public void upload(UploadedFile userFile, String userName, String userMail) {
		logger.debug(Log.header("upload", Log.entry("userFile", userFile), Log.entry("userName", userName), Log.entry("userMail", userMail)));
		Upload createdUpload = null;
		if (Validate.ifAnyObjectIsNull(userFile) || Validate.ifAnyStringIsNullOrEmpty(userName, userMail)) {
			logger.warn(Log.message("Invalid parameters detected!"));
			redirectToForm();
		} else {
			logger.debug(Log.message("Valid parameters. Persisting upload..."));
			createdUpload = persistUpload(userFile.getFileName(), userFile.getContentType(), userFile.getSize(), userName, userMail);
			logger.debug(Log.message("Upload persisted"));
			logger.info(Log.message("New upload created. ", Log.entry("createdUpload", createdUpload)));
			logger.debug(Log.message("Submitting created download to UploadProcessor."));
			uploadProcessor.process(new UploadProcessVO(userFile, createdUpload));
			logger.debug(Log.message("Redirecting to success page"));
			result.redirectTo(this).success(createdUpload);
		}
		logger.debug(Log.footer("upload", Log.entry("createdUpload", createdUpload)));
	}

	private void redirectToForm() {
		logger.trace(Log.header("redirectToForm"));
		result.redirectTo(this).form();
		logger.debug("Request will be redirected to form()");
		logger.trace(Log.footer("redirectToForm"));
	}

	public Upload success(Upload upload) {
		logger.debug(Log.header("confirmed", Log.entry("upload", upload)));
		logger.debug(Log.message("Validating parameters..."));
		if (upload == null || Validate.ifAnyStringIsNullOrEmpty(upload.getFileName(), upload.getSenderEmail(), upload.getSenderName())) {
			logger.warn("Invalid parameters detected!");
			upload = null;
			redirectToForm();
		} else {
			logger.debug(Log.message("Parameters validated."));
		}
		logger.debug(Log.footer("confirmed"));
		return upload;
	}

	private Upload persistUpload(String fileName, String fileContentType, Long fileSize, String userName, String userMail) {
		fileName = fileName.replace(",", "");
		logger.trace(Log.header("persistUpload", Log.entry("fileName", fileName), Log.entry("fileContentType", fileContentType),
				Log.entry("fileSize", fileSize), Log.entry("userName", userName), Log.entry("userMail", userMail)));
		Upload upload = new Upload(userName, userMail, fileName, fileContentType, fileSize);
		logger.trace(Log.message("Upload created.", Log.entry("upload", upload)));
		uploadDao.save(upload);
		logger.trace(Log.footer("persistUpload", upload));
		return upload;
	}
}
