package com.sprint4us.demo.rest;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class CountryLanguageResourceConf extends ResourceConfig {

	public CountryLanguageResourceConf() {

		packages(true, "com.sprint4us.demo.rest");
		register(LoggingFeature.class);
	}
}
