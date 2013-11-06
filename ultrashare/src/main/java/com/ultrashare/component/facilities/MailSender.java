package com.ultrashare.component.facilities;

import java.util.Arrays;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ultrashare.type.PropertyType;

public final class MailSender {

	private static final Logger logger = Logger.getLogger(MailSender.class);

	private MailSender() {

	}

	public static void sendMail(String[] recipients, String mailSubject, String mailText) {
		logger.debug("Begin of method public sendMail().");
		logger.debug("Recipients: " + recipients != null ? Arrays.deepToString(recipients) : "[null]");
		logger.debug("Subject: " + mailSubject != null ? mailSubject : "[null]");
		logger.debug("Mail Body: " + mailText != null ? mailText : "[null]");
		try {
			logger.debug("Building mails...");
			Transport.send(createMessage(recipients, mailSubject, mailText));
			logger.debug("Mail has been sent.");
		} catch (Exception e) {
			logger.error("Could not send mails.", e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("End of method public sendMail().");
		}
	}

	private static Message createMessage(String[] recipients, String mailSubject, String mailText) throws Exception {
		logger.debug("Creating the message...");
		logger.debug("Retrieving mail properties...");
		Properties mailProperties = getMailProperties();
		logger.debug("Properties found: " + mailProperties.toString());
		String senderMail = getSenderMail(mailProperties);
		String senderPassword = getSenderPassword(mailProperties);

		logger.debug("Building message...");
		Message message = new MimeMessage(getMailSession(mailProperties, senderMail, senderPassword));
		logger.debug("Setting FROM field with " + senderMail);
		message.setFrom(new InternetAddress(senderMail));
		logger.debug("Parsing recipients...");
		Address[] userMails = InternetAddress.parse(concatRecipients(recipients));
		logger.debug("Setting TO field with " + Arrays.deepToString(userMails));
		message.setRecipients(Message.RecipientType.TO, userMails);
		logger.debug("Setting SUBJECT field with \"" + mailSubject + "\".");
		message.setSubject(mailSubject);
		logger.debug("Setting Mail Text with \"" + mailText + "\".");
		message.setText(mailText);
		logger.debug("Message has been created.");
		return message;
	}

	private static String getSenderPassword(Properties mailProperties) {
		return mailProperties.getProperty("mail.credential.password");
	}

	private static String getSenderMail(Properties mailProperties) {
		return mailProperties.getProperty("mail.credential.username");
	}

	private static boolean getDebugMail(Properties mailProperties) {
		return mailProperties.getProperty("mail.config.debug").equalsIgnoreCase("true") ? true : false;
	}

	private static Properties getMailProperties() throws Exception {
		return PropertyLoader.loadProperties(PropertyType.MAIL_CONFIGS);
	}

	private static Session getMailSession(Properties mailProperties, final String senderMail, final String senderPassword) {
		logger.debug("Building mail session with username=" + senderMail + ", password=" + senderPassword + "...");
		Session session = Session.getDefaultInstance(mailProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderMail, senderPassword);
			}
		});
		session.setDebug(getDebugMail(mailProperties));
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
