package com.ultrashare.dao;

import javax.annotation.PostConstruct;
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

	// public void saveInTransaction(Upload upload) {
	// em.getTransaction().begin();
	// super.save(upload);
	// em.getTransaction().commit();
	// }

	@PostConstruct
	private void create() {
		em.getTransaction().begin();
	}

	@PreDestroy
	private void destroy() {
		em.getTransaction().commit();
	}
}
