package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.TimeIntervalOccupiedException;
import org.loose.fis.mov.model.Screening;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
class ScreeningServiceTest {

    @BeforeAll
    static void beforeAll() {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
    }

    @AfterAll
    static void afterAll()
    throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @BeforeEach
    void setUp()
    throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
        DatabaseService.initDatabase();
        UserService
                .addUser("test", "test", "test", "test_test", "test@test.test",
                         "Admin", "test", "test", "10"
                );
        MovieService.addMovie("existing_movie", "test", 10);
        UserService.login("test", "test_test");
    }

    @AfterEach
    void tearDown() {
        UserService.logout();
        DatabaseService.closeDatabase();
    }

    @Test
    @DisplayName("Test if a screening for a new movie is added while also adding the movie")
    void addScreeningNewMovie() {
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "new_movie",
                "test description",
                10,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
                           )
        );
        assertEquals(
                1,
                DatabaseService.getScreeningRepo().find().toList().size()
        );
        assertEquals(2, DatabaseService.getMovieRepo().find().toList().size());
        assertEquals(
                "new_movie",
                DatabaseService.getScreeningRepo().find().firstOrDefault()
                        .getMovieTitle()
        );
        assertEquals(
                "test",
                DatabaseService.getScreeningRepo().find().firstOrDefault()
                        .getCinemaName()
        );
    }

    @Test
    @DisplayName("Test if a screening for an existing movie is added without adding a separate entry in the movie database")
    void addScreeningExistingMovie() {
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
                           )
        );
        assertEquals(
                1,
                DatabaseService.getScreeningRepo().find().toList().size()
        );
        assertEquals(1, DatabaseService.getMovieRepo().find().toList().size());
        assertEquals(
                "existing_movie",
                DatabaseService.getScreeningRepo().find().firstOrDefault()
                        .getMovieTitle()
        );
        assertEquals(
                "test",
                DatabaseService.getMovieRepo().find().firstOrDefault()
                        .getDescription()
        );
        assertEquals(
                "test",
                DatabaseService.getScreeningRepo().find().firstOrDefault()
                        .getCinemaName()
        );
    }

    @Test
    @DisplayName("Test if screenings with overlapping timeframes are refused")
    void addScreeningOverlappingTimeframe() {
        assertDoesNotThrow(() -> {
            ScreeningService.addScreening(
                    "existing_movie",
                    "empty",
                    5,
                    new GregorianCalendar(
                            2099,
                            Calendar.DECEMBER,
                            25,
                            13,
                            37
                    ).getTime()
            );
            ScreeningService.addScreening(
                    "existing_movie",
                    "empty",
                    5,
                    new GregorianCalendar(
                            2099,
                            Calendar.DECEMBER,
                            31,
                            23,
                            59
                    ).getTime()
            );
        });

        // overlapping - the new movie overlaps on the end of an existing one
        assertThrows(TimeIntervalOccupiedException.class, () ->
                ScreeningService.addScreening(
                        "existing_movie",
                        "test test test",
                        31,
                        new GregorianCalendar(
                                2099,
                                Calendar.DECEMBER,
                                25,
                                13,
                                43
                        ).getTime()
                )
        );

        // overlapping - the new movie overlaps on the beginning of an existing one
        assertThrows(TimeIntervalOccupiedException.class, () ->
                ScreeningService.addScreening(
                        "new_movie",
                        "test test test",
                        30,
                        new GregorianCalendar(
                                2099,
                                Calendar.DECEMBER,
                                25,
                                13,
                                12
                        ).getTime()
                )
        );

        // overlapping - the new movie completely overlaps on the existing one - new is bigger
        assertThrows(TimeIntervalOccupiedException.class, () ->
                ScreeningService.addScreening(
                        "new_movie",
                        "test test test",
                        3,
                        new GregorianCalendar(
                                2099,
                                Calendar.DECEMBER,
                                25,
                                13,
                                32
                        ).getTime()
                )
        );

        // overlapping - the new movie completely overlaps on the existing one - old is bigger
        assertThrows(TimeIntervalOccupiedException.class, () ->
                ScreeningService.addScreening(
                        "newer_movie",
                        "test test test",
                        2,
                        new GregorianCalendar(
                                2099,
                                Calendar.DECEMBER,
                                25,
                                13,
                                39
                        ).getTime()
                )
        );

        assertEquals(
                2,
                DatabaseService.getScreeningRepo().find().toList().size()
        );
    }

    @Test
    @DisplayName("Test if screening deletion works")
    void deleteScreening() {
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
                           )
        );
        ScreeningService.deleteScreening(
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin("test")
                ).get(0)
        );
        assertEquals(
                0,
                DatabaseService.getScreeningRepo().find().toList().size()
        );
    }

    @Test
    @DisplayName("Test the getter for all the future screenings with Cinema param")
    void findAllFutureScreeningsCinema() {
        assertEquals(
                0,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        )
                ).size()
        );
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
        ));
        assertEquals(
                1,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        )
                ).size()
        );
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        26,
                        13,
                        37
                ).getTime()
        ));
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        26,
                        19,
                        37
                ).getTime()
        ));
        assertEquals(
                3,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        )
                ).size()
        );
    }

    @Test
    @DisplayName("Test the getter for all the future screenings with String param")
    void findAllFutureScreeningsString() {
        assertEquals(
                0,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        ).getName()
                ).size()
        );
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
        ));
        assertEquals(
                1,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        ).getName()
                ).size()
        );
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        26,
                        13,
                        37
                ).getTime()
        ));
        assertDoesNotThrow(() -> ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        26,
                        19,
                        37
                ).getTime()
        ));
        assertEquals(
                3,
                ScreeningService.findAllFutureScreeningsForCinema(
                        CinemaService.findCinemaForAdmin(
                                SessionService.getLoggedInUser()
                        ).getName()
                ).size()
        );
    }

    @Test
    @DisplayName("Test the Screening getter based on screening ID")
    void findScreeningById()
    throws TimeIntervalOccupiedException {
        Screening screening = ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
        );
        assertEquals(
                screening.getId(),
                ScreeningService.findScreeningByID(screening.getId()).getId()
        );
    }

    @Test
    @DisplayName("Test the method updating the available seats in the screening database after a booking")
    void updateScreeningSeats()
    throws TimeIntervalOccupiedException {
        Screening screening = ScreeningService.addScreening(
                "existing_movie",
                "empty",
                30,
                new GregorianCalendar(
                        2099,
                        Calendar.DECEMBER,
                        25,
                        13,
                        37
                ).getTime()
        );
        assertEquals(
                screening.getId(),
                ScreeningService.findScreeningByID(screening.getId()).getId()
        );

        ScreeningService.updateScreeningSeats(screening, -3);
        assertEquals(7, screening.getRemainingCapacity());
    }
}

