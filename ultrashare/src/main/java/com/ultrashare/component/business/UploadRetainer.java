package com.ultrashare.component.business;

import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.RetainedFileInputStream;
import com.ultrashare.component.facilities.Validate;

@Component
@ApplicationScoped
public final class UploadRetainer {

	private static final Logger logger = Logger.getLogger(UploadRetainer.class);

	protected UploadRetainer() {

	}

	private Hashtable<Long, InputStreamProjection> retainerMap = new Hashtable<Long, InputStreamProjection>();
	private Hashtable<Long, List<RetainedFileInputStream>> retainedInputStreamMap = new Hashtable<Long, List<RetainedFileInputStream>>();

	protected boolean retain(Long uploadId, InputStream retainedInputStream) throws IOException {
		if (!Validate.ifAnyObjectIsNull(uploadId, retainedInputStream)) {
			retainerMap.put(uploadId, getInputStreamProjection(retainedInputStream));
			retainedInputStreamMap.put(uploadId, new ArrayList<RetainedFileInputStream>());
			return true;
		} else {
			return false;
		}
	}

	private InputStreamProjection getInputStreamProjection(InputStream retainedInputStream) throws IOException {
		if (retainedInputStream instanceof ByteArrayInputStream) {
			byte[] bytes = IOUtils.toByteArray(retainedInputStream);
			retainedInputStream.reset();
			return new ByteArrayInputStreamProjection(bytes);
		} else if (retainedInputStream instanceof FileInputStream) {
			return new FileInputStreamProjection(((FileInputStream) retainedInputStream).getFD());
		}
		return null;
	}

	protected boolean release(Long uploadId) {
		if (!Validate.ifAnyObjectIsNull(uploadId) && retainerMap.containsKey(uploadId)) {
			retainerMap.remove(uploadId);
			List<RetainedFileInputStream> listToClean = retainedInputStreamMap.remove(uploadId);
			for (RetainedFileInputStream retainedFileIS : listToClean) {
				try {
					retainedFileIS.canNowBeClosedByClient();
				} catch (IOException e) {
					logger.error(Log.message("Could not close a marked to close RetainedFileInputStream", Log.entry("retainedFileIS", retainedFileIS)), e);
				}
			}
			// Clear all elements to prevent retained objects blocking GC on others...
			listToClean.clear();
			return true;
		} else {
			return false;
		}
	}

	protected InputStream getProvisoryStream(Long uploadId) {
		if (!Validate.ifAnyObjectIsNull(uploadId) && retainerMap.containsKey(uploadId)) {
			InputStream returnStream = retainerMap.get(uploadId).getRealInputStream();
			if (returnStream instanceof RetainedFileInputStream) {
				retainedInputStreamMap.get(uploadId).add((RetainedFileInputStream) returnStream);
			}
			return returnStream;
		} else {
			return null;
		}
	}

	private abstract class InputStreamProjection {
		public abstract InputStream getRealInputStream();
	}

	private class ByteArrayInputStreamProjection extends InputStreamProjection {

		private byte[] byteArray;

		public ByteArrayInputStreamProjection(byte[] byteArray) {
			this.byteArray = byteArray;
		}

		@Override
		public InputStream getRealInputStream() {
			return new ByteArrayInputStream(byteArray);
		}

	}

	private class FileInputStreamProjection extends InputStreamProjection {

		private final FileDescriptor fileDescriptor;

		public FileInputStreamProjection(FileDescriptor fileDescriptor) {
			this.fileDescriptor = fileDescriptor;
		}

		@Override
		public InputStream getRealInputStream() {
			return new RetainedFileInputStream(fileDescriptor);
		}

	}

}
