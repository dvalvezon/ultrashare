package com.ultrashare.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ultrashare.model.Upload;

@Component
@RequestScoped
public class UploadDAO extends GenericDAO<Upload> {

	private EntityManager em;

	public UploadDAO(EntityManager em) {
		super(Upload.class, em);
		this.em = em;
	}

	public List<Upload> findUploadsByFileName(String fileName) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileName", "%" + fileName + "%");
		return super.findResults(Upload.UPLOADS_BY_FILE_NAME, parameters);
	}

	@PreDestroy
	private void closeEmAfterRequest() {
		if (em.isOpen()) {
			em.close();
		}
	}
}
