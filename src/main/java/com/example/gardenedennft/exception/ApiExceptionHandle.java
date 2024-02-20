package com.example.gardenedennft.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandle {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleException(ApiRequestException e,
                                                  HttpServletRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = ResourceDuplicateException.class)
    public ResponseEntity<Object> handleException(ResourceDuplicateException e,
                                                  HttpServletRequest request) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ApiException exception = new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                notAcceptable,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception, notAcceptable);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleException(ResourceNotFoundException e,
                                                  HttpServletRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception, notFound);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e,
                                                  HttpServletRequest request
    ) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException exception = new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                internalServerError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception, internalServerError);
    }
}
