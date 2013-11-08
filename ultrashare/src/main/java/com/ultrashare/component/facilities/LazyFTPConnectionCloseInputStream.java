package com.ultrashare.component.facilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

public final class LazyFTPConnectionCloseInputStream extends InputStream {

	private FTPClient ftpClient;
	private InputStream inputStreamBeingDecorated;

	public LazyFTPConnectionCloseInputStream(FTPClient ftpClient, InputStream inputStreamBeingDecorated) {
		this.ftpClient = ftpClient;
		this.inputStreamBeingDecorated = inputStreamBeingDecorated;
	}

	@Override
	public int read() throws IOException {
		return inputStreamBeingDecorated.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return inputStreamBeingDecorated.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return inputStreamBeingDecorated.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return inputStreamBeingDecorated.skip(n);
	}

	@Override
	public int available() throws IOException {
		return inputStreamBeingDecorated.available();
	}

	@Override
	public void close() throws IOException {
		inputStreamBeingDecorated.close();
		ftpClient.completePendingCommand();
		ftpClient.disconnect();
	}

	@Override
	public void mark(int readlimit) {
		inputStreamBeingDecorated.mark(readlimit);
	}

	@Override
	public void reset() throws IOException {
		inputStreamBeingDecorated.reset();
	}

	@Override
	public boolean markSupported() {
		return inputStreamBeingDecorated.markSupported();
	}

}
