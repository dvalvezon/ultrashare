package com.ultrashare.dao;

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

	@PreDestroy
	public void postConstruct() {
		if (em.isOpen()) {
			em.close();
		}
	}
}
