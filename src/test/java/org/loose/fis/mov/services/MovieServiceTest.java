package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.loose.fis.mov.services.MovieService.checkMovieDuplicate;

@Disabled
class MovieServiceTest {

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
    void checkMovieDuplicateSuccess() {
        Movie movie = new Movie("test", "test", 12);
        Movie movieDuplicate = new Movie("test", "test2", 13);
        Movie movieNonDuplicate = new Movie("test2", "test3", 14);
        DatabaseService.getMovieRepo().insert(movie);
        assertTrue(checkMovieDuplicate(movieDuplicate));
        assertFalse(checkMovieDuplicate(movieNonDuplicate));
    }
}