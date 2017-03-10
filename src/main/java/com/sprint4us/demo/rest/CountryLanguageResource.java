package com.sprint4us.demo.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint4us.demo.dao.CountryLanguageDAO;
import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@Component
@Path("/")
public class CountryLanguageResource {

	@Autowired
	private CountryLanguageDAO service;

	@POST
	@Path("create/country")
	@Produces(MediaType.APPLICATION_JSON)
	public Country createCountry(String name) {

		Country country = new Country(name);
		service.create(country);
		return country;
		// return name + " with id " + country.getId() + " is created.\n";
	}

	@PUT
	@Path("update/country")
	@Produces(MediaType.APPLICATION_JSON)
	public Country updateCountry(@FormParam("id") Long id,
			@FormParam("l") String langName, @FormParam("p") int percentage) {

		System.out.println(id);
		Country country = service.findCountry(id);
		Language lang = new Language(langName, percentage);
		service.update(country, lang);

		return country;
	}

	@GET
	@Path("search/percentage")
	@Produces(MediaType.TEXT_PLAIN)
	public String searchPercentage(@QueryParam("c") String countryName,
			@QueryParam("l") String langName) {

		int percentage = service.searchPercentage(countryName, langName);

		return String.format("%s has %s%% %s as foreign language.\n",
				countryName, percentage, langName);
	}
}
