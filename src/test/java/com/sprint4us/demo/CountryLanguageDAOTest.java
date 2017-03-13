package com.sprint4us.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sprint4us.demo.dao.CountryLanguageDAO;
import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CountryLanguageDAOTest {

	@Autowired
	private CountryLanguageDAO service;

	/*
	 * According to https://en.wikipedia.org/wiki/Languages_of_{Country}, main
	 * foreign languages in some European countries exhibit below,
	 * 
	 * France : English 39%, Spanish 13%, German 8%, Italian 5% Germany :
	 * English 56%, French 15%, Russian 5% Italy : English 34%, French 16%,
	 * Spanish 11%, German 5% Spain : English 27%, French 12%, German 2%
	 * the_United_Kingdom: French 23%, German 9%, Spanish 8%
	 */

	@Test
	public void testCreateOK() {

		String[] countryNames = { "France", "Germany", "Italy", "Spain", "UK" };
		String[][][] languageNamePercentages = {
				{ { "English", "39" }, { "Spanish", "13" }, { "German", "8" },
						{ "Italian", "5" } },
				{ { "English", "56" }, { "French", "15" }, { "Russian", "5" } },
				{ { "English", "34" }, { "French", "16" }, { "Spanish", "11" },
						{ "German", "5" } },
				{ { "English", "27" }, { "French", "12" }, { "German", "5" } },
				{ { "French", "23" }, { "German", "9" }, { "Spanish", "8" } } };

		int expectedTotalCountries = countryNames.length;
		int expectedTotalLanguages = 0;

		for (int i = 0; i < expectedTotalCountries; i++) {
			Country country = new Country(countryNames[i]);
			service.create(country);

			for (String[] languageNamePercentage : languageNamePercentages[i]) {
				++expectedTotalLanguages;
				Language language = new Language(languageNamePercentage[0],
						Integer.valueOf(languageNamePercentage[1]));
				service.update(country, language);
			}
		}

		int actualTotalCountries = service.retrieveAllCountries().size();
		assertEquals("The total countries are not equal",
				expectedTotalCountries, actualTotalCountries);

		int actualTotalLanguages = service.retrieveAllLanguages().size();
		assertEquals("The total languages are not equal",
				expectedTotalLanguages, actualTotalLanguages);
	}

	@Test
	public void testSearchOK() {

		Country country = service.searchCountry("Spain");
		String acutalCountryName = country.getName();
		assertEquals("The country name is not equal", "Spain",
				acutalCountryName);

		int acutalNumberOfCountries = service.searchCountries("English").size();
		assertEquals("The number of countries is not equal", 4,
				acutalNumberOfCountries);

		int actualPercentage = service.searchPercentage("Germany", "French");
		assertEquals("The percentage is not equal", 15, actualPercentage);
	}

	@Test
	public void testUpdateOK() {

		int actualNumberOfUpdated = service.updatePercentage("English", 99);
		assertEquals("The number of updated is not equal", 4,
				actualNumberOfUpdated);

		Country country = service.searchCountry("Italy");

		Language language = country.getLanguages().stream()
				.filter(l -> l.getName() == "English").findFirst().get();
		int actualPercentage = language.getPercentage();
		assertEquals("The percentage is not equal", 34, actualPercentage);

		service.syncToDB(country);
		actualPercentage = language.getPercentage();
		assertEquals("The percentage is not equal", 99, actualPercentage);
	}
}
