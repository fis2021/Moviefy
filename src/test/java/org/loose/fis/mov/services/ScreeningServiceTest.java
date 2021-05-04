
    

package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    void checkScreeningOverlapTest() throws CinemaAlreadyExistsException {
        Cinema cinema = CinemaService.addCinema("testCinema", "test", "test", 10);
        DatabaseService.getMovieRepo().insert(new Movie("testMovie", "test", 30));
        DatabaseService.getScreeningRepo().insert(
                new Screening(
                        null,
                        new GregorianCalendar(
                                2021, Calendar.MAY, 31, 13, 37
                        ).getTime(),
                        "testMovie",
                        "testCinema",
                        10
                        )
        );
        assertTrue(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 31, 13, 38
                ).getTime(),
                30
                )
        );
        assertTrue(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 31, 12, 0
                ).getTime(),
                120
                )
        );
        assertTrue(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 31, 13, 50
                ).getTime(),
                10
                )
        );
        assertTrue(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 31, 13, 37
                ).getTime(),
                30
                )
        );
        assertFalse(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 31, 13, 6
                ).getTime(),
                30
                )
        );
        assertFalse(ScreeningService.checkIntervalOccupied(
                cinema,
                new GregorianCalendar(
                        2021, Calendar.MAY, 30, 13, 6
                ).getTime(),
                30
                )
        );
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
