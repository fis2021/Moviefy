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

    @FXML
    public void handleRegisterAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void checkFieldsForNull() throws EmptyFieldException {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new EmptyFieldException();
        }
    }


}