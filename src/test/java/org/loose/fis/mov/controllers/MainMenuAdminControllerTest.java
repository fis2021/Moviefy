package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(ApplicationExtension.class)
class MainMenuAdminControllerTest {

    private static final String USERNAME = "test_user";
    private static final String PASSWORD = "test_test";
    private static final String CINEMA_NAME = "test_cinema";
    private static final String MOVIE_TITLE = "test_movie";
    private static final String MOVIE_DESCRIPTION = "test_description";
    private static final String MOVIE_LENGTH = "120";
    private static final String SCREENING_YEAR = "2022";
    private static final String SCREENING_MONTH = "2";
    private static final String SCREENING_DAY = "1";
    private static final String SCREENING_HOUR = "4";
    private static final String SCREENING_MINUTE = "0";

    private static Screening selectedScreening;

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
                PASSWORD,
                "test@test.test",
                "Admin",
                CINEMA_NAME,
                "test_address",
                "10"
        );
        UserService.login(USERNAME, PASSWORD);
        selectedScreening = ScreeningService.addScreening(
                MOVIE_TITLE,
                MOVIE_DESCRIPTION,
                Integer.parseInt(MOVIE_LENGTH),
                new GregorianCalendar(
                        Integer.parseInt(SCREENING_YEAR) + 1,
                        Integer.parseInt(SCREENING_MONTH) - 1,
                        Integer.parseInt(SCREENING_DAY),
                        Integer.parseInt(SCREENING_HOUR),
                        Integer.parseInt(SCREENING_MINUTE)
                ).getTime()
        );
    }

    @AfterAll
    static void afterAll()
    throws IOException {
        UserService.logout();
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                                         .toFile());
    }

    @BeforeEach
    void beforeEach()
    throws Exception {
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
                PASSWORD,
                "test@test.test",
                "Admin",
                CINEMA_NAME,
                "test_address",
                "10"
        );
        UserService.login(USERNAME, PASSWORD);
        ScreeningService.addScreening(
                MOVIE_TITLE,
                MOVIE_DESCRIPTION,
                Integer.parseInt(MOVIE_LENGTH),
                new GregorianCalendar(
                        Integer.parseInt(SCREENING_YEAR) + 1,
                        Integer.parseInt(SCREENING_MONTH) - 1,
                        Integer.parseInt(SCREENING_DAY),
                        Integer.parseInt(SCREENING_HOUR),
                        Integer.parseInt(SCREENING_MINUTE)
                ).getTime()
        );
    }

    @Start
    void start(Stage primaryStage)
    throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                                                     .getResource(
                                                             "mainMenuAdmin.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test if bookings are deleted")
    void deleteScreening(FxRobot robot) {
        robot.clickOn("Delete this screening");
        assertEquals(0, ScreeningService.findAllFutureScreeningsForCinema(
                CinemaService.findCinemaForAdmin(
                        SessionService.getLoggedInUser()
                )).size()
        );
        assertNotNull(robot.lookup("Cancelled"));
    }

    @Test
    @DisplayName("Test if the list of bookings is shown for a movie")
    void seeBookings(FxRobot robot) {
        robot.clickOn("Bookings");
        assertEquals(
                "Bookings for movie "
                        + selectedScreening.getMovieTitle()
                        + " on "
                        + CommService.extractDate(selectedScreening.getDate())
                        + " "
                        + CommService.extractTime(selectedScreening.getDate()),
                robot.lookup("#pageTitle").queryText().getText()
        );
    }
}