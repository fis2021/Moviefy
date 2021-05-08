package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainMenuPROFILEClientController extends AbstractController {

    public void profiletobooking(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuBOOKINGClient.fxml");
    }
    public void profiletomain(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuMAINClient.fxml");
    }

}
