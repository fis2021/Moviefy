package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
        if (!areFieldsFilled()) {
            loginMessage.setText("A required field is empty!");
        } else {
            try {

                User user = UserService.login(usernameField.getText(), passwordField.getText());
                if (Objects.equals(user.getRole(), "Admin")) {
                    changeScene(event, "mainMenuAdmin.fxml");
                }
                else
                {
                    changeScene(event, "mainMenuMAINClient.fxml");
                }
            } catch (Exception e) {
                loginMessage.setText(e.getMessage());
            }
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

    private boolean areFieldsFilled() {
        return !usernameField.getText().isEmpty() && !passwordField.getText()
                .isEmpty();
    }
}
