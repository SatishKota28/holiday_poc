package com.poc.holidays.service;

import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;
import com.poc.holidays.error.DuplicateRecordException;
import com.poc.holidays.error.HolidayNotFoundException;
import com.poc.holidays.repository.HolidayRepository;
import com.poc.holidays.utils.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HolidayServiceImpl implements HolidayService{

    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public ResponseEntity<Object> getAll() {
        List<Holiday> holidays = holidayRepository.findAll();
        if (holidays.isEmpty())
            return new ResponseEntity<>("No record found", HttpStatus.OK);
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @Override
    public Holiday addHoliday(Holiday holiday) {
        Optional<Holiday> savedHoliday = holidayRepository.findByHolidayDateAndCountry(holiday.getHolidayDate(), holiday.getCountry());
        if (savedHoliday.isPresent())
            return null;
        else
            return holidayRepository.save(holiday);
//        try {
//            return holidayRepository.save(holiday);
//        } catch (DataIntegrityViolationException exception){
//            throw new DuplicateRecordException("This record already exists:: "+ holiday);
//        }


    }

    @Override
    public ResponseEntity<Object> updateHoliday(Holiday holiday) {
        Optional<Holiday> existingHoliday = holidayRepository.findById(holiday.getId());
        if (existingHoliday.isEmpty())
            return ResponseEntity.ok(new HolidayNotFoundException("Holiday not found"));

        Holiday extHoliday = existingHoliday.get();
        extHoliday.setName(holiday.getName());
        extHoliday.setHolidayDate(holiday.getHolidayDate());
        extHoliday.setDay(holiday.getDay());
        extHoliday.setCountry(holiday.getCountry());

        holidayRepository.save(extHoliday);
        return ResponseEntity.ok("updated");
    }

    @Override
    public ResponseEntity<Object> deleteHoliday(int id) {
        holidayRepository.findById(id).orElseThrow(()->new HolidayNotFoundException("Holiday not found"));
        holidayRepository.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    @Override
    public ResponseEntity<Object> deleteHolidayByCountry(String countryCode) {
        try {
            Country country = Country.valueOf(countryCode);
            List<Holiday> holidays = holidayRepository.findAllByCountry(country);
            if (holidays.isEmpty()){
                return new ResponseEntity<>("No Holidays found with country code", HttpStatus.OK);
            }
            holidayRepository.deleteAllByCountry(country);
            return ResponseEntity.ok("deleted");
        } catch (Exception e){
            return new ResponseEntity<>("No Holiday found with the provided country code", HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Object> uploadHoliday(MultipartFile[] multipartFile) {
        List<Holiday> holidays = new ArrayList<>();
        try {
            holidays = ExcelParser.parseExcelFile(multipartFile);
            if (!holidays.isEmpty()){
                try {
                    holidayRepository.saveAll(holidays);
                } catch (DataIntegrityViolationException exception){
                    return new ResponseEntity<>("The excel contains duplicate record or the data already exists", HttpStatus.CONFLICT);
                }
            } else{
                return new ResponseEntity<>("Values in the file are not correct, please check and upload again", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("File Saved", HttpStatus.CREATED);
    }


}
