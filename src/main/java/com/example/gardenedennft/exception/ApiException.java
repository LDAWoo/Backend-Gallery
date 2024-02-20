package com.example.gardenedennft.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(
        String path,
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp
) {
}
