package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

import static org.loose.fis.mov.services.CommService.WordGenerator;
import static org.loose.fis.mov.services.CommService.sendMail;

public  class ChangePasswordBeforeLogInController extends AbstractController{

    @FXML
    private TextField emailTextField;
    @FXML
    private Text message;

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        changeScene(event, "login.fxml");
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException {
        if (!isFieldFilled()) {
            message.setText("The e-mail field is empty!");
        } else if (!CommService.isEmailValid(emailTextField.getText())) {
            message.setText("E-mail format is invalid!");
        } else {
            String newPassword = WordGenerator(12);
            try {
                UserService.changePasswordBeforeLogin(emailTextField.getText(), newPassword);
                sendMail(emailTextField.getText(),"Moviefy Password Reset","Your new password is: " + newPassword);
                changeScene(event, "login.fxml");
            } catch (UserNotRegisteredException e) {
                message.setText(e.getMessage());
            }
        }
    }

    private boolean isFieldFilled() {
        return !emailTextField.getText().isEmpty();
    }
}