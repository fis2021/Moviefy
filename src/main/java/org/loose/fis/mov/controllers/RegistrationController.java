package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegistrationController extends AbstractController{
    private static final int MIN_PASSWORD_LENGTH = 8;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField cinemaNameField;
    @FXML
    private TextField cinemaAddressField;
    @FXML
    private TextField cinemaCapacityField;
    @FXML
    private ChoiceBox<String> role;
    @FXML
    private Text registrationMessage;

    @FXML
    public void initialize() {
        role.getItems().add("Client");
        role.getItems().add("Admin");
        role.setValue("Client");
    }


    @FXML
    public void handleRegisterAction() {
            if (!areFieldsFilled()) {
                registrationMessage.setText("A required field is empty!");
            } else if (!CommService.isEmailValid(emailField.getText())) {
                registrationMessage.setText("The e-mail address format is invalid!");
            } else if (!CommService.isPasswordValid(passwordField.getText())) {
                registrationMessage.setText(
                        "The password must be at least 8 characters long!");
            } else if (Objects.equals(
                    role.getValue(),
                    "Admin"
            ) && !isCinemaCapacityNumeric()) {
                registrationMessage.setText(
                        "The inputted capacity is not a valid capacity!");
            } else {
                try {
                    UserService.addUser(usernameField.getText(), firstnameField.getText(), lastnameField.getText(),
                                        passwordField.getText(), emailField.getText(), role.getValue(),
                                        cinemaNameField.getText(), cinemaAddressField.getText(),
                                        cinemaCapacityField.getText());
                    registrationMessage.setText("Account created successfully!");
                } catch (Exception e) {
                    registrationMessage.setText(e.getMessage());
                }
            }
    }

    @FXML
    public void handleLoginAction(ActionEvent event) throws IOException {
        changeScene(event, "login.fxml");
    }

    @FXML
    public void handleUserTypeChange() {
        cinemaNameField.setEditable(Objects.equals(role.getValue(), "Admin"));
        cinemaAddressField.setEditable(Objects.equals(role.getValue(), "Admin"));
        cinemaCapacityField.setEditable(Objects.equals(role.getValue(), "Admin"));
        if (Objects.equals(role.getValue(), "Client")) {
            cinemaNameField.clear();
            cinemaAddressField.clear();
            cinemaCapacityField.clear();
        }
    }

//    private boolean isPasswordValid() {
//        return passwordField.getText().length() >= MIN_PASSWORD_LENGTH;
//    }

//    private boolean isEmailValid() {
//        Pattern emailPattern = Pattern.compile(
//                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
//        );
//        return emailPattern.matcher(emailField.getText()).find();
//    }

    private boolean isCinemaCapacityNumeric() {
        Pattern numberPattern = Pattern.compile("^[1-9][0-9]*$");
        return numberPattern.matcher(cinemaCapacityField.getText()).find();
    }

    private boolean areFieldsFilled() {
        if (usernameField.getText().isEmpty() || firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty()
                || emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            return false;
        }
        return !Objects.equals(role.getValue(), "Admin")
                || (!cinemaNameField.getText().isEmpty() && !cinemaCapacityField
                .getText().isEmpty() && !cinemaAddressField.getText()
                .isEmpty());
    }
}
