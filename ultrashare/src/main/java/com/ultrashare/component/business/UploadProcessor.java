package com.ultrashare.component.business;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.ultrashare.component.facilities.FTPPublisher;
import com.ultrashare.component.facilities.MailSender;
import com.ultrashare.component.vo.UploadProcessVO;
import com.ultrashare.model.Upload;

@Component
@ApplicationScoped
public class UploadProcessor {

	// private LinkedBlockingQueue<UploadProcessVO> queue;
	//
	// public UploadProcessor() {
	// this.queue = new LinkedBlockingQueue<UploadProcessVO>();
	// }

	// @PostConstruct
	// private void execute() {
	// while (true) {
	// try {
	// new Thread(new UploadBusinessProcessor(this.queue.take())).run();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	public void addProcess(UploadProcessVO uploadProcessVO) {
		new Thread(new UploadBusinessProcessor(uploadProcessVO)).start();
		// if (uploadProcessVO != null) {
		// this.queue.offer(uploadProcessVO);
		// }
	}
}

class UploadBusinessProcessor implements Runnable {

	private static String CONFIRMATION_EMAIL_MESSAGE_PATTERN = "Dear <recipientName>,\n\nThanks for uploading in UltraSHARE.com!\n\nIn order to share \"<fileName>\" you need to click the link below to confirm your Upload.\n\n<confirmationLink>\n\nPlease do not reply this email!\n\nIf you have any questions or problems please send us an email:\nsupport@valvezon.com\n\nBest Regards,\nThe UltraSHARE Team";
	private static String CONFIRMATION_EMAIL_SUBJECT_PATTERN = "Confirmation Email for sharing \"<fileName>\" in UltraSHARE.com";
	private static String CONFIRMATION_EMAIL_LINK_PATTERN = "http://ultrashare.valvezon.com/upload/confirm/<id>/<confirmationCode>";

	private final UploadProcessVO processItem;

	public UploadBusinessProcessor(UploadProcessVO item) {
		this.processItem = item;
	}

	public void run() {
		publishFileInFtp(processItem.getUploadedFile());
		submitConfirmationEmail(processItem.getUploadEntity().getSenderName(), processItem.getUploadEntity().getSenderEmail(), processItem.getUploadedFile()
				.getFileName(), getConfirmationLink(processItem.getUploadEntity()));
	}

	private static void publishFileInFtp(UploadedFile arquivo) {
		FTPPublisher.getInstance().sendFileFromStream(arquivo.getFile(), arquivo.getFileName());
	}

	private static void submitConfirmationEmail(String recipientName, String recipientMail, String fileName, String confirmationLink) {
		MailSender.sendMail(new String[] { recipientMail }, CONFIRMATION_EMAIL_SUBJECT_PATTERN.replace("<fileName>", fileName),
				getConfirmationMailMessage(recipientName, fileName, confirmationLink));
	}

	private static String getConfirmationMailMessage(String recipientName, String fileName, String confirmationLink) {
		return CONFIRMATION_EMAIL_MESSAGE_PATTERN.replaceAll("<recipientName>", recipientName).replaceAll("<fileName>", fileName)
				.replaceAll("<confirmationLink>", confirmationLink);
	}

	private static String getConfirmationLink(Upload createdUpload) {
		return CONFIRMATION_EMAIL_LINK_PATTERN.replace("<id>", createdUpload.getId().toString()).replace("<confirmationCode>",
				createdUpload.getConfirmationCode().toString());
	}

}