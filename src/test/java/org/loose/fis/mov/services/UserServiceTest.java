package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.EmailAddressAlreadyUsedException;
import org.loose.fis.mov.exceptions.EmailFormatInvalidException;
import org.loose.fis.mov.exceptions.PasswordTooWeakException;
import org.loose.fis.mov.exceptions.UserAlreadyExistsException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @BeforeEach
    void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        DatabaseService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.closeDatabase();
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
}
