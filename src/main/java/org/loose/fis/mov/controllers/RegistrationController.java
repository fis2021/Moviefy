package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.loose.fis.mov.exceptions.*;
import org.loose.fis.mov.services.UserService;
import java.io.IOException;
import java.net.URL;

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
        try {
            checkFieldsForNull();
            checkEmailFormatValid();
            checkMinimumPasswordStrength();
            if (Objects.equals(role.getValue(), "Admin")) {
                checkCinemaCapacityNumeric();
            }
            UserService.addUser(usernameField.getText(), firstnameField.getText(), lastnameField.getText(),
                    passwordField.getText(), emailField.getText(), role.getValue(),
                    cinemaNameField.getText(), cinemaAddressField.getText(),
                    cinemaCapacityField.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (Exception e) {
            registrationMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleLoginAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
  
    @FXML
    public void switchToPopUp(ActionEvent event) throws IOException {

        changeScene(event,"changePasswordPopUp.fxml");
    }


    private void checkMinimumPasswordStrength() throws PasswordTooWeakException {
        if (passwordField.getText().length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordTooWeakException();
        }
    }

    private void checkEmailFormatValid() throws EmailFormatInvalidException {
        Pattern emailPattern = Pattern.compile("^[A-Za-z1-9.]+@[A-Za-z1-9]+\\.[a-z]+$");
        if (!emailPattern.matcher(emailField.getText()).find()) {
            throw new EmailFormatInvalidException();
        }
    }

    private void checkCinemaCapacityNumeric() throws CinemaCapacityNotUnsignedIntegerException {
        Pattern numberPattern = Pattern.compile("^[1-9][0-9]*$");
        if (!numberPattern.matcher(cinemaCapacityField.getText()).find()) {
            throw new CinemaCapacityNotUnsignedIntegerException();
        }
    }

    private void checkFieldsForNull() throws EmptyFieldException {
        if (usernameField.getText().isEmpty() || firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty()
                || emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new EmptyFieldException();
        }
        if (Objects.equals(role.getValue(), "Admin")
                && (cinemaNameField.getText().isEmpty() || cinemaCapacityField.getText().isEmpty() || cinemaAddressField.getText().isEmpty())) {
            throw new EmptyFieldException();
        }
    }
}
