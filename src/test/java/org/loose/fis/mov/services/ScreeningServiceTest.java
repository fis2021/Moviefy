package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Screening;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class ScreeningServiceTest {

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
    void findAllFutureScreeningsForCinemaString() {
        try {
            CinemaService.addCinema("test", "test", "test", 12);
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() - 10000),
                    "test", "test", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 20000),
                    "test2", "test2", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 10000),
                    "test3", "test", 11));
            assertEquals(1, ScreeningService.findAllFutureScreeningsForCinema("test").size());
            assertEquals("test3", ScreeningService.findAllFutureScreeningsForCinema("test").get(0).getMovieTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllFutureScreeningsForCinemaCinema() {
        try {
            Cinema cinema = CinemaService.addCinema("test", "test", "test", 12);
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() - 10000),
                    "test", "test", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 20000),
                    "test2", "test2", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 10000),
                    "test3", "test", 11));
            assertEquals(1, ScreeningService.findAllFutureScreeningsForCinema(cinema).size());
            assertEquals("test3", ScreeningService.findAllFutureScreeningsForCinema(cinema).get(0).getMovieTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllScreeningsForCinemaString() {
        try {
            CinemaService.addCinema("test", "test", "test", 12);
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() - 10000),
                    "test", "test", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 20000),
                    "test2", "test2", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 10000),
                    "test3", "test", 11));
            assertEquals(2, ScreeningService.findAllScreeningsForCinema("test").size());
            assertEquals("test", ScreeningService.findAllScreeningsForCinema("test").get(0).getMovieTitle());
            assertEquals("test3", ScreeningService.findAllScreeningsForCinema("test").get(1).getMovieTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void FindAllScreeningsForCinemaCinema() {
        try {
            Cinema cinema = CinemaService.addCinema("test", "test", "test", 12);
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() - 10000),
                    "test", "test", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 20000),
                    "test2", "test2", 11));
            DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(System.currentTimeMillis() + 10000),
                    "test3", "test", 11));
            assertEquals(2, ScreeningService.findAllScreeningsForCinema(cinema).size());
            assertEquals("test", ScreeningService.findAllScreeningsForCinema(cinema).get(0).getMovieTitle());
            assertEquals("test3", ScreeningService.findAllScreeningsForCinema(cinema).get(1).getMovieTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
