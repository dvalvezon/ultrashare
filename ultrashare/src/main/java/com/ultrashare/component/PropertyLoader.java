package com.ultrashare.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ultrashare.type.PropertyType;

public class PropertyLoader {

	private static final Logger logger = Logger.getLogger(PropertyLoader.class);

	private PropertyLoader() {

	}

	public static Properties loadProperties(PropertyType type) throws Exception {
		logger.debug("Begin of method loadProperties(PropertyType).");
		Properties prop = new Properties();
		try {
			logger.debug("Loading Properties file from type: " + type.toString() + "...");
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(type.getUri()));
			logger.debug("Properties file loaded.");
			return prop;
		} catch (FileNotFoundException e) {
			logger.error("Could not find file with URI: " + type.getUri(), e);
			throw e;
		} catch (IOException e) {
			logger.error("Could not read InputStream for file with URI: " + type.getUri(), e);
			throw e;
		} finally {
			logger.debug("End of method loadProperties(PropertyType).");
		}
	}
}
