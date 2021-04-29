package org.loose.fis.mov.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Properties;
import javafx.event.ActionEvent;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.loose.fis.mov.services.CommService.WordGenerator;
import static org.loose.fis.mov.services.CommService.sendMail;
import static org.loose.fis.mov.services.UserService.findUserByEmail;

public  class ChangePasswordBeforeLogInController extends AbstractController{
    @FXML
    private TextField emailTextField;

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        changeScene(event, "login.fxml");
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException, UserNotRegisteredException {
        String email = emailTextField.getText();
        String newPassword=WordGenerator(12);
        UserService.changePassword(email, newPassword);
        sendMail(email,"Moviefy Password Reset","Your new password is: " + newPassword);
        changeScene(event, "login.fxml");
    }
}