package com.dummy.ids;

import org.slf4j.Logger;

public class ExceptionUtils {

	public static void throwRuntimeException(String message, Throwable ex, Logger logger) {
		logger.error(message, ex);
		throw new RuntimeException(message, ex);
	}

	public static void checkNotNull(Object obj, String message) {
		if (obj == null) {
			throw new NullPointerException(message);
		}
	}

}
