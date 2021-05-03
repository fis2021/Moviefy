package org.loose.fis.mov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;
import org.loose.fis.mov.services.MovieService;
import org.loose.fis.mov.services.UserService;

import javax.xml.crypto.Data;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();

        /* FOR DEVELOPMENT OF MOV-9 */
        DatabaseService.getMovieRepo().insert(
                new Movie(
                        "Salut aici Moviefy" + Math.random(), "ceva test", 120
                )
        );

        /* FOR DEVELOPMENT OF MOV-9 */


        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Moviefy");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
