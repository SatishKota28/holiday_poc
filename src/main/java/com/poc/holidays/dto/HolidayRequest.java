package com.poc.holidays.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class HolidayRequest {
	@NotBlank(message = "Holiday name can not be null or empty")
	@JsonProperty("name")
	private String name;
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format YYYY-MM-DD")
	@NotBlank(message = "Date can not be null or empty")
	@JsonProperty("holidayDate")
	private String holidayDate;
	@NotBlank(message = "Day can not be null or empty")
	@JsonProperty("day")
	private String day;
	@NotBlank(message = "Country can not be null or empty")
	@JsonProperty("country")
	private String country;

	public HolidayRequest() {
	}

	public HolidayRequest(String name, String holidayDate, String day, String country) {
		this.name = name;
		this.holidayDate = holidayDate;
		this.day = day;
		this.country = country;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("holidayDate")
	public String getHolidayDate() {
		return holidayDate;
	}

	@JsonProperty("holidayDate")
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	@JsonProperty("day")
	public String getDay() {
		return day;
	}

	@JsonProperty("day")
	public void setDay(String day) {
		this.day = day;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}
}
