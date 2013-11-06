package com.ultrashare.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.ultrashare.type.PropertyType;

public class FTPPublisher {

	private static final Logger logger = Logger.getLogger(FTPPublisher.class);

	private static final FTPPublisher instance = new FTPPublisher();

	private static Properties ftpProperties;

	// TODO - MANY KINDS OF VALIDATIONS

	private FTPPublisher() {
		try {
			ftpProperties = PropertyLoader.loadProperties(PropertyType.FTP_CONFIGS);
		} catch (Exception e) {
			logger.error("FTP Properties not found.", e);
			throw new RuntimeException(e);
		}
	}

	public static FTPPublisher getInstance() {
		return instance;
	}

	public void sendFileFromUri(String fileUri) throws FileNotFoundException {
		sendFile(getISFromString(fileUri), getFileNameFromString(fileUri));
	}

	public void sendFileFromStream(InputStream is, String fileName) {
		sendFile(is, fileName);
	}

	private static void sendFile(InputStream is, String fileName) {
		logger.debug("Begin of private sendFile(InputStream, String) method.");
		FTPClient ftp = new FTPClient();
		try {
			logger.debug("Trying to connecto to the ftp server.");
			ftp.connect(ftpProperties.getProperty("ftp.config.host"));
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				logger.debug("Authenticating...");
				if (ftp.login(ftpProperties.getProperty("ftp.credential.username"), ftpProperties.getProperty("ftp.credential.password"))) {
					logger.debug("Authentication successful.");
					ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
					logger.debug("Sending file " + fileName + "...");
					ftp.storeFile(fileName, is);
					logger.debug("File " + fileName + " sent!");
				} else {
					logger.debug("Authentication failure.");
				}
			} else {
				logger.debug("The ftp server did not respond.");
			}
			logger.debug("Disconnecting from ftp server...");
			ftp.disconnect();
			logger.debug("Disconnected.");
		} catch (Exception e) {
			logger.error("An error has ocurred...", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("Could not close the Input Stream...", e);
			}
		}
		logger.debug("End of private sendFile(InputStream, String) method.");
	}

	private static InputStream getISFromString(String uri) throws FileNotFoundException {
		return new FileInputStream(uri);
	}

	private static String getFileNameFromString(String uri) {
		String[] parts = uri.split(getPatternForUriSplit());
		return parts[parts.length - 1];
	}

	private static String getPatternForUriSplit() {
		if (File.separator.equals("/")) {
			return File.separator;
		} else {
			return "\\\\";
		}
	}
}
