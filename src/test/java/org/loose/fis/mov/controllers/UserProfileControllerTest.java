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
import org.loose.fis.mov.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(ApplicationExtension.class)
class UserProfileControllerTest {

    private static final String USERNAME = "test_user";
    private static final String OLD_PASSWORD = "test_test";
    private static final String WRONG_OLD_PASSWORD = "test_wrong";
    private static final String NEW_PASSWORD = "new_password";
    private static final String WRONG_NEW_PASSWORD = "short";
    private static final String CINEMA_NAME = "test_cinema";

    @BeforeAll
    static void beforeAll()
    throws Exception {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();
        UserService.addUser(
                USERNAME,
                "test",
                "test",
                OLD_PASSWORD,
                "test@test.test",
                "Admin",
                CINEMA_NAME,
                "test_address",
                "10"
        );
        UserService.login(USERNAME, OLD_PASSWORD);
    }

    @BeforeEach
    void beforeEach()
    throws Exception {
    }

    @AfterAll
    static void afterAll()
    throws IOException {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @AfterEach
    void tearDown()
    throws Exception {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
        DatabaseService.initDatabase();
        UserService.addUser(
                USERNAME,
                "test",
                "test",
                OLD_PASSWORD,
                "test@test.test",
                "Admin",
                CINEMA_NAME,
                "test_address",
                "10"
        );
        UserService.login(USERNAME, OLD_PASSWORD);
    }

    @Start
    void start(Stage primaryStage)
    throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                                                     .getResource(
                                                             "userProfile.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test a successful password change")
    void changePasswordAfterLogin(FxRobot robot) {
        robot.clickOn("#oldPasswordField").write(OLD_PASSWORD);
        robot.clickOn("#newPasswordField").write(NEW_PASSWORD);
        robot.clickOn("Change password");
        assertEquals(
                "Password change successful!",
                robot.lookup("#changePasswordMessage").queryText().getText()
        );
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#passwordField").write(NEW_PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "Future screenings for " + CINEMA_NAME,
                robot.lookup("#pageTitle").queryText().getText()
        );
    }

    @Test
    @DisplayName("Test a unsuccessful change password attempt due to an incorrect old password")
    void changePasswordAfterLoginInvalidOldPassword(FxRobot robot) {
        robot.clickOn("#oldPasswordField").write(WRONG_OLD_PASSWORD);
        robot.clickOn("#newPasswordField").write(NEW_PASSWORD);
        robot.clickOn("Change password");
        assertEquals(
                "Incorrect password!",
                robot.lookup("#changePasswordMessage").queryText().getText()
        );
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#passwordField").write(NEW_PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "Incorrect password!",
                robot.lookup("#loginMessage").queryText().getText()
        );
    }

    @Test
    @DisplayName("Test a unsuccessful change password attempt due to an invalid new password")
    void changePasswordAfterLoginInvalidNewPassword(FxRobot robot) {
        robot.clickOn("#oldPasswordField").write(OLD_PASSWORD);
        robot.clickOn("#newPasswordField").write(WRONG_NEW_PASSWORD);
        robot.clickOn("Change password");
        assertEquals(
                "The password must be at least 8 characters long!",
                robot.lookup("#changePasswordMessage").queryText().getText()
        );
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#passwordField").write(WRONG_NEW_PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "Incorrect password!",
                robot.lookup("#loginMessage").queryText().getText()
        );
    }

    @Test
    @DisplayName("Test a unsuccessful change password attempt due to empty fields")
    void changePasswordAfterLoginEmptyFields(FxRobot robot) {
        robot.clickOn("Change password");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#changePasswordMessage").queryText().getText()
        );
        robot.clickOn("#oldPasswordField").write(OLD_PASSWORD);
        robot.clickOn("Change password");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#changePasswordMessage").queryText().getText()
        );
        robot.clickOn("Logout");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#passwordField").write(NEW_PASSWORD);
        robot.clickOn("Login");
        assertEquals(
                "Incorrect password!",
                robot.lookup("#loginMessage").queryText().getText()
        );
    }
}