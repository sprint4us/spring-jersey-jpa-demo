package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sprint4us.demo.dao.CountryLanguageDAO;
import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;
import com.sprint4us.demo.rest.CountryLanguageResourceConf;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CoutryLanguageEndToEndTest extends JerseyTest {

	@Autowired
	private CountryLanguageDAO service;

	@Override
	protected Application configure() {

		ResourceConfig config = new CountryLanguageResourceConf();

		return config;
	}

	@Test
	public void testResourcesE2E() {

		Response resp = target("create/country").request().post(
				Entity.entity("Netherlands", MediaType.TEXT_PLAIN));
		Country country = resp.readEntity(Country.class);
		assertEquals(200, resp.getStatus());
		assertEquals("Netherlands", country.getName());

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
		formData.add("id", String.valueOf(country.getId()));
		formData.add("l", "English");
		formData.add("p", "90");
		resp = target("update/country").request(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON).put(Entity.form(formData));
		Country updatedCountry = resp.readEntity(Country.class);
		assertEquals(country.getId(), updatedCountry.getId());
		assertEquals(country.getName(), updatedCountry.getName());
		Language lang = updatedCountry.getLanguages().get(0);
		assertEquals("English", lang.getName());
		assertEquals(90, lang.getPercentage());

		resp = target("search/percentage").queryParam("c", country.getName())
				.queryParam("l", lang.getName()).request().get();
		String str = resp.readEntity(String.class);
		assertEquals(String.format("%s has %d%% %s as foreign language.\n",
				country.getName(), lang.getPercentage(), lang.getName()), str);
	}
}
