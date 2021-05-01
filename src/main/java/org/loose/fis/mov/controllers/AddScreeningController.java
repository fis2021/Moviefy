package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.loose.fis.mov.exceptions.SessionDoesNotExistException;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

public class AddScreeningController extends AbstractController{
    @FXML
    public TextField movieTitleField;
    @FXML
    public TextField movieLengthField;
    @FXML
    public TextArea movieDescriptionField;
    @FXML
    public ComboBox screeningDay;
    @FXML
    public ComboBox screeningMonth;
    @FXML
    public ComboBox screeningYear;
    @FXML
    public ComboBox screeningHour;
    @FXML
    public ComboBox screeningMinute;

    @FXML
    public void handleAddScreening(ActionEvent event) {
        System.out.println("Not yet implemented");
    }

    @FXML
    public void handleMenuHome(ActionEvent event) {
        System.out.println("Not yet implemented");
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) {
        System.out.println("Not yet implemented");
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
