package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.UserNotAdminException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.*;

import java.io.IOException;
import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class UserProfileController extends AbstractController{
    @FXML
    private Text usernameField;
    @FXML
    private Text nameField;
    @FXML
    private Text emailField;
    @FXML
    private Text roleField;
    @FXML
    private GridPane cinemaTab;
    @FXML
    private Text cinemaNameField;
    @FXML
    private Text cinemaAddressField;
    @FXML
    private Text cinemaCapacityField;

    @FXML
    public void initialize() throws UserNotAdminException {
        User user = SessionService.getLoggedInUser();
        usernameField.setText(user.getUsername());
        nameField.setText(user.getFirstname() + " " +  user.getLastname());
        emailField.setText(user.getEmail());
        roleField.setText(user.getRole());
        cinemaTab.setVisible(false);

        if (Objects.equals(user.getRole(), "Admin")) {
            Cinema cinema = CinemaService.findCinemaForAdmin(user);

            cinemaTab.setVisible(true);
            cinemaNameField.setText(cinema.getName());
            cinemaAddressField.setText(cinema.getAddress());
            cinemaCapacityField.setText(String.valueOf(cinema.getCapacity()));
        }
    }

    @FXML
    public void handleMenuHome(ActionEvent event) throws IOException {
        User user = SessionService.getLoggedInUser();
        if (Objects.equals(user.getRole(), "Admin")) {
            changeScene(event, "mainMenuAdmin.fxml");
        } else {
            System.out.println("Not yet implemented!");
        }
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

    @FXML
    public void handleChangePassword(ActionEvent event) {
        System.out.println("Not yet implemented.");
    }
}
