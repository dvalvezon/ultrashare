package com.ultrashare.component.facilities;

import java.util.Map.Entry;

public final class Log {

	private Log() {

	}

	public static String header(String methodName, LogEntry... params) {
		String message = "Begin of method " + methodName + "() ";
		message += formatParametersMessage(params);
		return message;
	}

	public static String parameters(LogEntry... params) {
		return formatParametersMessage(params);
	}

	public static String footer(String methodName, LogEntry... params) {
		String message = "End of method " + methodName + "() ";
		message += formatParametersMessage(params);
		return message;
	}

	public static String footer(String methodName, Object returnEntry, LogEntry... params) {
		String message = "End of method " + methodName + "() ";
		if (returnEntry != null) {
			message += "Return Value: " + returnEntry + " ";
		}
		message += formatParametersMessage(params);
		return message;
	}

	private static String formatParametersMessage(LogEntry[] params) {
		String paramsMessage = "";
		if (params != null && params.length > 0) {
			paramsMessage = "Parameters: {";
			for (Entry<String, Object> entry : params) {
				paramsMessage += entry + ",";
			}
			paramsMessage += "}";
		}
		return paramsMessage.replace(",}", "}");
	}

	public static LogEntry entry(String key, Object value) {
		return new LogEntry(key, value);
	}
}
