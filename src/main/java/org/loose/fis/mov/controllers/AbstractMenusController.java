package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

abstract class AbstractMenusController extends AbstractController{

    @FXML
    public void handleLogoutButton(ActionEvent event) throws  IOException {
        UserService.logout();
        changeScene(event, "login.fxml");
    }
}
