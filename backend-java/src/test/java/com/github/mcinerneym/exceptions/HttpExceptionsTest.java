package com.github.mcinerneym.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class HttpExceptionsTest {

    private final HttpExceptions httpExceptions = new HttpExceptions();

    @Test
    void handleDuplicateAlbumReturnsConflict() {
        DuplicateAlbumException exception = new DuplicateAlbumException("Album 'X' by 'Y' already exists");

        ResponseEntity<ErrorResponse> response = httpExceptions.handleDuplicateAlbum(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().status());
        assertEquals("Album 'X' by 'Y' already exists", response.getBody().message());
    }

    @Test
    void handleAlbumNotFoundReturnsNotFound() {
        AlbumNotFoundException exception = new AlbumNotFoundException("Album with Id '1' does not exist.");

        ResponseEntity<ErrorResponse> response = httpExceptions.handleAlbumNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().status());
        assertEquals("Album with Id '1' does not exist.", response.getBody().message());
    }

    @Test
    void handleGenericErrorReturnsInternalServerError() {
        RuntimeException exception = new RuntimeException("Something went wrong");

        ResponseEntity<ErrorResponse> response = httpExceptions.handleGenericError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().status());
        assertEquals("An error has occured", response.getBody().message());
    }
}
