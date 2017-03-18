package com.sprint4us.demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.sprint4us.demo.rest.CountryLanguageResourceConf;

public class CountryLanguageJerseyTestBase extends JerseyTest {

	@Override
	public ResourceConfig configure() {

		return new CountryLanguageResourceConf();
	}
}
