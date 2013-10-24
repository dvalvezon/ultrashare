package com.ultrashare.controller;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

@Resource
public class UploadController {

	public void form() {

	}

	@Post
	public void upload(UploadedFile arquivo, String userName, String userMail) {
		System.out.println(arquivo + " | " + userName + " | " + userMail);
	}
}
