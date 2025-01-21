package com.poc.holidays.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;

import jakarta.transaction.Transactional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    @Transactional
    List<Holiday> findAllByCountry(Country country);
    @Transactional
    void deleteAllByCountry(Country country);
    Optional<Holiday> findByHolidayDateAndCountry(String date, Country country);
}
