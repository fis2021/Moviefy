package org.loose.fis.mov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.FileSystemService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        FileSystemService.initDirectory();
        DatabaseService.initDatabase();
    }
}
