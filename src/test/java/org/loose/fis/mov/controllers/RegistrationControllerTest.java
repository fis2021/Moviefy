package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@ExtendWith(ApplicationExtension.class)
class RegistrationControllerTest {
    private static final String USERNAME = "test";
    private static final String FIRSTNAME = "test";
    private static final String LASTNAME = "test";
    private static final String EMAIL = "test@test.test";
    private static final String EMAIL_WRONG = "test";
    private static final String PASSWORD = "test_test";
    private static final String PASSWORD_SHORT = "test_te";
    private static final String CINEMA_NAME_FIELD = "test";
    private static final String CINEMA_ADDRESS_FIELD = "test";
    private static final String CINEMA_CAPACITY_FIELD = "10";

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        DatabaseService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.closeDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                                                                     .getClassLoader()
                                                                     .getResource(
                                                                             "register.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    @Test
    void handleRegisterActionPasswordTooShort(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write("test_te");
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "The password must be at least 8 characters long!");
        robot.clickOn("#passwordField").write("st");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "Account created successfully!");
    }

    @Test
    void handleRegisterActionEmailFormatInvalid(FxRobot robot) {
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write("test");
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "The e-mail address format is invalid!");
        robot.clickOn("#emailField").write("@");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "The e-mail address format is invalid!");
        robot.clickOn("#emailField").write("test");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "The e-mail address format is invalid!");
        robot.clickOn("#emailField").write(".");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "The e-mail address format is invalid!");
        robot.clickOn("#emailField").write("com");
        robot.clickOn("Register");
        assertEquals(robot.lookup("#registrationMessage").queryText().getText(), "Account created successfully!");
    }
}
