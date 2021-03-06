package com.ultrashare.component.facilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class FTPSendAction extends FTPAction<Boolean> {

	private static final Logger logger = Logger.getLogger(FTPSendAction.class);

	private String fileName;
	private InputStream inputStream;

	public FTPSendAction(String fileName, InputStream inputStream) {
		this.fileName = fileName;
		this.inputStream = inputStream;
	}

	@Override
	protected Boolean executeAction(FTPClient ftpClient) {
		logger.debug("Begin of FTPSendAction's executeAction(FTPClient) method.");
		boolean executed = true;
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			logger.debug("Sending file " + fileName + "...");
			ftpClient.storeFile(fileName, inputStream);
			logger.debug("File " + fileName + " sent!");
		} catch (IOException e) {
			logger.error("Could not send file to FTP Server...", e);
			executed = false;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("Could not close the Input Stream...", e);
			}
			try {
				ftpClient.disconnect();
				logger.debug("FTPClient Disconnected.");
			} catch (IOException e) {
				logger.error("An error ocurred while trying to close the FTPClient.", e);
			}
		}
		logger.debug("End of FTPSendAction's executeAction(FTPClient) method.");
		return executed;
	}
}
