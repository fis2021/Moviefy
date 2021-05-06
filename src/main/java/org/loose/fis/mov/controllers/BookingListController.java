package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

public class BookingListController extends AbstractController {

    @FXML
    private Text pageTitle;
    @FXML
    private ListView<Booking> list;

    @FXML
    public void initialize() {
        pageTitle.setText("Bookings for movie");
    }

    @FXML
    public void handleMenuHome(ActionEvent event) throws IOException {
        changeScene(event, "mainMenuAdmin.fxml");
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) throws IOException {
        changeScene(event, "userProfile.fxml");
    }

    @FXML
    public void handleMenuAddScreening(ActionEvent event) throws IOException {
        changeScene(event, "addScreening.fxml");
    }

    @FXML
    public void handleMenuLogout(ActionEvent event) throws Exception {
        UserService.logout();
        changeScene(event, "login.fxml");
    }
}
