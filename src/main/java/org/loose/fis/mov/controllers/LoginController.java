package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.EmptyFieldException;
import org.loose.fis.mov.exceptions.PasswordIncorrectException;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.UserService;

import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text loginMessage;

    @FXML
    public void handleLoginAction() {
        try {
            checkFieldsForNull();
            User user = UserService.login(usernameField.getText(), passwordField.getText());
            loginMessage.setText(String.format("Welcome, %s %s!", user.getRole(), user.getUsername()));
        } catch (Exception e) {
            loginMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleChangePasswordAction() {

    }

    private void checkFieldsForNull() throws EmptyFieldException {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new EmptyFieldException();
        }
    }
}
