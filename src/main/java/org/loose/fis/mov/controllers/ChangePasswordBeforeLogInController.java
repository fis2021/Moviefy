package org.loose.fis.mov.controllers;

import javafx.event.EventHandler;
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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.loose.fis.mov.services.CommService.WordGenerator;
import static org.loose.fis.mov.services.CommService.sendMail;
import static org.loose.fis.mov.services.UserService.findUserByEmail;

public  class ChangePasswordBeforeLogInController extends AbstractController{



    @FXML
    private TextField emialtextfield;
    private String email;

    @FXML
    public void switchToRegister(ActionEvent event) throws IOException {
        changeScene(event,"register.fxml");
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException, UserNotRegisteredException {

        email = emialtextfield.getText();
        User user=findUserByEmail(email);
        String newPassword=WordGenerator(12);
        user.setPassword(newPassword);
        sendMail(email,"Kingule ti-o picat parola","Parola ta este: "+newPassword+" Sa nu spui la nimeni;)");
        changeScene(event,"register.fxml");
    }
}