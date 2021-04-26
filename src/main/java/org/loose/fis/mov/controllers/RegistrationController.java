package org.loose.fis.mov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.CinemaAlreadyExistsException;
import org.loose.fis.mov.exceptions.EmailAddressAlreadyUsedException;
import org.loose.fis.mov.exceptions.PasswordTooWeakException;
import org.loose.fis.mov.exceptions.UserAlreadyExistsException;
import org.loose.fis.mov.services.UserService;

import java.util.Objects;

public class RegistrationController {
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
        try {
            if (Objects.equals(role.getValue(), "Client")) {
                UserService.addUser(usernameField.getText(), firstnameField.getText(), lastnameField.getText(),
                        passwordField.getText(), emailField.getText(), role.getValue());
            } else {
                UserService.addUser(usernameField.getText(), firstnameField.getText(), lastnameField.getText(),
                        passwordField.getText(), emailField.getText(), role.getValue(),
                        cinemaNameField.getText(), cinemaAddressField.getText(),
                        Integer.parseInt(cinemaCapacityField.getText()));
            }
            registrationMessage.setText("Account created successfully!");
        } catch (Exception e) {
            registrationMessage.setText(e.getMessage());
        }
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
}