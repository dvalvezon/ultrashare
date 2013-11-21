package com.ultrashare.config;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

import com.ultrashare.component.facilities.Log;
import com.ultrashare.controller.MainController;

@Intercepts
@RequestScoped
public class ExceptionInterceptor implements Interceptor {

	private final static Logger logger = Logger.getLogger(ExceptionInterceptor.class);

	private Result result;

	public ExceptionInterceptor(Result result) {
		this.result = result;

	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		try {
			stack.next(method, resourceInstance);
		} catch (Throwable t) {
			logger.fatal(Log.message("Error intercepted in ExceptionInterceptor... Preventing user to see an exception screen... Cause:"), t);
			result.redirectTo(MainController.class).error(t.toString());
			logger.debug(Log.message("Redirecting to error page..."));
		}
	}

	public boolean accepts(ResourceMethod method) {
		return true;
	}

}
