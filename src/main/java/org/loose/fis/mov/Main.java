package org.loose.fis.mov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                                                                     .getClassLoader()
                                                                     .getResource(
                                                                             "login.fxml")));
        primaryStage.setTitle("Moviefy");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
