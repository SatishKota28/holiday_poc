package com.poc.holidays.service;

import com.poc.holidays.entity.Holiday;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface HolidayService {
    ResponseEntity<Object> getAll();
    Holiday addHoliday(Holiday holiday);
    ResponseEntity<Object> updateHoliday(Holiday holiday);
    ResponseEntity<Object> deleteHoliday(int id);
    ResponseEntity<Object> deleteHolidayByCountry(String countryCode);
    ResponseEntity<Object> uploadHoliday(MultipartFile[] multipartFile);
}
