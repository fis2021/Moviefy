package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.model.Review;
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
//@Disabled
@ExtendWith(ApplicationExtension.class)
class PopUpReviewControllerTest {

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
        SessionService.setSelectedString(MovieService.getAllMovies().get(0));
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
        PopUpReviewController.currentMode = ReviewMode.ADD;
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(
                                "PopUpReview.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    void saveButton(FxRobot robot) {
        robot.clickOn("Save");
        robot.clickOn("#wreview").write("Text TEXT ");
        robot.clickOn("Save");
    }

    @Test
    void closeButton(FxRobot robot) {
        robot.clickOn("Close");
    }
}