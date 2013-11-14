package com.ultrashare.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ultrashare.model.Download;

@Component
@RequestScoped
public class DownloadDAO extends GenericDAO<Download> {

	private EntityManager em;

	public DownloadDAO(EntityManager em) {
		super(Download.class, em);
		this.em = em;
	}

	@PreDestroy
	private void closeEmAfterRequest() {
		if (em.isOpen()) {
			em.close();
		}
	}
}
