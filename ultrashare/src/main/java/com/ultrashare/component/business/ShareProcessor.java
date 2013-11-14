package com.ultrashare.component.business;

import java.util.ArrayList;
import java.util.List;

import com.ultrashare.component.facilities.MailSender;
import com.ultrashare.component.vo.MailVO;
import com.ultrashare.component.vo.ShareVO;
import com.ultrashare.model.Upload;

public class ShareProcessor extends AbstractProcessor<ShareVO> {

	private static String SHARE_EMAIL_MESSAGE_PATTERN = "Greetings!\n\n<senderName> (<senderMail>) has shared \"<fileName>\" with you!\n\nTo download this file please click the link below:\n\n<confirmationLink>\n\nUltraSHARE is a place where you can share anything, with anyone.\nVisit us at http://ultrashare.valvezon.com\n\nPlease do not reply this email!\n\nIf you have any questions or problems please send us an email:\nultrasharesupport@valvezon.com\n\nBest Regards,\nThe UltraSHARE Team";
	private static String SHARE_EMAIL_SUBJECT_PATTERN = "<sharerName> has shared \"<fileName>\" with you in UltraSHARE!";
	private static String SHARE_EMAIL_LINK_PATTERN = "http://ultrashare.valvezon.com/download/request/<id>/<confirmationCode>";

	@Override
	protected void execute(ShareVO processItem) {
		List<MailVO> mailsVO = new ArrayList<MailVO>(1);
		mailsVO.add(new MailVO(processItem.getRecipients(), SHARE_EMAIL_SUBJECT_PATTERN.replaceAll("<sharerName>", processItem.getSharerName()).replaceAll(
				"<fileName>", processItem.getUpload().getFileName()), getShareMailMessage(processItem.getSharerName(), processItem.getSharerEmail(),
				processItem.getUpload().getFileName(), getShareLink(processItem.getUpload()))));
		submitShareEmail(mailsVO);
	}

	private static void submitShareEmail(List<MailVO> mailsVO) {
		MailSender.sendMails(mailsVO);
	}

	private static String getShareMailMessage(String sharerName, String sharerEmail, String fileName, String confirmationLink) {
		return SHARE_EMAIL_MESSAGE_PATTERN.replaceAll("<sharerName>", sharerName).replaceAll("<sharerEmail>", sharerEmail).replaceAll("<fileName>", fileName)
				.replaceAll("<confirmationLink>", confirmationLink);
	}

	private static String getShareLink(Upload upload) {
		return SHARE_EMAIL_LINK_PATTERN.replaceAll("<id>", upload.getId().toString()).replaceAll("<confirmationCode>", upload.getConfirmationCode().toString());
	}
}
