package com.ultrashare.component.facilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class FTPRetrieveAction extends FTPAction<InputStream> {

	private static final Logger logger = Logger.getLogger(FTPRetrieveAction.class);

	private String fileName;

	public FTPRetrieveAction(String fileName) {
		this.fileName = fileName;
	}

	@Override
	protected InputStream executeAction(FTPClient ftp) {
		logger.debug("Begin of FTPRetrieveAction's executeAction(FTPClient) method.");
		InputStream inputStream = null;
		try {
			logger.debug("Retrieving InputStream for file " + fileName + "...");
			inputStream = ftp.retrieveFileStream(fileName);
			logger.debug("InputStream retrieved!");
		} catch (IOException e) {
			logger.error("Could not retrieve InputStream for file " + fileName + "...", e);
		}
		logger.debug("End of FTPRetrieveAction's executeAction(FTPClient) method.");
		return inputStream;
	}
}
