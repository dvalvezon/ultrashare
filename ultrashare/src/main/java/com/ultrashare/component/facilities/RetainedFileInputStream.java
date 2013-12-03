package com.ultrashare.component.facilities;

import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class RetainedFileInputStream extends InputStream {

	private BufferedInputStream newFileInputStream;
	private boolean isAlreadyClosedByClient = false;
	private boolean isAlreadyFreeFromRetention = false;

	public RetainedFileInputStream(FileDescriptor retainedFileFD) {
		this.newFileInputStream = new BufferedInputStream(new FileInputStream(retainedFileFD));
	}

	@Override
	public int read() throws IOException {
		return newFileInputStream.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return newFileInputStream.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return newFileInputStream.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return newFileInputStream.skip(n);
	}

	@Override
	public int available() throws IOException {
		return newFileInputStream.available();
	}

	@Override
	public synchronized void close() throws IOException {
		if (isAlreadyFreeFromRetention && !isAlreadyClosedByClient) {
			// Prevents calling close more than once...
			isAlreadyClosedByClient = true;
			newFileInputStream.close();
		} else if (!isAlreadyClosedByClient) {
			isAlreadyClosedByClient = true;
		}
	}

	public synchronized void canNowBeClosedByClient() throws IOException {
		if (isAlreadyClosedByClient && !isAlreadyFreeFromRetention) {
			// Prevents calling close more than once...
			isAlreadyFreeFromRetention = true;
			newFileInputStream.close();
		} else if (!isAlreadyFreeFromRetention) {
			isAlreadyFreeFromRetention = true;
		}
	}

	@Override
	public void mark(int readlimit) {
		newFileInputStream.mark(readlimit);
	}

	@Override
	public void reset() throws IOException {
		newFileInputStream.reset();
	}

	@Override
	public boolean markSupported() {
		return newFileInputStream.markSupported();
	}

}
