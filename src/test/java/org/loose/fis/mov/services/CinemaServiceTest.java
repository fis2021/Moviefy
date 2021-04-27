package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTest {

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        DatabaseService.initDatabase();
    }

    @AfterEach
    void tearDown() throws IOException {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    }

    @Test
    void addCinema() {
        assertDoesNotThrow(() -> CinemaService.addCinema("test", "test", "test", 10));
        assertThrows(CinemaAlreadyExistsException.class, () -> CinemaService.addCinema("test", "test", "test", 10));
    }
}