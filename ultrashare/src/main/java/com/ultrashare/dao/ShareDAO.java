package com.ultrashare.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ultrashare.model.Share;

@Component
@RequestScoped
public class ShareDAO extends GenericDAO<Share> {

	private EntityManager em;

	public ShareDAO(EntityManager em) {
		super(Share.class, em);
		this.em = em;
	}

	@PreDestroy
	private void closeEmAfterRequest() {
		if (em.isOpen()) {
			em.close();
		}
	}
}
