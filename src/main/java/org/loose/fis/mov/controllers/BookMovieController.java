package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;

public class BookMovieController  extends AbstractMenusController{

    public void bookingToBooking(ActionEvent e) throws IOException {

            changeScene(e, "MainMenuBOOKINGClient.fxml");
        }
    public void bookingToProfile(ActionEvent e) throws IOException {

        changeScene(e, "MainMenuPROFILEClient.fxml");
    }
    public void bookingToMain(ActionEvent e) throws IOException {

        changeScene(e, "MainMenuMAINClient.fxml");
    }


}
