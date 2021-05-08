package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainMenuBOOKINGClientController extends AbstractController{

    public void bookingtoprofile(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuPROFILEClient.fxml");

    }
    public void bookingtomain(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuMAINClient.fxml");
    }

}
