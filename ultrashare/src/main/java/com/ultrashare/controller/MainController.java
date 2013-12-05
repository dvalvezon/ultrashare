package com.ultrashare.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ultrashare.component.facilities.Log;
import com.ultrashare.component.facilities.Validate;
import com.ultrashare.component.vo.SearchVO;
import com.ultrashare.dao.UploadDAO;
import com.ultrashare.model.Upload;

@Resource
public class MainController {

	private static Logger logger = Logger.getLogger(MainController.class);

	private Result result;

	private UploadDAO uploadDao;

	public MainController(Result result, UploadDAO uploadDao) {
		this.result = result;
		this.uploadDao = uploadDao;

	}

	@Post
	public List<SearchVO> search(String keyword) {
		logger.debug(Log.header("search", Log.entry("keyword", keyword)));
		ArrayList<SearchVO> searchItems = new ArrayList<SearchVO>();
		for (Upload upload : uploadDao.findUploadsByFileName(keyword)) {
			searchItems.add(new SearchVO(upload));
		}
		logger.debug(Log.footer("error"));
		return searchItems;
	}

	public void error(String throwable) {
		logger.debug(Log.header("error", Log.entry("throwable", throwable)));
		if (Validate.ifAnyObjectIsNull(throwable)) {
			result.redirectTo(UploadController.class).form();
		}
		logger.debug(Log.footer("error"));
	}

	@Get
	public void about() {
		logger.debug(Log.header("about"));
		logger.debug(Log.footer("about"));
	}

	@Get
	public void contact() {
		logger.debug(Log.header("contact"));
		logger.debug(Log.footer("contact"));
	}
}
