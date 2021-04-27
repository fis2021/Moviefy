package org.loose.fis.mov.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;


import java.io.IOException;

import static org.loose.fis.mov.services.CommService.WordGenerator;
import static org.loose.fis.mov.services.UserService.findUserByEmail;

public  class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField emialtextfield;
    private String email;

    @FXML
    public void switchToRegister(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException, UserNotRegisteredException {


        email = emialtextfield.getText();
        System.out.println(email);
        //findUserByEmail(email);
        String newPassword=WordGenerator(12);
        System.out.println(newPassword);

        root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}