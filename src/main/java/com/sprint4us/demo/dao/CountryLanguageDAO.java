package com.sprint4us.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

@Repository
public class CountryLanguageDAO {

	@PersistenceContext(unitName = "myPU", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Transactional
	public void create(Object obj) {

		em.persist(obj);
	}

	@Transactional
	public void update(Country country, Language language) {

		country.addLanguage(language);
		em.flush();
	}

	@Transactional
	public int updatePercentage(String languageName, int percentage) {

		int totalUpdated = em.createQuery(
				"update Language l set l.percentage=" + percentage
						+ " where l.name='" + languageName + "'")
				.executeUpdate();

		return totalUpdated;
	}

	@Transactional
	public void syncToDB(Object obj) {

		em.refresh(obj);
	}

	public Country findCountry(Long id) {

		return em.find(Country.class, id);
	}

	public List<Country> retrieveAllCountries() {

		TypedQuery<Country> q = em.createQuery("select c from Country c",
				Country.class);

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Country>();
		}
	}

	public List<Language> retrieveAllLanguages() {

		TypedQuery<Language> q = em.createQuery("select l from Language l",
				Language.class);

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Language>();
		}
	}

	public Country searchCountry(String countryName) {

		TypedQuery<Country> q = em.createQuery(
				"select c from Country c where c.name='" + countryName + "'",
				Country.class);

		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return new Country();
		}
	}

	public List<Country> searchCountries(String languageName) {

		TypedQuery<Country> q = em.createQuery(
				"select c from Country c, Language l where l.name='"
						+ languageName + "' and l member of c.languages",
				Country.class);

		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Country>();
		}
	}

	public Integer searchPercentage(String countryName, String languageName) {

		TypedQuery<Integer> q = em.createQuery(
				"select l.percentage from Country c, Language l where c.name='"
						+ countryName + "' and l.name='" + languageName
						+ "' and l member of c.languages", Integer.class);

		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}
}
