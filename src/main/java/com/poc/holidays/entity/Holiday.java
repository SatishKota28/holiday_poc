package com.poc.holidays.entity;

import com.poc.holidays.enums.Country;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Holiday", uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "country" }) })
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "Holiday name can not be null or empty")
	@Column(name = "name")
	private String name;
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format YYYY-MM-DD")
	@NotBlank(message = "Holiday name can not be null or empty")
	@Column(name = "date")
	private String holidayDate;
	@Column(name = "day")
	private String day;
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	@Column(name = "country", columnDefinition = "enum('USA','CA')")
	private Country country;

	public Holiday() {
	}

	public Holiday(int id, String name, String holidayDate, String day, Country country) {
		this.id = id;
		this.name = name;
		this.holidayDate = holidayDate;
		this.day = day;
		this.country = country;
	}

	public Holiday(String name, String holidayDate, String day, Country country) {
		this.name = name;
		this.holidayDate = holidayDate;
		this.day = day;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Holiday{" + "id=" + id + ", name='" + name + '\'' + ", holidayDate='" + holidayDate + '\'' + ", day='"
				+ day + '\'' + ", country=" + country + '}';
	}
}
