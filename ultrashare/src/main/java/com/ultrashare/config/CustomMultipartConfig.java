package com.ultrashare.config;

import br.com.caelum.vraptor.interceptor.multipart.DefaultMultipartConfig;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

	private static final long TEN_GIGABYTE_SIZE_LIMIT = 10737418240L;

	public long getSizeLimit() {
		return TEN_GIGABYTE_SIZE_LIMIT;
	}
}
