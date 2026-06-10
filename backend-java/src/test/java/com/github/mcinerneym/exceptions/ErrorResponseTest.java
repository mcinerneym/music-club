package com.github.mcinerneym.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void recordExposesStatusAndMessage() {
        ErrorResponse errorResponse = new ErrorResponse(404, "Album with Id '1' does not exist.");

        assertEquals(404, errorResponse.status());
        assertEquals("Album with Id '1' does not exist.", errorResponse.message());
    }
}
