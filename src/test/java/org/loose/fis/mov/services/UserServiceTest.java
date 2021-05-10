package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.mov.exceptions.EmailAddressAlreadyUsedException;
import org.loose.fis.mov.exceptions.PasswordIncorrectException;
import org.loose.fis.mov.exceptions.UserAlreadyExistsException;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

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
    @DisplayName("Check if client users are added correctly")
    void addUserClient() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test_client",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertEquals(1, UserService.getAllUsers().size());
        assertEquals("Client", UserService.getAllUsers().get(0).getRole());
        assertEquals(
                "test_client",
                UserService.getAllUsers().get(0).getUsername()
        );
    }

    @Test
    @DisplayName("Check if admin users are added correctly")
    void addUserAdmin() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test_admin",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Admin",
                "test",
                "test",
                "10"
        ));
        assertEquals(1, UserService.getAllUsers().size());
        assertEquals("Admin", UserService.getAllUsers().get(0).getRole());
        assertEquals(
                "test_admin",
                UserService.getAllUsers().get(0).getUsername()
        );
    }

    @Test
    @DisplayName("Check if users with already existing usernames are rejected")
    void addUserDuplicateUsername() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertThrows(UserAlreadyExistsException.class, () ->
                UserService.addUser(
                        "test",
                        "test",
                        "test",
                        "test_test",
                        "test2@test.test",
                        "Admin",
                        "test",
                        "test",
                        "10"
                )
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Check if users with an existing email are rejected")
    void addUserEmailDuplicate() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertThrows(EmailAddressAlreadyUsedException.class, () ->
                UserService.addUser(
                        "test2",
                        "test",
                        "test",
                        "test_test",
                        "test@test.test",
                        "Admin",
                        "test",
                        "test",
                        "10"
                )
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test a successful login")
    void login() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            UserService.login("test", "test_test");
        });
        assertEquals("test", SessionService.getLoggedInUser().getUsername());
    }

    @Test
    @DisplayName("Test a login failing due to using the wrong password")
    void loginIncorrectPassword() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertThrows(
                PasswordIncorrectException.class,
                () -> UserService.login("test", "wrong")
        );
    }

    @Test
    @DisplayName("Test a login failing due to using a non-existent username")
    void loginIncorrectUsername() {
        assertThrows(
                UserNotRegisteredException.class,
                () -> UserService.login("test", "test")
        );
    }

    @Test
    @DisplayName("Test the getter for the list of users")
    void getAllUsers() {
        assertEquals(0, UserService.getAllUsers().size());
        assertDoesNotThrow(() -> UserService.addUser(
                "test",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertEquals(1, UserService.getAllUsers().size());
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test2",
                    "test",
                    "test",
                    "test_test",
                    "test2@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            UserService.addUser(
                    "test3",
                    "test",
                    "test",
                    "test_test",
                    "test3@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
        });
        assertEquals(3, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test logout")
    void logout() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            assertDoesNotThrow(() -> UserService.login("test", "test_test"));
            UserService.logout();
        });
        assertNull(SessionService.getLoggedInUser());
    }

    @Test
    @DisplayName("Check if the password can be changed before login")
    void changePasswordBeforeLogin() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            UserService.login("test", "test_test");
            UserService.changePasswordBeforeLogin(
                    "test@test.test",
                    "new_test_password"
            );
            UserService.logout();
            UserService.login("test", "new_test_password");
        });
    }

    @Test
    @DisplayName("Check if the password won't be changed before login on nonexistent e-mail given")
    void changePasswordBeforeLoginInvalidEmail() {
        assertDoesNotThrow(() -> UserService.addUser(
                "test",
                "test",
                "test",
                "test_test",
                "test@test.test",
                "Client",
                "",
                "",
                ""
        ));
        assertThrows(UserNotRegisteredException.class, () ->
                UserService.changePasswordBeforeLogin(
                        "wrong_email@test.test",
                        "new_test_password"
                ));
    }

    @Test
    @DisplayName("Check if the password can be changed after logging in")
    void changePasswordAfterLogin() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            UserService.login("test", "test_test");
            UserService.changePasswordAfterLogin(
                    "test_test",
                    "new_test_password"
            );
            UserService.logout();
            UserService.login("test", "new_test_password");
        });
    }

    @Test
    @DisplayName("Check if the password won't be changed after login if the wrong confirmation old password is provided")
    void changePasswordAfterLoginWrongOldPassword() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
            UserService.login("test", "test_test");
        });
        assertThrows(PasswordIncorrectException.class, () ->
                UserService.changePasswordAfterLogin(
                        "wrong_old_password",
                        "new_test_password"
                ));
        UserService.logout();
        assertThrows(PasswordIncorrectException.class, () ->
                UserService.login("test", "new_test_password")
        );
    }

    @Test
    @DisplayName("Test if an existing user can be found by username")
    void findUser() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
        });
        assertNull(UserService.findUser("nonexistent"));
        assertEquals(
                "test",
                UserService.findUser("test").getUsername()
        );
    }

    @Test
    @DisplayName("Test if an existing user can be found by email")
    void findUserByEmail() {
        assertDoesNotThrow(() -> {
            UserService.addUser(
                    "test",
                    "test",
                    "test",
                    "test_test",
                    "test@test.test",
                    "Client",
                    "",
                    "",
                    ""
            );
        });
        assertNull(UserService.findUserByEmail("nonexistent@test.test"));
        assertEquals(
                "test@test.test",
                UserService.findUserByEmail("test@test.test").getEmail()
        );
    }
}
