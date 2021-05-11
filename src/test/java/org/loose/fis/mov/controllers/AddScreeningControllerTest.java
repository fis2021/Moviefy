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
import org.loose.fis.mov.services.ScreeningService;
import org.loose.fis.mov.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class AddScreeningControllerTest {

    private static final String USERNAME = "test_user";
    private static final String PASSWORD = "test_test";
    private static final String CINEMA_NAME = "test_cinema";
    private static final String MOVIE_TITLE = "test_movie";
    private static final String NEW_MOVIE_TITLE = "test_movie_new";
    private static final String MOVIE_DESCRIPTION = "test_description";
    private static final String MOVIE_LENGTH = "120";
    private static final String SCREENING_YEAR = "2022";
    private static final String SCREENING_MONTH = "2";
    private static final String SCREENING_DAY = "1";
    private static final String SCREENING_HOUR = "4";
    private static final String SCREENING_MINUTE = "0";

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
        ScreeningService.addScreening(
                MOVIE_TITLE,
                MOVIE_DESCRIPTION,
                Integer.parseInt(MOVIE_LENGTH),
                new GregorianCalendar(
                        Integer.parseInt(SCREENING_YEAR) + 1,
                        Integer.parseInt(SCREENING_MONTH),
                        Integer.parseInt(SCREENING_DAY),
                        Integer.parseInt(SCREENING_HOUR),
                        Integer.parseInt(SCREENING_MINUTE)
                ).getTime()
        );
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
                        Integer.parseInt(SCREENING_MONTH),
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
                                                             "addScreening.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test adding a screening for a new movie")
    void addScreeningNewMovie(FxRobot robot) {
        robot.clickOn("#movieTitleField").write(NEW_MOVIE_TITLE);
        robot.clickOn("#movieDescriptionField").write(MOVIE_DESCRIPTION);
        robot.clickOn("#movieLengthField").write(MOVIE_LENGTH);
        robot.clickOn("#screeningDayField").clickOn(SCREENING_DAY);
        robot.clickOn("#screeningMonthField").clickOn(SCREENING_MONTH);
        robot.clickOn("#screeningYearField").clickOn(SCREENING_YEAR);
        robot.clickOn("#screeningHourField").clickOn(SCREENING_HOUR);
        robot.clickOn("#screeningMinuteField").clickOn(SCREENING_MINUTE);
        robot.clickOn("#addScreeningButton");
        assertEquals(
                "Successfully added screening!",
                robot.lookup("#message").queryText().getText()
        );
    }

    @Test
    @DisplayName("Test adding a screening for an existing movie")
    void addScreeningExistingMovie(FxRobot robot) {
        robot.clickOn("Add screening for already existing movie");
        robot.clickOn("#availableMoviesField").clickOn(MOVIE_TITLE);
        robot.clickOn("#screeningDayField").clickOn(SCREENING_DAY);
        robot.clickOn("#screeningMonthField").clickOn(SCREENING_MONTH);
        robot.clickOn("#screeningYearField").clickOn(SCREENING_YEAR);
        robot.clickOn("#screeningHourField").clickOn(SCREENING_HOUR);
        robot.clickOn("#screeningMinuteField").clickOn(SCREENING_MINUTE);
        robot.clickOn("#addScreeningButton");
        assertEquals(
                "Successfully added screening!",
                robot.lookup("#message").queryText().getText()
        );
    }
}