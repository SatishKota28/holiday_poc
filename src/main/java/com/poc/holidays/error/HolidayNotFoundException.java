package com.poc.holidays.error;

public class HolidayNotFoundException extends RuntimeException{

    public HolidayNotFoundException(String message){
        super(message);
    }
}
