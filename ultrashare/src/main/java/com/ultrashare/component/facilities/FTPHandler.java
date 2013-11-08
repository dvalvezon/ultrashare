package com.ultrashare.component.facilities;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.ultrashare.type.PropertyType;

public class FTPHandler {

	private static final Logger logger = Logger.getLogger(FTPHandler.class);

	private static FTPClient ftpClient;

	private static final Properties ftpProperties;
	static {
		try {
			ftpProperties = PropertyLoader.loadProperties(PropertyType.FTP_CONFIGS);
		} catch (Exception e) {
			logger.error("FTP Properties not found.", e);
			throw new RuntimeException(e);
		}
	}

	private static FTPClient getActiveFTPClient() {
		if (ftpClient == null || !ftpClient.isConnected() || !ftpClient.isAvailable()) {
			try {
				ftpClient = new FTPClient();
				logger.debug("Trying to connecto to the ftp server.");
				ftpClient.connect(ftpProperties.getProperty("ftp.config.host"));
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					logger.debug("Authenticating...");
					if (ftpClient.login(ftpProperties.getProperty("ftp.credential.username"), ftpProperties.getProperty("ftp.credential.password"))) {
						logger.debug("Authentication successful.");
					} else {
						logger.debug("Authentication failure.");
					}
				} else {
					logger.debug("The ftp server did not respond.");
				}
			} catch (Exception e) {
				logger.error("An error has ocurred...", e);
			}
		}
		return ftpClient;
	}

	public static <T> T processFTPAction(FTPAction<T> ftpAction) {
		T returnValue = null;
		logger.debug("Begin of processFTPAction(FTPAction) method.");
		FTPClient ftpClient = getActiveFTPClient();
		returnValue = ftpAction.executeAction(ftpClient);
		try {
			ftpClient.completePendingCommand();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("End of processFTPAction(FTPAction) method.");
		return returnValue;
	}
	// public static <T> T processFTPAction(FTPAction<T> ftpAction) {
	// T returnValue = null;
	// logger.debug("Begin of processFTPAction(FTPAction) method.");
	// FTPClient ftp = new FTPClient();
	// try {
	// logger.debug("Trying to connecto to the ftp server.");
	// ftp.connect(ftpProperties.getProperty("ftp.config.host"));
	// if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
	// logger.debug("Authenticating...");
	// if (ftp.login(ftpProperties.getProperty("ftp.credential.username"),
	// ftpProperties.getProperty("ftp.credential.password"))) {
	// logger.debug("Authentication successful.");
	// returnValue = ftpAction.executeAction(ftp);
	// } else {
	// logger.debug("Authentication failure.");
	// }
	// } else {
	// logger.debug("The ftp server did not respond.");
	// }
	// logger.debug("Disconnecting from ftp server...");
	// ftp.disconnect();
	// logger.debug("Disconnected.");
	// } catch (Exception e) {
	// logger.error("An error has ocurred...", e);
	// }
	// logger.debug("End of processFTPAction(FTPAction) method.");
	// return returnValue;
	// }
}
