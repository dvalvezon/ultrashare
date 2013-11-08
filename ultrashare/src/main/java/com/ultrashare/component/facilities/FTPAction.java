package com.ultrashare.component.facilities;

import org.apache.commons.net.ftp.FTPClient;

public abstract class FTPAction<T> {

	protected abstract T executeAction(FTPClient openFTPClient);
}
