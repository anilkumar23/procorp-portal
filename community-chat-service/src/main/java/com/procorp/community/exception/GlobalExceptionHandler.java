package com.procorp.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GlobalResponseDTO> unauthorizedException(UnauthorizedException ex) {
        GlobalResponseDTO response = GlobalResponseDTO
                .builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .msg(ex.getMessage())
                .build();
       /* ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED));
        response.setErrorMessage(ex.getMessage());*/

        return new ResponseEntity<GlobalResponseDTO>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ChatIllegalStateException.class)
    public ResponseEntity<GlobalResponseDTO> chatIllegalStateException(ChatIllegalStateException ex) {
        GlobalResponseDTO response = GlobalResponseDTO
                .builder()
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .msg(ex.getMessage())
                .build();
        return new ResponseEntity<GlobalResponseDTO>(response, HttpStatus.UNAUTHORIZED);
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
}
