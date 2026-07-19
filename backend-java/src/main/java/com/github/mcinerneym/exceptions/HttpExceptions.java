package com.github.mcinerneym.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class HttpExceptions {
    
    @ExceptionHandler(DuplicateAlbumException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAlbum(DuplicateAlbumException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAlbumNotFound(AlbumNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidNewUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNewUser(InvalidNewUserException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
        log.atError().log(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occured"));
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status)
            .body(new ErrorResponse(status.value(), errorMessage));
    }

}
