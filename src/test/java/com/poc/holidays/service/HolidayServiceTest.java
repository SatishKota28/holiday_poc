package com.poc.holidays.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;
import com.poc.holidays.error.HolidayNotFoundException;
import com.poc.holidays.repository.HolidayRepository;
import com.poc.holidays.utils.ExcelParser;

class HolidayServiceImplTest {

	@InjectMocks
	private HolidayServiceImpl holidayService;

	@Mock
	private HolidayRepository holidayRepository;

	@Mock
	private ExcelParser excelParser;

	private Holiday holiday;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		holiday = new Holiday("New Year", "2025-01-20", "Wednesday", Country.USA);
	}

	@Test
	void testGetAll_HolidaysExist() {
		Mockito.when(holidayRepository.findAll()).thenReturn(Collections.singletonList(holiday));

		ResponseEntity<Object> response = holidayService.getAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody() instanceof List);
		assertEquals(1, ((List<?>) response.getBody()).size());
	}

	@Test
	void testGetAll_NoHolidays() {
		Mockito.when(holidayRepository.findAll()).thenReturn(Collections.emptyList());

		ResponseEntity<Object> response = holidayService.getAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("No record found", response.getBody());
	}

	@Test
	void testAddHoliday_Success() {
		Mockito.when(holidayRepository.findByHolidayDateAndCountry(any(), any())).thenReturn(Optional.empty());
		Mockito.when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday);

		Holiday savedHoliday = holidayService.addHoliday(holiday);

		assertNotNull(savedHoliday);
		assertEquals("New Year", savedHoliday.getName());
	}

	@Test
	void testAddHoliday_Duplicate() {
		Mockito.when(holidayRepository.findByHolidayDateAndCountry(any(), any())).thenReturn(Optional.of(holiday));

		Holiday savedHoliday = holidayService.addHoliday(holiday);

		assertNull(savedHoliday);
	}

	@Test
	void testUpdateHoliday_Success() {
		Mockito.when(holidayRepository.findById(anyInt())).thenReturn(Optional.of(holiday));
		Mockito.when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday);

		ResponseEntity<Object> response = holidayService.updateHoliday(holiday);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("updated", response.getBody());
	}

	@Test
	void testUpdateHoliday_NotFound() {
		Mockito.when(holidayRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseEntity<Object> response = holidayService.updateHoliday(holiday);

		assertTrue(response.getBody() instanceof HolidayNotFoundException);
		assertEquals("Holiday not found", ((HolidayNotFoundException) response.getBody()).getMessage());
	}

	@Test
	void testDeleteHoliday_Success() {
		Mockito.when(holidayRepository.findById(anyInt())).thenReturn(Optional.of(holiday));

		ResponseEntity<Object> response = holidayService.deleteHoliday(1);

		Mockito.verify(holidayRepository).deleteById(anyInt());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("deleted", response.getBody());
	}

	@Test
	void testDeleteHoliday_NotFound() {
		Mockito.when(holidayRepository.findById(anyInt())).thenReturn(Optional.empty());

		Exception exception = assertThrows(HolidayNotFoundException.class, () -> holidayService.deleteHoliday(1));
		assertEquals("Holiday not found", exception.getMessage());
	}

	@Test
	void testDeleteHolidayByCountry_Success() {
		Mockito.when(holidayRepository.findAllByCountry(any())).thenReturn(Collections.singletonList(holiday));

		ResponseEntity<Object> response = holidayService.deleteHolidayByCountry("USA");

		Mockito.verify(holidayRepository).deleteAllByCountry(any());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("deleted", response.getBody());
	}

	@Test
	void testDeleteHolidayByCountry_NotFound() {
		Mockito.when(holidayRepository.findAllByCountry(any())).thenReturn(Collections.emptyList());

		ResponseEntity<Object> response = holidayService.deleteHolidayByCountry("USA");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("No Holidays found with country code", response.getBody());
	}
}