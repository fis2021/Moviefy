package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class UserServiceTest {
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
    void addUserClient() {
        assertDoesNotThrow(() -> UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Client", "", "", ""));
    }

    @Test
    void addUserAdmin() {
        assertDoesNotThrow(() -> UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Admin", "test", "test", "10"));
    }

    @Test
    void addUserUserDuplicate() {
        assertThrows(UserAlreadyExistsException.class, () -> {
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Client", "", "", "");
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Admin", "test", "test", "12");
        });
    }

    @Test
    void addUserEmailDuplicate() {
        assertThrows(EmailAddressAlreadyUsedException.class, () -> {
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Client", "", "", "");
            UserService.addUser("test2", "test", "test", "test_test", "test@test.test",
                    "Admin", "test", "test", "12");
        });
    }

    @Test
    void loginSuccess() {
        assertDoesNotThrow(() -> {
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Client", "", "", "");
            UserService.login("test", "test_test");
        });
    }

    @Test
    void loginFailure() {
        assertThrows(UserNotRegisteredException.class, () -> UserService.login("test", "test_test"));
        assertThrows(SessionAlreadyExistsException.class, () -> {
            UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                    "Client", "", "", "");
            UserService.login("test", "test_test");
            UserService.login("test", "test_test");
        });
    }

    @Test
    void logout() {
        assertThrows(SessionDoesNotExistException.class, () -> {
            UserService.logout();
            UserService.logout();
        });
    }
}
