package com.poc.holidays.controller;

import com.poc.holidays.dto.HolidayRequest;
import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;
import com.poc.holidays.error.ApiError;
import com.poc.holidays.service.HolidayServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/poc/holidays")
public class HolidayController {

	@Autowired
	HolidayServiceImpl holidayService;

	@GetMapping("/getAll")
	public ResponseEntity<Object> getHolidays() {
		return holidayService.getAll();
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addHoliday(@Valid @RequestBody HolidayRequest holidayRequest) {
		try {
			Country country = Country.valueOf(holidayRequest.getCountry());
			Holiday holiday = new Holiday(holidayRequest.getName(), holidayRequest.getHolidayDate(),
					holidayRequest.getDay(), country);
			Holiday savedHoliday = holidayService.addHoliday(holiday);
			if (null == savedHoliday)
				return ResponseEntity.status(HttpStatus.CONFLICT).body("The record already exists");
			return ResponseEntity.status(HttpStatus.CREATED).body(savedHoliday);
		} catch (Exception e) {
			return new ResponseEntity<>(getApiError(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateHoliday(@RequestBody Holiday updatedHoliday) {
		try {
			return holidayService.updateHoliday(updatedHoliday);
		} catch (RuntimeException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found");
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteHoliday(@PathVariable int id) {
		return holidayService.deleteHoliday(id);
	}

	@DeleteMapping("/country/{code}")
	public ResponseEntity<Object> deleteHolidayByCountry(@PathVariable String code) {
		return holidayService.deleteHolidayByCountry(code);
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadHolidays(@RequestBody MultipartFile[] multipartFile) {
		if (null == multipartFile || multipartFile.length == 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Excel file");
		for (MultipartFile file : multipartFile) {
			if (null == file || file.getContentType().equals(".xslx"))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("File format is not valid. Only Excel file .xslx files can be uploaded");
		}
		return holidayService.uploadHoliday(multipartFile);
	}

	private ApiError getApiError(String errorMessage) {
		ApiError apiError = new ApiError();
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		apiError.setTimeStamp(LocalDateTime.now());
		apiError.setPathUri("uri=/poc/holidays/add");
		apiError.setErrors(List.of(errorMessage));
		return apiError;
	}

}
