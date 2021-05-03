package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.mov.exceptions.EmptyFieldException;
import org.loose.fis.mov.exceptions.PasswordIncorrectException;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;
import java.util.Objects;

public class LoginController extends AbstractController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text loginMessage;

    @FXML
    public void handleLoginAction(ActionEvent event) {
        try {
            checkFieldsForNull();
            User user = UserService.login(usernameField.getText(), passwordField.getText());
            if (Objects.equals(user.getRole(), "Admin")) {
                changeScene(event, "addScreening.fxml");
            } else {
                loginMessage.setText(String.format("Welcome, %s %s!", user.getRole(), user.getUsername()));
            }
        } catch (Exception e) {
            loginMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleChangePasswordAction(ActionEvent event) throws IOException {
        changeScene(event, "changePasswordPopUp.fxml");
    }

    @FXML
    public void handleRegisterAction(ActionEvent event) throws IOException {
        changeScene(event,"register.fxml");
    }

    private void checkFieldsForNull() throws EmptyFieldException {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new EmptyFieldException();
        }
    }
}
