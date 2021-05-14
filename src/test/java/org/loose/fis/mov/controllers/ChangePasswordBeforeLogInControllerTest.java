package org.loose.fis.mov.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@ExtendWith(ApplicationExtension.class)
class ChangePasswordBeforeLogInControllerTest {

    private static final String EMAIL= "test@user.test";
    @Start
    void start(Stage primaryStage)
            throws Exception {
        Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(
                                "changePasswordPopUp.fxml")));
        primaryStage.setTitle("Moviefy - Testing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
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
    void tearDown() throws IOException {
        DatabaseService.closeDatabase();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath()
                .toFile());
        DatabaseService.initDatabase();
    }

    @Test
    void switchToLogin(FxRobot robot) {
        robot.clickOn("Cancel");
    }

    @Test
    void switchToRegisterWithPassword(FxRobot robot) {
        robot.clickOn("Confirm");
        assertEquals("The e-mail field is empty!",robot.lookup("#message").queryText().getText());
        robot.clickOn("#emailTextField").write("1");
        robot.clickOn("Confirm");
        assertEquals("E-mail format is invalid!",robot.lookup("#message").queryText().getText());
        robot.clickOn("#emailTextField").press(KeyCode.BACK_SPACE);
        robot.clickOn("#emailTextField").write(EMAIL);
        robot.clickOn("Cancel");
    }
}