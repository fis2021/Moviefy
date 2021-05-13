package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
@Disabled
@ExtendWith(ApplicationExtension.class)
class MainMenuMAINClientControllerTest {

    private static final String USERNAME = "test_user";
    private static final String PASSWORD = "test_test";
    private static final String CINEMA_NAME = "test_cinema";
    private static final String MOVIE_TITLE = "test_movie";
    private static final String INITIAL_REVIEW = "test_review";
    private static final String NEW = "new_";

    @BeforeAll
    static void beforeAll() {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
    }

    @AfterAll
    static void afterAll()
    throws IOException {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @BeforeEach
    void beforeEach()
    throws Exception {
        DatabaseService.initDatabase();
        UserService.addUser(
                USERNAME,
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
                "test_admin",
                "test",
                "test",
                PASSWORD,
                "test2@test.test",
                "Admin",
                "test_cinema",
                "test",
                "10"
        );
        UserService.login("test_admin", PASSWORD);
        ScreeningService.addScreening(
                MOVIE_TITLE,
                "test_description",
                Integer.parseInt("10"),
                new GregorianCalendar(
                        2099,
                        Calendar.JANUARY,
                        1,
                        0,
                        1
                ).getTime()
        );
        UserService.logout();
        UserService.login(USERNAME, PASSWORD);
    }

    @AfterEach
    void afterEach()
    throws IOException {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @Start
    void start(Stage primaryStage)
    throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                                                     .getResource(
                                                             "MainMenuMAINClient.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test a successful review edit")
    void editReview(FxRobot robot) {
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.moveTo(MOVIE_TITLE).moveBy(175, 0).clickOn().clickOn("#addButton");
        robot.write(INITIAL_REVIEW).clickOn("Save");
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.moveTo(MOVIE_TITLE).moveBy(175, 0).clickOn().clickOn("#editButton");
        robot.write(NEW).clickOn("Save");
        assertEquals((NEW + INITIAL_REVIEW), ReviewService.getClientReview(USERNAME, MOVIE_TITLE).getText());
    }

    @Test
    @DisplayName("Test a successful review deletion")
    void deleteReview(FxRobot robot) {
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.moveTo(MOVIE_TITLE).moveBy(175, 0).clickOn().clickOn("#addButton");
        robot.write(INITIAL_REVIEW).clickOn("Save");
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.moveTo(MOVIE_TITLE).moveBy(175, 0).clickOn().clickOn("#deleteButton");
        assertNull(ReviewService.getClientReview(USERNAME, MOVIE_TITLE));
    }
}