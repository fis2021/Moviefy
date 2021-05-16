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
//@Disabled
@ExtendWith(ApplicationExtension.class)
class RegistrationControllerTest {

    private static final String USERNAME = "test_user";
    private static final String FIRSTNAME = "test_firstname";
    private static final String LASTNAME = "test_lastname";
    private static final String EMAIL = "test@test.test";
    private static final String EMAIL_WRONG = "test";
    private static final String PASSWORD = "test_test";
    private static final String PASSWORD_SHORT = "test_te";
    private static final String CINEMA_NAME_FIELD = "test_cinema";
    private static final String CINEMA_ADDRESS_FIELD = "test_address";
    private static final String CINEMA_CAPACITY_FIELD = "10";
    private static final String CINEMA_CAPACITY_FIELD_WRONG = "abc";

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
    }

    @Start
    void start(Stage primaryStage)
    throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                                                                     .getClassLoader()
                                                                     .getResource(
                                                                             "login.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test going through buttons")
    void goThroughButtons(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("Login");
        robot.clickOn("Change Password");
        robot.clickOn("Cancel");
    }

    @Test
    @DisplayName("Test a successful registration for a client user")
    void registerClient(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        assertEquals(
                "Account created successfully!",
                robot.lookup("#registrationMessage").queryText().getText()

        );
        assertEquals(1, UserService.getAllUsers().size());
        assertEquals(USERNAME, UserService.getAllUsers().get(0).getUsername());
    }

    @Test
    @DisplayName("Test a successful registration for an admin user")
    void registerAdmin(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Admin");
        robot.clickOn("#cinemaNameField").write(CINEMA_NAME_FIELD);
        robot.clickOn("#cinemaAddressField").write(CINEMA_ADDRESS_FIELD);
        robot.clickOn("#cinemaCapacityField").write(CINEMA_CAPACITY_FIELD);
        robot.clickOn("Register");
        assertEquals(
                "Account created successfully!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
        assertEquals(USERNAME, UserService.getAllUsers().get(0).getUsername());
    }

    @Test
    @DisplayName("Test an attempt to register a client user with empty fields")
    void registerClientEmptyFields(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(0, UserService.getAllUsers().size());
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("Register");
        assertEquals(
                "Account created successfully!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt to add an admin user with empty fields")
    void registerAdminEmptyFields(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Admin");
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#cinemaNameField").write(CINEMA_NAME_FIELD);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        robot.clickOn("#cinemaAddressField").write(CINEMA_ADDRESS_FIELD);
        robot.clickOn("Register");
        assertEquals(
                "A required field is empty!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(0, UserService.getAllUsers().size());
        robot.clickOn("#cinemaCapacityField").write(CINEMA_CAPACITY_FIELD);
        robot.clickOn("Register");
        assertEquals(
                "Account created successfully!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt at trying to register with a short password")
    void registerShortPassword(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD_SHORT);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        assertEquals(
                "The password must be at least 8 characters long!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(0, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt at trying to register with an invalid email")
    void registerInvalidEmail(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL_WRONG);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        assertEquals(
                "The e-mail address format is invalid!",
                robot.lookup("#registrationMessage").queryText().getText()

        );
        assertEquals(0, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt to create a new account with an existing username")
    void registerExistingUsername(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        robot.clickOn("Register");
        assertEquals(
                String.format("An account with the username %s already exists!", USERNAME),
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt to create a new account with an existing email")
    void registerExistingEmail(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Client");
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write("2");
        robot.clickOn("Register");
        assertEquals(
                String.format("The e-mail address %s is already used!", EMAIL),
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test an attempt to create a new admin account with an existing cinema name")
    void registerExistingCinema(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Admin");
        robot.clickOn("#cinemaNameField").write(CINEMA_NAME_FIELD);
        robot.clickOn("#cinemaAddressField").write(CINEMA_ADDRESS_FIELD);
        robot.clickOn("#cinemaCapacityField").write(CINEMA_CAPACITY_FIELD);
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write("2");
        robot.clickOn("#emailField").write("m");
        robot.clickOn("Register");
        assertEquals(
                String.format("A cinema with the name %s already exists!", CINEMA_NAME_FIELD),
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(1, UserService.getAllUsers().size());
    }

    @Test
    @DisplayName("Test if only accepted cinema capacities are integers")
    void registerCapacityNotInteger(FxRobot robot) {
        robot.clickOn("Register");
        robot.clickOn("#usernameField").write(USERNAME);
        robot.clickOn("#firstnameField").write(FIRSTNAME);
        robot.clickOn("#lastnameField").write(LASTNAME);
        robot.clickOn("#emailField").write(EMAIL);
        robot.clickOn("#passwordField").write(PASSWORD);
        robot.clickOn("#role").clickOn("Admin");
        robot.clickOn("#cinemaNameField").write(CINEMA_NAME_FIELD);
        robot.clickOn("#cinemaAddressField").write(CINEMA_ADDRESS_FIELD);
        robot.clickOn("#cinemaCapacityField").write(CINEMA_CAPACITY_FIELD_WRONG);
        robot.clickOn("Register");
        assertEquals(
                "The inputted capacity is not a valid capacity!",
                robot.lookup("#registrationMessage").queryText().getText()
        );
        assertEquals(0, UserService.getAllUsers().size());
    }
}
