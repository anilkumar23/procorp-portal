package com.procorp.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GlobalResponseDTO> unauthorizedException(UnauthorizedException ex) {

        GlobalResponseDTO response= GlobalResponseDTO
                .builder()
                .msg(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.name())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();
                return new ResponseEntity<GlobalResponseDTO>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseDTO> unauthorizedException(Exception ex) {

        GlobalResponseDTO response= GlobalResponseDTO
                .builder()
                .msg(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<GlobalResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
