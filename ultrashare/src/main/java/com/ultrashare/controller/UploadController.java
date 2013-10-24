package com.ultrashare.controller;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import com.ultrashare.component.FTPPublisher;

@Resource
public class UploadController {

	public void form() {

	}

	@Post
	public void upload(UploadedFile arquivo, String userName, String userMail, String friendsMails) {
		System.out.println(arquivo + " | " + userName + " | " + userMail + " | " + friendsMails);
		System.out.println("File size = " + arquivo.getSize() / 1024 + "MB");

		FTPPublisher.getInstance().sendFileFromStream(arquivo.getFile(), arquivo.getFileName());

		// OutputStream outputStream = null;
		// InputStream inputStream = arquivo.getFile();
		// File f = new File("C:\\UltraShareTest\\" + arquivo.getFileName());
		// try {
		// if (f.createNewFile()) {
		// outputStream = new FileOutputStream(f);
		// int read = 0;
		// byte[] bytes = new byte[1024];
		//
		// while ((read = inputStream.read(bytes)) != -1) {
		// outputStream.write(bytes, 0, read);
		// }
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (inputStream != null) {
		// try {
		// inputStream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// if (outputStream != null) {
		// try {
		// // outputStream.flush();
		// outputStream.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// }
		// }
	}
}
