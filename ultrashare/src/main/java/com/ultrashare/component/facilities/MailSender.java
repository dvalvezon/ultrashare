package com.ultrashare.component.facilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ultrashare.component.vo.MailVO;
import com.ultrashare.type.PropertyType;

public final class MailSender {

	private static final Logger logger = Logger.getLogger(MailSender.class);

	private static final Properties emailProperties;
	static {
		try {
			emailProperties = PropertyLoader.loadProperties(PropertyType.MAIL_CONFIGS);
		} catch (Exception e) {
			logger.error("Could not load Email Properties...", e);
			throw new RuntimeException(e);
		}
	}

	private MailSender() {

	}

	// public static void sendMail(String[] recipients, String mailSubject,
	// String mailText) {
	// logger.debug("Begin of method public sendMail().");
	// logger.debug("Recipients: " + recipients != null ?
	// Arrays.deepToString(recipients) : "[null]");
	// logger.debug("Subject: " + mailSubject != null ? mailSubject : "[null]");
	// logger.debug("Mail Body: " + mailText != null ? mailText : "[null]");
	// try {
	// logger.debug("Building mails...");
	// Transport.send(createMessage(recipients, mailSubject, mailText));
	// logger.debug("Mail has been sent.");
	// } catch (Exception e) {
	// logger.error("Could not send mails.", e);
	// throw new RuntimeException(e);
	// } finally {
	// logger.debug("End of method public sendMail().");
	// }
	// }

	public static void sendMails(List<MailVO> mails) {
		List<Message> messages = new ArrayList<Message>();
		Session emailSession = getMailSession();
		for (MailVO mail : mails) {
			try {
				messages.add(createMessage(mail, emailSession));
			} catch (MessagingException e) {
				logger.error("Could not create message with VO:" + mail.toString(), e);
			}
		}
		for (Message message : messages) {
			try {
				Transport.send(message);
			} catch (MessagingException e) {
				logger.error("Could not send message: " + message.toString(), e);
			}
		}
	}

	private static Message createMessage(MailVO mail, Session emailSession) throws MessagingException {
		Message message = new MimeMessage(emailSession);
		message.setFrom(new InternetAddress(getSenderMail()));
		Address[] userMails = InternetAddress.parse(concatRecipients(mail.getRecipients()));
		message.setRecipients(Message.RecipientType.TO, userMails);
		message.setSubject(mail.getSubject());
		message.setText(mail.getText());
		return message;
	}

	// private static Message createMessage(String[] recipients, String
	// mailSubject, String mailText) throws Exception {
	// logger.debug("Creating the message...");
	// logger.debug("Retrieving mail properties...");
	// Properties mailProperties = getMailProperties();
	// logger.debug("Properties found: " + mailProperties.toString());
	// String senderMail = getSenderMail(mailProperties);
	// String senderPassword = getSenderPassword(mailProperties);
	//
	// logger.debug("Building message...");
	// Message message = new MimeMessage(getMailSession(mailProperties));
	// logger.debug("Setting FROM field with " + senderMail);
	// message.setFrom(new InternetAddress(senderMail));
	// logger.debug("Parsing recipients...");
	// Address[] userMails =
	// InternetAddress.parse(concatRecipients(recipients));
	// logger.debug("Setting TO field with " + Arrays.deepToString(userMails));
	// message.setRecipients(Message.RecipientType.TO, userMails);
	// logger.debug("Setting SUBJECT field with \"" + mailSubject + "\".");
	// message.setSubject(mailSubject);
	// logger.debug("Setting Mail Text with \"" + mailText + "\".");
	// message.setText(mailText);
	// logger.debug("Message has been created.");
	// return message;
	// }

	private static String getSenderPassword() {
		return getMailProperties().getProperty("mail.credential.password");
	}

	private static String getSenderMail() {
		return getMailProperties().getProperty("mail.credential.username");
	}

	private static boolean getDebugMail() {
		return getMailProperties().getProperty("mail.config.debug").equalsIgnoreCase("true") ? true : false;
	}

	private static Properties getMailProperties() {
		return emailProperties;
	}

	private static Session getMailSession() {
		logger.debug("Building mail session with username=" + getSenderMail() + ", password=" + getSenderPassword() + "...");
		Session session = Session.getDefaultInstance(getMailProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSenderMail(), getSenderPassword());
			}
		});
		session.setDebug(getDebugMail());
		logger.debug("Mail session debugging is set to \"" + session.getDebug() + "\".");
		logger.debug("Mail session has been built.");
		return session;
	}

	private static String concatRecipients(String[] recipients) {
		String string = "";
		if (recipients.length == 1) {
			return recipients[0];
		} else if (recipients.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < recipients.length; i++) {
				sb.append(recipients[i]);
				if (i != recipients.length - 1) {
					sb.append(",");
				}
			}
			string = sb.toString();
		}
		logger.debug("Retrieving recipients from array: " + (string.isEmpty() ? "[null]" : string));
		return string;
	}
}
