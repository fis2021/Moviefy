package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
}