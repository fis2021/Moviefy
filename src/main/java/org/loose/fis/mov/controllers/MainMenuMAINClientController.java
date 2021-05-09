package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainMenuMAINClientController extends AbstractController{


    public void maintoprofile(ActionEvent event) throws IOException {

    changeScene(event,"MainMenuPROFILEClient.fxml");
    }
    public void maintobooking(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuBOOKINGClient.fxml");
    }


}
