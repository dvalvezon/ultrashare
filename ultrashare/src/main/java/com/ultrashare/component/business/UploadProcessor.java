package com.ultrashare.component.business;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.ultrashare.component.facilities.FTPHandler;
import com.ultrashare.component.facilities.FTPSendAction;
import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.MailSender;
import com.ultrashare.component.vo.MailVO;
import com.ultrashare.component.vo.UploadProcessVO;
import com.ultrashare.model.Upload;

@Component
@ApplicationScoped
public final class UploadProcessor extends AbstractProcessor<UploadProcessVO> {

	private static final Logger logger = Logger.getLogger(UploadProcessor.class);

	private UploadRetainer uploadRetainer;

	public UploadProcessor(UploadRetainer uploadRetainer) {
		this.uploadRetainer = uploadRetainer;
	}

	private static String UPLOAD_EMAIL_MESSAGE_PATTERN = "Dear <recipientName>,\n\nThanks for uploading in UltraSHARE!\n\nNow you can download or share your file (<fileName>) using the link below: \n\n<downloadLink>\n\nPlease do not reply this email!\n\nIf you have any questions or problems please send us an email:\nsupport.ultrashare@valvezon.com\n\nBest Regards,\nThe UltraSHARE Team";
	private static String UPLOAD_EMAIL_SUBJECT_PATTERN = "Download link for sharing \"<fileName>\" in UltraSHARE.";
	private static String DOWNLOAD_EMAIL_LINK_PATTERN = "http://www.ultrashare.valvezon.com/download/request/<id>/<confirmationCode>";

	@Override
	protected void execute(UploadProcessVO processItem) {
		try {
			uploadRetainer.retain(processItem.getUploadEntity().getId(), processItem.getUploadedFile().getFile());
		} catch (IOException e) {
			logger.error(Log.message("Could not retain temporary upload stream."), e);
		}
		publishFileInFtp(processItem.getUploadedFile(), processItem.getUploadEntity().getId().toString());
		uploadRetainer.release(processItem.getUploadEntity().getId());
		submitUploadEmail(processItem.getUploadEntity().getSenderName(), processItem.getUploadEntity().getSenderEmail(), processItem.getUploadEntity()
				.getFileName(), getDownloadLink(processItem.getUploadEntity()));
	}

	private static void publishFileInFtp(UploadedFile uploadedFile, String uploadId) {
		FTPHandler.processFTPAction(new FTPSendAction(uploadId, uploadedFile.getFile()));
	}

	private static void submitUploadEmail(String recipientName, String recipientMail, String fileName, String confirmationLink) {
		MailSender.sendMails(Arrays.asList(new MailVO[] { new MailVO(new String[] { recipientMail }, UPLOAD_EMAIL_SUBJECT_PATTERN.replace("<fileName>",
				fileName), getUploadMailMessage(recipientName, fileName, confirmationLink)) }));
	}

	private static String getUploadMailMessage(String recipientName, String fileName, String downloadLink) {
		return UPLOAD_EMAIL_MESSAGE_PATTERN.replaceAll("<recipientName>", recipientName).replaceAll("<fileName>", fileName)
				.replaceAll("<downloadLink>", downloadLink);
	}

	private static String getDownloadLink(Upload createdUpload) {
		return DOWNLOAD_EMAIL_LINK_PATTERN.replace("<id>", createdUpload.getId().toString()).replace("<confirmationCode>",
				createdUpload.getConfirmationCode().toString());
	}
}