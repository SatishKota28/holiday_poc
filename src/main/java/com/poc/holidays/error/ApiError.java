package com.poc.holidays.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    private HttpStatus httpStatus;
    private List<String> errors;
    private LocalDateTime timeStamp;
    private String pathUri;

    public ApiError() {
    }

    public ApiError(HttpStatus httpStatus, List<String> errors, LocalDateTime timeStamp, String pathUri) {
        this.httpStatus = httpStatus;
        this.errors = errors;
        this.timeStamp = timeStamp;
        this.pathUri = pathUri;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPathUri() {
        return pathUri;
    }

    public void setPathUri(String pathUri) {
        this.pathUri = pathUri;
    }
}
