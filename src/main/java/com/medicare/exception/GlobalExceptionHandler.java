package com.medicare.exception;

import com.medicare.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for centralized REST API error handling.
 * Catches all controller exceptions and converts them into structured ApiResponseDTO.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ApiResponseDTO.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleDuplicateAppointment(DuplicateAppointmentException ex) {
        return new ResponseEntity<>(ApiResponseDTO.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(ApiResponseDTO.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponseDTO<Map<String, String>> response = new ApiResponseDTO<>(false, "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(
                ApiResponseDTO.error("An unexpected server error occurred: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
