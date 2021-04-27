package org.loose.fis.mov.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public  class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToRegister(ActionEvent event) throws IOException
    {
        root=FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException
    {
        root=FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
