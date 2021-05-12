package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.CinemaService;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.SessionService;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;
import java.util.Objects;

public class UserProfileController extends AbstractController{
    @FXML
    private Hyperlink commonMenuField;
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
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Text changePasswordMessage;

    @FXML
    public void initialize() {
        User user = SessionService.getLoggedInUser();
        usernameField.setText(user.getUsername());
        nameField.setText(user.getFirstname() + " " +  user.getLastname());
        emailField.setText(user.getEmail());
        roleField.setText(user.getRole());
        cinemaTab.setVisible(false);

        if (Objects.equals(user.getRole(), "Admin")) {
            commonMenuField.setText("Add Screening");

            Cinema cinema = CinemaService.findCinemaForAdmin(user);

            cinemaTab.setVisible(true);
            cinemaNameField.setText(cinema.getName());
            cinemaAddressField.setText(cinema.getAddress());
            cinemaCapacityField.setText(String.valueOf(cinema.getCapacity()));
        } else {
            commonMenuField.setText("My Bookings");
        }
    }

    @FXML
    public void handleMenuHome(ActionEvent event) throws IOException {
        User user = SessionService.getLoggedInUser();
        if (Objects.equals(user.getRole(), "Admin")) {
            changeScene(event, "mainMenuAdmin.fxml");
        } else {
            changeScene(event, "MainMenuMAINClient.fxml");
        }
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) throws IOException {
        changeScene(event, "userProfile.fxml");
    }

    @FXML
    public void handleMenuCommonField(ActionEvent event)
    throws IOException {
        User user = SessionService.getLoggedInUser();
        if (Objects.equals(user.getRole(), "Admin")) {
            changeScene(event, "addScreening.fxml");
        } else {
            changeScene(event, "MainMenuBOOKINGClient.fxml");
        }
    }

    @FXML
    public void handleMenuLogout(ActionEvent event) throws Exception {
        UserService.logout();
        changeScene(event, "login.fxml");
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
        if (!areFieldsFilled()) {
            changePasswordMessage.setText("A required field is empty!");
        } else if (!CommService.isPasswordValid(newPasswordField.getText())) {
            changePasswordMessage.setText("The password must be at least 8 characters long!");
        } else {
            try {
                UserService.changePasswordAfterLogin(
                        oldPasswordField.getText(),
                        newPasswordField.getText()
                );
                changePasswordMessage.setText("Password change successful!");
            } catch (Exception e) {
                changePasswordMessage.setText(e.getMessage());
            }
        }
    }

    private boolean areFieldsFilled() {
        return !oldPasswordField.getText().isEmpty() && !newPasswordField
                .getText().isEmpty();
    }
}
