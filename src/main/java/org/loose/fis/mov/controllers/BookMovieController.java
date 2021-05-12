package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.services.BookingService;
import org.loose.fis.mov.services.DatabaseService;
import org.loose.fis.mov.services.ScreeningService;
import org.loose.fis.mov.services.SessionService;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookMovieController  extends AbstractMenusController{

    @FXML
    private Label tfilm;
    @FXML
    private Label tcinema;
    @FXML
    private Label rasp;
    @FXML
    private TextField locuri;
    @FXML
    private Button buton;
    public void initialize() {
        buton.setDisable(false);
        tfilm.setText(SessionService.getSelectedScreening().getMovieTitle());
        tcinema.setText(SessionService.getSelectedScreening().getCinemaName());
        if(SessionService.getSelectedScreening().getRemainingCapacity()==0){
            locuri.setPromptText("We are sorry,All seats have been booked!");
        }
        else{
        locuri.setPromptText(" There are"+SessionService.getSelectedScreening().getRemainingCapacity()+" seats.");
        }

    }
    public void onBookAction(){
try{
        if(locuri.getText().isEmpty()){
            rasp.setText("You need to book seats first!");

        }
        if((Integer.parseInt(locuri.getText())>SessionService.getSelectedScreening().getRemainingCapacity())&&(Integer.parseInt(locuri.getText())>=1)){
        rasp.setText("The number of booked seats is to large!");
        }
        else{
            if((Integer.parseInt(locuri.getText())<1)){
                rasp.setText("You need to book at least 1 seat!");
            }else{
    Booking booking=new Booking(null,SessionService.getLoggedInUser().getUsername(),SessionService.getSelectedScreening().getId(),Integer.parseInt(locuri.getText()));
            SessionService.getSelectedScreening().setRemainingCapacity(SessionService.getSelectedScreening().getRemainingCapacity()-Integer.parseInt(locuri.getText()));
            rasp.setText("Enjoy!");
            BookingService.addBooking(booking);
            buton.setDisable(true);
            }
        }
    }
catch(Exception e){

}
    }
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
