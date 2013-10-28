package com.ultrashare.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import com.ultrashare.model.Upload;

//@Component
//@RequestScoped
public class UploadDAO extends GenericDAO<Upload> {

	private EntityManager em;

	public UploadDAO(EntityManager em) {
		super(Upload.class, em);
		this.em = em;
	}

	@PostConstruct
	private void create() {
		em.getTransaction().begin();
	}

	@PreDestroy
	private void destroy() {
		em.getTransaction().commit();
	}
}
