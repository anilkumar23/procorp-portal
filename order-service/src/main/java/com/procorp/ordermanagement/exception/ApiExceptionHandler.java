package com.procorp.ordermanagement.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalResponseDTO> handle(ConstraintViolationException e) {
        GlobalResponseDTO error = GlobalResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .msg(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GlobalResponseDTO> handle(ResourceNotFoundException e) {
        GlobalResponseDTO error = GlobalResponseDTO
                .builder()
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .msg(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseDTO> handleGlobalException(Exception e) {
        GlobalResponseDTO error = GlobalResponseDTO
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .build();

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }

    public static class ErrorItem {

        @JsonInclude(JsonInclude.Include.NON_NULL) private String code;

        private String message;
        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public static class ErrorResponse {

        private List<ErrorItem> errors = new ArrayList<>();

        public List<ErrorItem> getErrors() {
            return errors;
        }

        public void setErrors(List<ErrorItem> errors) {
            this.errors = errors;
        }

        public void addError(ErrorItem error) {
            this.errors.add(error);
        }

    }
}
