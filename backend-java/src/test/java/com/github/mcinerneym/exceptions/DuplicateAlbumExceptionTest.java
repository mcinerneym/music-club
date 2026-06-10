package com.github.mcinerneym.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

class DuplicateAlbumExceptionTest {

    @Test
    void constructorSetsMessage() {
        DuplicateAlbumException exception = new DuplicateAlbumException("Album 'X' by 'Y' already exists");

        assertEquals("Album 'X' by 'Y' already exists", exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }
}
