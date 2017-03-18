package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sprint4us.demo.dao.CountryLanguageDAO;
import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;
import com.sprint4us.demo.rest.CountryLanguageResourceConf;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CoutryLanguageResourceTest extends JerseyTest {

	@Mock
	private CountryLanguageDAO service;

	@Override
	public Application configure() {

		ResourceConfig config = new CountryLanguageResourceConf();
		config.register(new MockProvider());

		return config;
	}

	private class MockProvider extends AbstractBinder
			implements Factory<CountryLanguageDAO> {

		@Override
		public void configure() {

			bindFactory(this).to(CountryLanguageDAO.class).in(Singleton.class);
		}

		@Override
		public CountryLanguageDAO provide() {

			return service;
		}

		@Override
		public void dispose(CountryLanguageDAO service) {

		}
	}

	@Test
	public void testResources() {

		doNothing().when(service).create(any(Country.class));

		Response resp = target("create/country").request()
				.post(Entity.entity("Netherlands", MediaType.TEXT_PLAIN));
		Country country = resp.readEntity(Country.class);
		assertEquals(200, resp.getStatus());
		assertEquals("Netherlands", country.getName());

		when(service.findCountry(999L)).thenReturn(country);
		doNothing().when(service).update(any(Country.class),
				any(Language.class));

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
		formData.add("id", "999");
		formData.add("l", "English");
		formData.add("p", "90");
		Country updatedCountry = target("update/country")
				.request(MediaType.TEXT_PLAIN)
				.accept(MediaType.APPLICATION_JSON).put(Entity.form(formData))
				.readEntity(Country.class);
		assertEquals(200, resp.getStatus());
		assertEquals("Netherlands", updatedCountry.getName());

		when(service.searchPercentage(country.getName(), "English"))
				.thenReturn(90);

		String str = target("search/percentage")
				.queryParam("c", country.getName()).queryParam("l", "English")
				.request().get().readEntity(String.class);
		assertEquals(200, resp.getStatus());
		assertEquals(String.format("%s has %d%% %s as foreign language.\n",
				country.getName(), 90, "English"), str);
	}
}
