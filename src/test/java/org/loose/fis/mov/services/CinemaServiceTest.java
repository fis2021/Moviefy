package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
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

    @Test
    void findCinemaForAdminStringSuccess() {
        try {
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Admin", "test", "test", "10");
            assertEquals("test", CinemaService.findCinemaForAdmin("test").getAdminUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

//    @Test
//    void findCinemaForAdminStringFail() {
//        try {
//            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
//                    "Client", "", "", "");
//            assertThrows(UserNotAdminException.class, () -> CinemaService.findCinemaForAdmin("test"));
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }

    @Test
    void findCinemaForAdminUserSuccess() {
        try {
            User user = UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Admin", "test", "test", "10");
            assertEquals("test", CinemaService.findCinemaForAdmin(user).getAdminUsername());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

//    @Test
//    void findCinemaForAdminUserFail() {
//        try {
//            User user = UserService.addUser("test", "test", "test", "test_test", "test@test.test",
//                    "Client", "", "", "");
//            assertThrows(UserNotAdminException.class, () -> CinemaService.findCinemaForAdmin(user));
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }
}
