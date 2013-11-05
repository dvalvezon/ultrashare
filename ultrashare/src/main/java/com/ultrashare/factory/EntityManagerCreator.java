package com.ultrashare.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class EntityManagerCreator implements ComponentFactory<EntityManager> {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

	// private EntityManager em;

	// @PostConstruct
	// private void create() {
	// em = emf.createEntityManager();
	// }

	// @PreDestroy
	// private void destroy() {
	// em.close();
	// emf.close();
	// }

	public EntityManager getInstance() {
		// return em;
		return emf.createEntityManager();
	}

}
