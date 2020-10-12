package com.fortech.mockapp.utility.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataLayerException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public DataLayerException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
