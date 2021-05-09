package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

import static org.loose.fis.mov.services.CommService.WordGenerator;
import static org.loose.fis.mov.services.CommService.sendMail;

public  class ChangePasswordBeforeLogInController extends AbstractController{
    @FXML
    private TextField emailTextField;

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        changeScene(event, "login.fxml");
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException {
        String newPassword = WordGenerator(12);
        try {
            UserService.changePasswordBeforeLogin(emailTextField.getText(), newPassword);
            sendMail(emailTextField.getText(),"Moviefy Password Reset","Your new password is: " + newPassword);
            changeScene(event, "login.fxml");
        } catch (UserNotRegisteredException e) {
            e.printStackTrace();
        }
    }
}