package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
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

    @Test
    @DisplayName("Test if the cinema list getter works")
    void getAllCinema() {
        assertEquals(0, CinemaService.getAllCinema().size());
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
        assertEquals(1, CinemaService.getAllCinema().size());
        assertEquals(
                "test_cinema",
                CinemaService.getAllCinema().get(0).getName()
        );
        assertDoesNotThrow(() -> UserService.addUser(
                "test_admin2",
                "test",
                "test",
                "test_test",
                "test2@test.test",
                "Admin",
                "test_cinema2",
                "test",
                "10"
        ));
        assertDoesNotThrow(() -> UserService.addUser(
                "test_admin3",
                "test",
                "test",
                "test_test",
                "test3@test.test",
                "Admin",
                "test_cinema3",
                "test",
                "10"
        ));
        assertEquals(3, CinemaService.getAllCinema().size());
    }

    @Test
    @DisplayName("Test if the getter for cinemas based on name works")
    void findCinemaByName() {
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
        assertEquals("test_cinema", CinemaService.findCinemaByName("test_cinema").getName());
        assertNull(CinemaService.findCinemaByName("nonexistent_cinema"));
    }
}
