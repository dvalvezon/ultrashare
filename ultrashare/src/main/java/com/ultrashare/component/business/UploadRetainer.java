package com.ultrashare.component.business;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Hashtable;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.ultrashare.component.facilities.Validate;

@Component
@ApplicationScoped
public final class UploadRetainer {

	private static final Logger logger = Logger.getLogger(UploadRetainer.class);

	protected UploadRetainer() {

	}

	private Hashtable<Long, InputStreamProjection> retainerMap = new Hashtable<Long, InputStreamProjection>();

	protected boolean retain(Long uploadId, InputStream retainedInputStream) throws IOException {
		if (!Validate.ifAnyObjectIsNull(uploadId, retainedInputStream)) {
			retainerMap.put(uploadId, getInputStreamProjection(retainedInputStream));
			return true;
		} else {
			return false;
		}
	}

	private InputStreamProjection getInputStreamProjection(InputStream inputStreamToRetain) throws IOException {
		if (inputStreamToRetain instanceof ByteArrayInputStream) {
			return new ByteArrayInputStreamProjection((ByteArrayInputStream) inputStreamToRetain);
		} else if (inputStreamToRetain instanceof FileInputStream) {
			return new FileInputStreamProjection((FileInputStream) inputStreamToRetain);
		}
		return null;
	}

	protected boolean release(Long uploadId) {
		if (!Validate.ifAnyObjectIsNull(uploadId) && retainerMap.containsKey(uploadId)) {
			retainerMap.remove(uploadId);
			return true;
		} else {
			return false;
		}
	}

	protected InputStream getProvisoryStream(Long uploadId) throws IOException {
		if (!Validate.ifAnyObjectIsNull(uploadId) && retainerMap.containsKey(uploadId)) {
			InputStream returnStream = retainerMap.get(uploadId).getRealInputStream();
			return returnStream;
		} else {
			return null;
		}
	}

	private abstract class InputStreamProjection {
		public abstract InputStream getRealInputStream() throws IOException;
	}

	private class ByteArrayInputStreamProjection extends InputStreamProjection {

		private byte[] byteArray;

		public ByteArrayInputStreamProjection(ByteArrayInputStream byteArrayOutputStream) throws IOException {
			this.byteArray = IOUtils.toByteArray(byteArrayOutputStream);
		}

		@Override
		public InputStream getRealInputStream() {
			return new ByteArrayInputStream(byteArray);
		}

	}

	private class FileInputStreamProjection extends InputStreamProjection {

		private final File file;

		public FileInputStreamProjection(FileInputStream fileInputStream) {
			this.file = extractFileFromInputStream(fileInputStream);
		}

		private File extractFileFromInputStream(FileInputStream fileInputStream) {
			File extractedFile = null;
			try {
				Field pathField = fileInputStream.getClass().getDeclaredField("path");
				pathField.setAccessible(true);
				extractedFile = new File(pathField.get(fileInputStream).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return extractedFile;
		}

		@Override
		public InputStream getRealInputStream() throws IOException {
			return new FileInputStream(file);
		}

	}

}
