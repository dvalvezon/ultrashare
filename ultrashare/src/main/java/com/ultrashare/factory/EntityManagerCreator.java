package com.ultrashare.factory;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

import com.ultrashare.component.facilities.EntityManagerProxy;

@Component
@ApplicationScoped
public class EntityManagerCreator implements ComponentFactory<EntityManager> {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsqldb");

	@PreDestroy
	private void closeEntityManagerFactoryOnApplicationShutdown() {
		emf.close();
	}

	public EntityManager getInstance() {
		return new EntityManagerProxy(emf);
	}

}
