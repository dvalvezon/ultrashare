package com.ultrashare.controller;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.component.FTPPublisher;
import com.ultrashare.component.MailSender;
import com.ultrashare.component.Validate;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Upload;

@Resource
public class UploadController {

	private static Logger logger = Logger.getLogger(UploadController.class);

	private static String CONFIRMATION_EMAIL_MESSAGE_PATTERN = "Dear <recipientName>,\n\nThanks for uploading in UltraSHARE.com!\n\nIn order to share \"<fileName>\" you need to click the link below to confirm your Upload.\n\n<confirmationLink>\n\nPlease do not reply this email!\n\nIf you have any questions or problems please send us an email:\nsupport@valvezon.com\n\nBest Regards,\nThe UltraSHARE Team";
	private static String CONFIRMATION_EMAIL_SUBJECT_PATTERN = "Confirmation Email for sharing \"<fileName>\" in UltraSHARE.com";
	private static String CONFIRMATION_EMAIL_LINK_PATTERN = "http://ultrashare.valvezon.com/upload/confirm/<id>/<confirmationCode>";

	private Result result;

	private UploadDAO uploadDao;

	public UploadController(Result result, UploadDAO uploadDao) {
		this.result = result;
		this.uploadDao = uploadDao;
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
			publishFileInFtp(userFile);
			Upload createdUpload = persistUpload(userFile.getFileName(), userName, userMail, friendsMails);
			submitConfirmationEmail(userName, userMail, userFile.getFileName(), getConfirmationLink(createdUpload));
			result.redirectTo(this).success(createdUpload);
		}
	}

	@Get
	@Path("/upload/confirm/{id}/{confirmationCode}")
	public void confirm(String id, String confirmationCode) {
		if (!Validate.ifAnyStringIsNullOrEmpty(id, confirmationCode) && !Validate.ifAnyStringIsNotNumeric(id, confirmationCode)) {
			Upload upload = uploadDao.find(Long.valueOf(id));
			if (upload != null && upload.getConfirmationCode().equals(Long.valueOf(confirmationCode))) {
				upload.setIsAlreadyConfirmed(true);
				uploadDao.update(upload);
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

	private void publishFileInFtp(UploadedFile arquivo) {
		FTPPublisher.getInstance().sendFileFromStream(arquivo.getFile(), arquivo.getFileName());
	}

	private void submitConfirmationEmail(String recipientName, String recipientMail, String fileName, String confirmationLink) {
		MailSender.sendMail(new String[] { recipientMail }, CONFIRMATION_EMAIL_SUBJECT_PATTERN.replace("<fileName>", fileName),
				getConfirmationMailMessage(recipientName, fileName, confirmationLink));
	}

	private String getConfirmationMailMessage(String recipientName, String fileName, String confirmationLink) {
		return CONFIRMATION_EMAIL_MESSAGE_PATTERN.replaceAll("<recipientName>", recipientName).replaceAll("<fileName>", fileName)
				.replaceAll("<confirmationLink>", confirmationLink);
	}

	private Upload persistUpload(String fileName, String userName, String userMail, String friendsMails) {
		Upload upload = new Upload(userName, userMail, fileName, friendsMails);
		uploadDao.save(upload);
		return upload;
	}

	private String getConfirmationLink(Upload createdUpload) {
		return CONFIRMATION_EMAIL_LINK_PATTERN.replace("<id>", createdUpload.getId().toString()).replace("<confirmationCode>",
				createdUpload.getConfirmationCode().toString());
	}
}
