package com.poc.holidays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HolidaysPocApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainMethodRunsSuccessfully() {
		// This test ensures the main method runs without any exceptions.
		HolidaysPocApplication.main(new String[] {});
	}
}