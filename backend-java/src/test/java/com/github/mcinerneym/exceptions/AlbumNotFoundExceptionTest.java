package com.github.mcinerneym.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

class AlbumNotFoundExceptionTest {

    @Test
    void constructorSetsMessage() {
        AlbumNotFoundException exception = new AlbumNotFoundException("Album with Id '1' does not exist.");

        assertEquals("Album with Id '1' does not exist.", exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }
}
