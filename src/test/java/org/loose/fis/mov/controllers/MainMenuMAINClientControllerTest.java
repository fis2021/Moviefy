package org.loose.fis.mov.controllers;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
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

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@ExtendWith(ApplicationExtension.class)
class MainMenuMAINClientControllerTest {
    private static final String USERNAME = "test_user";
    private static final String PASSWORD = "test_test";
    private static final String CINEMA_NAME = "test_cinema";
    private static final String MOVIE_TITLE = "test_movie";
    private static final String INITIAL_REVIEW = "test_review";
    private static final String NEW = "new_";

    @AfterAll
    static void afterAll()
            throws IOException {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
    }

    @BeforeAll
    static void beforeAll() {
        FileSystemService.setApplicationFolder("moviefy_test");
        FileSystemService.initDirectory();
    }

    @AfterEach
    void tearDown() throws Exception {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
    }

    @BeforeEach
    void beforeEach() throws Exception {
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
    void maintoprofile(FxRobot robot) {
        robot.clickOn("Profile");
    }

    @Test
    void maintobooking(FxRobot robot) {
        robot.clickOn("Booking");
    }

    @Test
    void addReview(FxRobot robot) throws InterruptedException {
        Point2D point =new Point2D.Double(30,0);
        robot.moveTo("#MCSlider");
        robot.moveBy(-12,0).clickOn();
        robot.clickOn("more");
        robot.clickOn("Add");
    }

    @Test
    @DisplayName("Test a successful review edit")
    void editReview(FxRobot robot) {
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.clickOn("more").clickOn("#addButton");
        robot.write(INITIAL_REVIEW).clickOn("Save");
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.clickOn("more").clickOn("#editButton");
        robot.write(NEW).clickOn("Save");
        assertEquals((NEW + INITIAL_REVIEW), ReviewService.getClientReview(USERNAME, MOVIE_TITLE).getText());
    }

    @Test
    @DisplayName("Test a successful review deletion")
    void deleteReview(FxRobot robot) {
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.clickOn("more").clickOn("#addButton");
        robot.write(INITIAL_REVIEW).clickOn("Save");
        robot.moveTo("#MCSlider").moveBy(-15, 0).clickOn();
        robot.clickOn("more").clickOn("#deleteButton");
        assertNull(ReviewService.getClientReview(USERNAME, MOVIE_TITLE));
    }

    @Test
    void BookASeat(FxRobot robot) throws Exception {
        robot.clickOn("#MCSlider");
        robot.clickOn("Browse Movie");
        robot.clickOn("Book seats");
    }
}