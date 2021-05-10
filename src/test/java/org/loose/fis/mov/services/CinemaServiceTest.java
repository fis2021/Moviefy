package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTest {

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
        SessionService.destroySession();
    }

    @Test
    @DisplayName("Test adding cinema to database")
    void addCinema() {
        assertDoesNotThrow(() -> CinemaService
                .addCinema("test", "test", "test", 10));
        assertEquals(1, DatabaseService.getCinemaRepo().find().toList().size());
    }

    @Test
    @DisplayName("Test if adding duplicate cinemas is impossible")
    void addCinemaFail() {
        assertDoesNotThrow(() -> {
            CinemaService
                    .addCinema("test", "test", "test", 10);
            CinemaService
                    .addCinema("test2", "test2", "test2", 10);
        });
        assertThrows(
                CinemaAlreadyExistsException.class,
                () -> CinemaService.addCinema("test", "test2", "test", 10)
        );
        assertEquals(2, DatabaseService.getCinemaRepo().find().toList().size());
    }

    @Test
    @DisplayName("Test if we can find a cinema based on an Admin Username string")
    void findCinemaForAdminString() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test_admin",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Admin",
                "test_cinema",
                "test",
                "10"
        ));
        assertNull(CinemaService.findCinemaForAdmin("wrong_admin"));
        assertEquals(
                "test_cinema",
                CinemaService.findCinemaForAdmin("test_admin").getName()
        );
    }

    @Test
    @DisplayName("Test if we can find a cinema based on an Admin User object")
    void findCinemaForAdminUser()
    throws Exception {
        User admin = UserService.addUser(
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
        assertEquals(
                "test_cinema",
                CinemaService.findCinemaForAdmin(admin).getName()
        );
    }
}
