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



    }
    @AfterEach
    void tearDown() throws Exception {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
        DatabaseService.initDatabase();
        SessionService.destroySession();

    }
@BeforeEach
void something() throws Exception {
    UserService.addUser(TEST,TEST,TEST,TEST,TEST,"Admin",TEST,TEST,"1");

    User user= UserService.findUser(TEST);
    SessionService.startSession(user);
    Date date=new GregorianCalendar(2021, Calendar.JANUARY, 1, 13, 37).getTime();
    Date date2=new GregorianCalendar(2020, Calendar.JANUARY, 1, 12, 37).getTime();
   // SessionService.setSelectedScreening(ScreeningService.addScreening(TEST,TEST,3,date));
    ScreeningService.addScreening("test2","test2",4,date2);
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
        robot.moveTo("test");
        robot.moveBy(200,0).clickOn();
        robot.clickOn("Add");
    }

    @Test
    void BookASeat(FxRobot robot) throws Exception {
        Date date=new GregorianCalendar(2021, Calendar.JANUARY, 1, 12, 37).getTime();
        ScreeningService.addScreening("testulet","testulet",56,date);
        Screening scre= ScreeningService.findAllFutureScreeningsForCinema("test").get(0);
        System.out.println(scre.getMovieTitle());
        UserService.addUser("test1","test1","test1","test1","test1","Client","test1","test1","test1");
        User user= UserService.findUser("test1");
        SessionService.startSession(user);
        robot.clickOn("#MCSlider");
        robot.doubleClickOn("Browse Movie");
        robot.clickOn("Book seats");
    }
}