package com.sprint4us.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Language {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int percentage;

	public Language() {

	}

	public Language(String name, int percentage) {

		this.name = name;
		this.percentage = percentage;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public int getPercentage() {

		return percentage;
	}

	public void setPercentage(int percentage) {

		this.percentage = percentage;
	}

	@Override
	public String toString() {

		return "Language [id=" + id + ", name=" + name + ", percentage="
				+ percentage + "]";
	}
}
