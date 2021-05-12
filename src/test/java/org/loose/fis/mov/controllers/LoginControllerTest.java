package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;
import org.loose.fis.mov.services.SessionService;
import org.loose.fis.mov.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    private static final String USERNAME_CLIENT = "test_client";
    private static final String USERNAME_ADMIN = "test_admin";
    private static final String WRONG_USERNAME = "wrong_user";
    private static final String PASSWORD = "test_test";
    private static final String WRONG_PASSWORD = "test_wrong";
    private static final String CINEMA_NAME = "test_cinema";

    @BeforeAll
    static void beforeAll()
    throws Exception {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();
        UserService.addUser(
                USERNAME_CLIENT,
                "test",
                "test",
                PASSWORD,
                "test@test.test",
                "Client",
                "",
                "",
                ""
        );
        UserService.addUser(
                USERNAME_ADMIN,
                "test",
                "test",
                PASSWORD,
                "test2@test.test",
                "Admin",
                CINEMA_NAME,
                "test",
                "10"
        );
    }

    @AfterAll
    static void afterAll()
    throws IOException {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @AfterEach
    void tearDown() {
        UserService.logout();
    }

    @Start
    void start(Stage primaryStage)
    throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                                                     .getResource(
                                                             "login.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test a successful login")
    void login(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME_CLIENT);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        assertEquals(USERNAME_CLIENT, SessionService.getLoggedInUser().getUsername());
    }

    @Test
    @DisplayName("Test a successful admin login")
    void loginAdmin(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME_ADMIN);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        assertEquals(USERNAME_ADMIN, SessionService.getLoggedInUser().getUsername());
        assertEquals(
                "Future screenings for " + CINEMA_NAME,
                robot.lookup("#pageTitle").queryText().getText()
        );
    }

    @Test
    @DisplayName("Test a login attempt with a wrong password")
    void loginWrongPassword(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME_CLIENT);
        robot.clickOn("#passwordField").write(WRONG_PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "Incorrect password!",
                robot.lookup("#loginMessage").queryText().getText()

        );
        assertNull(SessionService.getLoggedInUser());
    }

    @Test
    @DisplayName("Test a login attempt with a nonexistent username")
    void loginWrongUsername(FxRobot robot) {
        robot.clickOn("#usernameField").write(WRONG_USERNAME);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "This user does not exist!",
                robot.lookup("#loginMessage").queryText().getText()

        );
        assertNull(SessionService.getLoggedInUser());
    }

    @Test
    @DisplayName("Test a login attempt with empty fields")
    void loginEmptyFields(FxRobot robot) {
        robot.clickOn("Login");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#loginMessage").queryText().getText()

        );
        robot.clickOn("#usernameField").write(USERNAME_CLIENT);
        robot.clickOn("Login");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#loginMessage").queryText().getText()

        );
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        assertEquals(USERNAME_CLIENT, SessionService.getLoggedInUser().getUsername());
    }

    @Test
    @DisplayName("Tests all the buttons in the menus which are not covered by other tests")
    void testMenuButtonsAdmin(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME_ADMIN);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");

        /* test buttons on home screen */
        robot.clickOn("Home");
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME_ADMIN);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        robot.clickOn("Add Screening");
        assertDoesNotThrow(() -> robot.clickOn("#screeningDayField"));
        robot.clickOn("Home");
        robot.clickOn("My Profile");
        assertEquals(
                USERNAME_ADMIN,
                robot.lookup("#usernameField").queryText().getText()
        );
        robot.clickOn("Home");

        /* test buttons on my profile screen */
        robot.clickOn("My Profile");
        robot.clickOn("My Profile");
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME_ADMIN);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        robot.clickOn("My Profile");
        robot.clickOn("Add Screening");
        assertDoesNotThrow(() -> robot.clickOn("#screeningDayField"));
        robot.clickOn("My Profile");
        robot.clickOn("Home");
        assertEquals(
                "Future screenings for " + CINEMA_NAME,
                robot.lookup("#pageTitle").queryText().getText()
        );

        /* test buttons on add screening screen */
        robot.clickOn("Add Screening");
        robot.clickOn("Add Screening");
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME_ADMIN);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Login");
        robot.clickOn("Add Screening");
        robot.clickOn("My Profile");
        assertEquals(
                USERNAME_ADMIN,
                robot.lookup("#usernameField").queryText().getText()
        );
        robot.clickOn("Add Screening");
        robot.clickOn("Home");
        assertEquals(
                "Future screenings for " + CINEMA_NAME,
                robot.lookup("#pageTitle").queryText().getText()
        );
        robot.clickOn("Logout");
    }
}