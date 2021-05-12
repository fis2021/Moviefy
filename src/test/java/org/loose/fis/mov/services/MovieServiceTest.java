package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

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
    throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
        DatabaseService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.closeDatabase();
    }

    @Test
    @DisplayName("Test if movies are added correctly")
    void addMovie() {
        assertEquals(0, DatabaseService.getMovieRepo().find().size());
        assertEquals(
                "test_movie",
                MovieService.addMovie("test_movie", "test_description", 10)
                        .getTitle()
        );
        assertThrows(UniqueConstraintException.class, () ->
                MovieService.addMovie("test_movie", "test_description", 10)
        );
        assertEquals(1, DatabaseService.getMovieRepo().find().size());
    }

    @Test
    @DisplayName("Test if I can get the movie object behind a screening object")
    void getMovieForScreening() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test_admin",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Admin",
                    "test_cinema",
                    "test",
                    "10"
            );
            UserService.login("test_admin", "test_test");
            ScreeningService.addScreening(
                    "test_movie",
                    "test_description",
                    30,
                    new GregorianCalendar(
                            2099,
                            Calendar.DECEMBER,
                            25,
                            13,
                            37
                    ).getTime()
            );
        });
        assertEquals(
                "test_movie",
                MovieService.getMovieForScreening(
                        DatabaseService.getScreeningRepo().find()
                                .firstOrDefault()).getTitle()
        );
        UserService.logout();
    }

    @Test
    @DisplayName("Test if I can get the movie object for a title")
    void findMovieByTitle() {
        MovieService.addMovie("test_movie", "test_description", 10);
        assertNull(MovieService.findMovieByTitle("nonexistent_movie"));
        assertEquals(
                "test_movie",
                MovieService.findMovieByTitle("test_movie").getTitle()
        );
    }

    @Test
    @DisplayName("Test the getter for the list of all movies")
    void getAllMovies() {
        assertEquals(0, MovieService.getAllMovies().size());
        MovieService.addMovie("test_movie", "test_description", 10);
        assertEquals(
                "test_movie",
                MovieService.getAllMovies().get(0).getTitle()
        );
        MovieService.addMovie("test_movie2", "test_description", 10);
        MovieService.addMovie("test_movie3", "test_description", 10);
        assertEquals(3, MovieService.getAllMovies().size());

    }
}