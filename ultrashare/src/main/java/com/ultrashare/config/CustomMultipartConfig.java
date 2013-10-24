package com.ultrashare.config;

import br.com.caelum.vraptor.interceptor.multipart.DefaultMultipartConfig;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

	private static final long ONE_GIGABYTE_SIZE_LIMIT = 1073741824;

	public long getSizeLimit() {
		return ONE_GIGABYTE_SIZE_LIMIT;
	}
}
