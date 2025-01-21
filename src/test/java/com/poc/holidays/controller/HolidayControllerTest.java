package com.poc.holidays.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.holidays.dto.HolidayRequest;
import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;
import com.poc.holidays.service.HolidayServiceImpl;

@WebMvcTest(HolidayController.class)
class HolidayControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HolidayServiceImpl holidayService;

	@Autowired
	private ObjectMapper objectMapper;

	private Holiday holiday;
	private HolidayRequest holidayRequest;

	@BeforeEach
	void setUp() {
		holiday = new Holiday("New Year", "2025-01-20", "Wednesday", Country.USA);
		holidayRequest = new HolidayRequest("New Year", "2025-01-20", "Wednesday", "USA");
	}

	@Test
	void testGetHolidays() throws Exception {
		Mockito.when(holidayService.getAll()).thenReturn(ResponseEntity.ok(Collections.singletonList(holiday)));

		mockMvc.perform(get("/poc/holidays/getAll")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value("New Year"));
	}

	@Test
	void testAddHoliday_Success() throws Exception {
		Mockito.when(holidayService.addHoliday(any(Holiday.class))).thenReturn(holiday);

		mockMvc.perform(post("/poc/holidays/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(holidayRequest))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("New Year"));
	}

	@Test
	void testAddHoliday_InvalidCountry() throws Exception {
		HolidayRequest invalidRequest = new HolidayRequest("Holiday", "2025-01-20", "Friday", "INVALID");

		mockMvc.perform(post("/poc/holidays/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest))).andExpect(status().isBadRequest());
		// .andExpect(jsonPath("$.errors[0]").value("Country is not valid"));
	}

	@Test
	void testUpdateHoliday_Success() throws Exception {
		Mockito.when(holidayService.updateHoliday(any(Holiday.class))).thenReturn(ResponseEntity.ok("Updated"));

		mockMvc.perform(put("/poc/holidays/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(holiday))).andExpect(status().isOk())
				.andExpect(content().string("Updated"));
	}

	@Test
	void testUpdateHoliday_NotFound() throws Exception {
		Mockito.when(holidayService.updateHoliday(any(Holiday.class)))
				.thenThrow(new RuntimeException("No record found"));

		mockMvc.perform(put("/poc/holidays/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(holiday))).andExpect(status().isNotFound())
				.andExpect(content().string("No record found"));
	}

	@Test
	void testDeleteHoliday_Success() throws Exception {
		Mockito.when(holidayService.deleteHoliday(eq(1))).thenReturn(ResponseEntity.ok("Deleted"));

		mockMvc.perform(delete("/poc/holidays/1")).andExpect(status().isOk()).andExpect(content().string("Deleted"));
	}

	@Test
	void testDeleteHolidayByCountry_Success() throws Exception {
		Mockito.when(holidayService.deleteHolidayByCountry(eq("USA"))).thenReturn(ResponseEntity.ok("Deleted"));

		mockMvc.perform(delete("/poc/holidays/country/USA")).andExpect(status().isOk())
				.andExpect(content().string("Deleted"));
	}

	@Test
	void testUploadHolidays_InvalidFile() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.multipart("/poc/holidays/upload")).andExpect(status().isBadRequest())
				.andExpect(content().string("Please upload Excel file"));
	}
}