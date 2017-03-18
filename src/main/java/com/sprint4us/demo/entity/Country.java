package com.sprint4us.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.common.collect.Lists;

@Entity
public class Country {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Language> languages = new ArrayList<Language>();

	public Country() {

	}

	public Country(String name) {

		this.name = name;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public List<Language> getLanguages() {

		return languages;
	}

	public void addLanguage(Language language) {

		languages.add(language);
	}

	@Override
	public String toString() {

		return "Country [id=" + id + ", name=" + name + ", languages="
				+ Lists.newArrayList(languages) + "]";
	}
}
