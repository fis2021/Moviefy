package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@ExtendWith(ApplicationExtension.class)
class MainMenuPROFILEClientControllerTest {
    private static final String TEST = "test";
    @AfterAll
    static void afterAll()
            throws IOException {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
    }

    @BeforeAll
    static void beforeAll()
            throws Exception {
        FileSystemService.setApplicationFolder("moviefy_test");

        FileSystemService.initDirectory();
        DatabaseService.initDatabase();

        UserService.addUser(TEST,TEST,TEST,TEST,TEST,"Admin",TEST,TEST,"1");
        User user= UserService.findUser(TEST);
        SessionService.startSession(user);
    }
    @AfterEach
    void tearDown() throws Exception {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
        DatabaseService.initDatabase();

    }
    @Start
    void start(Stage primaryStage)
            throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(
                                "MainMenuPROFILEClient.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    @Test
    void profiletobooking(FxRobot robot) {

        robot.clickOn("Booking");
    }

    @Test
    void profiletomain(FxRobot robot) {
        robot.clickOn("Main");
    }

    @Test
    void changePassword(FxRobot robot) {
        robot.clickOn("Change Password");
        robot.clickOn("#op").write("t");
        robot.clickOn("Change Password");
        robot.clickOn("#op").write("est");
        robot.clickOn("Change Password");
        robot.clickOn("#np").write("test12345");
        robot.clickOn("Change Password");

    }
}