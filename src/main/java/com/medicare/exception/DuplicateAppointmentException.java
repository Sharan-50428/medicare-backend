package com.medicare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception thrown when an appointment slot is already booked.
 * Maps to HTTP Status 409 CONFLICT.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAppointmentException extends RuntimeException {

    public DuplicateAppointmentException(String message) {
        super(message);
    }
}
