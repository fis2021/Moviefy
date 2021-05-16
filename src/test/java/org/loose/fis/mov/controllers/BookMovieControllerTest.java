package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Screening;
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
class BookMovieControllerTest {
    private static final String TEST = "test";
    private static final int SEATS = 1;

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

        Date date=new GregorianCalendar(2021, Calendar.JANUARY, 1, 13, 37).getTime();
        SessionService.setSelectedScreening(ScreeningService.addScreening(TEST,TEST,3,date));
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
                                "BookMovie.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    void onBookAction(FxRobot robot) {
        robot.clickOn("#buton");
        robot.clickOn("#locuri").write("-1");
        robot.clickOn("#buton");
        robot.clickOn("#locuri").press(KeyCode.BACK_SPACE);
        robot.clickOn("#locuri").press(KeyCode.BACK_SPACE);
        robot.clickOn("#locuri").write("1");
        robot.clickOn("#buton");
    }

    @Test
    @DisplayName("Testing booking button")
    void bookingToBooking(FxRobot robot) {
        robot.clickOn("Booking");
    }

    @Test
    void bookingToProfile(FxRobot robot) {
        robot.clickOn("Profile");
    }

    @Test
    void bookingToMain(FxRobot robot) {
        robot.clickOn("Main");
    }
}