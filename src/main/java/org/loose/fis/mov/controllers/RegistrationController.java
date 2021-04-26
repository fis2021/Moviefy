package org.loose.fis.mov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    public TextField cinemaNameField;
    @FXML
    public TextField cinemaAddressField;
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
        //UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue());
        registrationMessage.setText("Account created successfully!");
    }

    @FXML
    public void handleUserTypeChange() {
        cinemaNameField.setEditable(role.getValue().equals("Admin"));
        cinemaAddressField.setEditable(role.getValue().equals("Admin"));
        if (role.getValue().equals("Client")) {
            cinemaNameField.clear();
            cinemaAddressField.clear();
        }
    }
}