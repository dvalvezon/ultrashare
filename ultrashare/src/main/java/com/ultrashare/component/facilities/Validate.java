package com.ultrashare.component.facilities;

import org.apache.commons.lang3.StringUtils;

public final class Validate {

	private Validate() {

	}

	public static boolean ifAnyObjectIsNull(Object... objects) {
		if (objects == null || objects.length == 0) {
			return true;
		}
		return checkIfAnyObjectIsNull(objects);
	}

	public static boolean ifAnyStringIsNullOrEmpty(String... strings) {
		for (String string : strings) {
			if (StringUtils.isBlank(string)) {
				return true;
			}
		}
		return false;
	}

	public static boolean ifAnyStringIsNotNumeric(String... strings) {
		for (String string : strings) {
			if (!StringUtils.isNumeric(string)) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkIfAnyObjectIsNull(Object[] objects) {
		for (Object object : objects) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}
}
