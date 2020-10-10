package com.fortech.mockapp.utility.Exceptions;

import com.fortech.mockapp.utility.Exceptions.DataLayerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerPrototype extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { DataLayerException.class })
    protected ResponseEntity<Object> handleConflict(
        DataLayerException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), ex.getHttpStatus(), request);
    }
}
