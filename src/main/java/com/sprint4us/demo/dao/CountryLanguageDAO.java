package com.sprint4us.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sprint4us.demo.entity.Country;
import com.sprint4us.demo.entity.Language;

public class CountryLanguageDAO {

	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	public CountryLanguageDAO() {

		emf = Persistence.createEntityManagerFactory("myPU");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	public void create(Object obj) {
		tx.begin();
		em.persist(obj);
		tx.commit();
	}

	public void update(Country country, Language language) {

		tx.begin();
		country.addLanguage(language);
		tx.commit();
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

	public int updatePercentage(String languageName, int percentage) {

		tx.begin();

		int totalUpdated = em.createQuery(
				"update Language l set l.percentage=" + percentage
						+ " where l.name='" + languageName + "'")
				.executeUpdate();
		tx.commit();

		return totalUpdated;
	}

	public void syncToDB(Object obj) {

		em.refresh(obj);
	}

	public void close() {

		em.clear();
		emf.close();
	}
}
