package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.loose.fis.mov.exceptions.*;
import org.loose.fis.mov.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class UserServiceTest {
    @BeforeEach
    void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        DatabaseService.initDatabase();
    }

    @AfterEach
    void tearDown() throws IOException {
        SessionService.destroySession();
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
    void changePasswordTest1() {
        assertDoesNotThrow(() -> {
           User user = UserService.addUser(
                   "test", "test", "test", "test_test",
                   "test@test.test", "Client", "", "", ""
           );
           UserService.changePassword(user, "new_test_test");
           UserService.login("test", "new_test_test");
        });
    }

    @Test
    void changePasswordTest2() {
        assertDoesNotThrow(() -> {
            User user = UserService.addUser(
                    "test", "test", "test", "test_test",
                    "test@test.test", "Client", "", "", ""
            );
            UserService.login(user.getUsername(), "test_test");
            UserService.changePassword("test_test", "new_test_test");
            UserService.logout();
            UserService.login("test", "new_test_test");
        });
    }

}
