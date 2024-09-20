package com.sied.clients.config.exceptions;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.exceptions.global.EntityCreationException;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.util.security.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor

public class GlobalExceptionHandler {

    private final MessageService messageService;

    /**
     * Exception control.
     *
     * @param ex Exception being thrown.
     * @return Response (Not Found).
     */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiCustomResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiCustomResponse<Void> apiCustomResponse = new ApiCustomResponse<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(apiCustomResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception control.
     *
     * @param ex Exception thrown.
     * @return Response (Bad Request).
     */

    @ExceptionHandler(EntityCreationException.class)
    public ResponseEntity<ApiCustomResponse<Void>> handleEntityCreationException(EntityCreationException ex) {
        ApiCustomResponse<Void> apiCustomResponse = new ApiCustomResponse<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(apiCustomResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception control.
     *
     * @return Response (Internal Server Error).
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCustomResponse<Void>> handleGlobalException() {
        ApiCustomResponse<Void> apiCustomResponse = new ApiCustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value(), messageService.getMessage("error.handleGlobalException"));
        return new ResponseEntity<>(apiCustomResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception control.
     *
     * @param ex Exception thrown.
     * @return Response (Unauthorized).
     */

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiCustomResponse<Void>> securityException(Exception ex) {
        ApiCustomResponse<Void> apiCustomResponse = new ApiCustomResponse<>(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(apiCustomResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Validation exceptions.
     *
     * @param ex Exception.
     * @return Response (Bad Request) and details of validation errors.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiCustomResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiCustomResponse<Map<String, String>> apiCustomResponse = new ApiCustomResponse<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), messageService.getMessage("error.handleValidationExceptions"), errors);
        apiCustomResponse.setDataCount(errors.size());

        return ResponseEntity.badRequest().body(apiCustomResponse);
    }


}