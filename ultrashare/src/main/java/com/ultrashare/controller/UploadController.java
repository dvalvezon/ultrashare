package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Upload;

@Resource
public class UploadController {

	private static Logger logger = Logger.getLogger(UploadController.class);

	private Result result;

	private UploadDAO uploadDao;

	public UploadController(Result result, UploadDAO uploadDao) {
		this.result = result;
		this.uploadDao = uploadDao;
	}

	public void form() {

	}

	@Get
	@Post
	public void upload(UploadedFile arquivo, String userName, String userMail, String friendsMails) {
		if (arquivo == null || userName == null || userName.isEmpty() || userMail == null || userMail.isEmpty() || friendsMails == null
				|| friendsMails.isEmpty()) {
			result.redirectTo(this).form();
		} else {
			uploadDao.save(new Upload(userName, userMail, arquivo.getFileName(), friendsMails));
			// TODO - Implement Logging...
			logger.debug(arquivo + " | " + userName + " | " + userMail + " | " + friendsMails);
			logger.debug("File size = " + arquivo.getSize() / (1024 * 1024) + "MB");
			// FTPPublisher.getInstance().sendFileFromStream(arquivo.getFile(),
			// arquivo.getFileName());
		}
	}
}
