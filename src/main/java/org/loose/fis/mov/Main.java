package org.loose.fis.mov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        primaryStage.setTitle("Moviefy");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
