package com.procorp.chat.exception;

import com.procorp.chat.dtos.GlobalResponseDTO;
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

    @ExceptionHandler(StudentCourseIllegalStateException.class)
    public ResponseEntity<GlobalResponseDTO> StudentCourseIllegalStateException(StudentCourseIllegalStateException ex) {
        GlobalResponseDTO response= GlobalResponseDTO
                .builder()
                .msg(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<GlobalResponseDTO>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseDTO> handleGlobalException(Exception e) {
        GlobalResponseDTO response= GlobalResponseDTO
                .builder()
                .msg(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
