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
        assertDoesNotThrow(() -> UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Client", "", "", ""));
        assertThrows(UserAlreadyExistsException.class, () -> UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Admin", "test", "test", "12"));
    }

    @Test
    void addUserEmailDuplicate() {
        assertDoesNotThrow(() -> UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Client", "", "", ""));
        assertThrows(EmailAddressAlreadyUsedException.class, () -> UserService.addUser("test2", "test", "test", "test_test", "test@test.test",
                "Admin", "test", "test", "12"));
    }

    @Test
    void loginSuccess() throws Exception {
        UserService.addUser("test", "test", "test", "test_test", "test@test.test",
                "Client", "", "", "");
        assertDoesNotThrow(() -> UserService.login("test", "test_test"));
    }

    @Test
    void loginFailure() throws Exception {
        assertThrows(UserNotRegisteredException.class, () -> UserService.login("test", "test_test"));
    }
}
