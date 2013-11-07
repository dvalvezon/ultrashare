package com.ultrashare.component.business;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public abstract class AbstractProcessor<T> {

	public void process(T instance) {
		new Thread(new Processor<T, AbstractProcessor<T>>(this, instance)).start();
	}

	protected abstract void execute(T instance);
}

class Processor<T, U extends AbstractProcessor<T>> implements Runnable {

	private final U processor;
	private T instance;

	public Processor(U processor, T instance) {
		this.processor = processor;
		this.instance = instance;

	}

	public void run() {
		this.processor.execute(instance);
	}
}