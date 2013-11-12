package com.ultrashare.component.facilities;

import java.util.Map.Entry;

public class LogEntry implements Entry<String, Object> {

	private String paramName;
	private Object paramValue;

	public LogEntry(String paramName, Object paramValue) {
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	public String getKey() {
		return paramName;
	}

	public Object getValue() {
		return paramValue;
	}

	public Object setValue(Object value) {
		Object oldValue = paramValue;
		paramValue = value;
		return oldValue;
	}

	@Override
	public String toString() {
		return "[" + paramName + "=" + paramValue + "]";
	}
}