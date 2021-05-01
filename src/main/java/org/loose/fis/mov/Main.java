package org.loose.fis.mov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;

import java.util.Calendar;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();

        /* TEMPORARY FOR THE DEVELOPMENT OF MOV-37 */
//        DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(121, Calendar.APRIL, 29, 21, 30), "Iohannis in actiune", "Cinema Timis", 13));
//        DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(121, Calendar.APRIL, 29, 19, 00), "Cum sa nu te pierzi pana in centru", "Cinema Timis", 21));
//        //DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(2021, 4, 28, 16, 35), "Dragonu scoate album", "Cinema Timis", 0));
//        DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(121, Calendar.APRIL, 30, 11, 35), "Dragonu scoate album", "Cinema Braila", 0));
//        DatabaseService.getScreeningRepo().insert(new Screening(null, new Date(121, Calendar.MAY, 31, 11, 35), "Film amusant", "Cinema Timis", 1));

        /* TEMPORARY FOR THE DEVELOPMENT OF MOV-37 */

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Moviefy");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
