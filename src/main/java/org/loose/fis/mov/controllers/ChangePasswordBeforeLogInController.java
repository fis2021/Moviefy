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
import static org.loose.fis.mov.services.UserService.findUserByEmail;

public  class ChangePasswordBeforeLogInController {



    @FXML
    private TextField emialtextfield;
    private String email;

    @FXML
    public void switchToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToRegisterWithPassword(ActionEvent event) throws IOException, UserNotRegisteredException {

        email = emialtextfield.getText();
        User user=findUserByEmail(email);
        String newPassword=WordGenerator(12);
        user.setPassword(newPassword);
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");

        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("geani.gibilan", "mojojojo13");
            }
        });
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress("geani.gibilan@gmail.com"));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // Set Subject: header field
            message.setSubject("Kingule, ti-a cazut parola");
            // Now set the actual message
            message.setText("Parola  este "+ newPassword+" sa nu zici la nimeni ;)");
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}