package com.ultrashare.dao;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ultrashare.model.Upload;

@Component
@RequestScoped
public class UploadDAO extends GenericDAO<Upload> {

	public UploadDAO(EntityManager em) {
		super(Upload.class, em);
	}
}
