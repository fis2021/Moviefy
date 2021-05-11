package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.loose.fis.mov.exceptions.PasswordIncorrectException;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.SessionService;
import org.loose.fis.mov.services.UserService;
import org.w3c.dom.Text;

import java.io.IOException;

public class MainMenuPROFILEClientController extends AbstractMenusController {

    @FXML
    private Label ul;
    @FXML
    private Label fl;
    @FXML
    private Label ll;
    @FXML
    private Label rl;
    @FXML
    private Label el;
    @FXML
    private Label cpm;
    @FXML
    private PasswordField op;
    @FXML
    private PasswordField np;
    @FXML
    private TextField nu;

    public void initialize(){
        ul.setText(SessionService.getLoggedInUser().getUsername());
        fl.setText(SessionService.getLoggedInUser().getFirstname());
        ll.setText(SessionService.getLoggedInUser().getLastname());
        el.setText(SessionService.getLoggedInUser().getEmail());
        rl.setText(SessionService.getLoggedInUser().getRole());
    }

    public void profiletobooking(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuBOOKINGClient.fxml");
    }
    public void profiletomain(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuMAINClient.fxml");
    }
    public void changeUsername(){
    if(nu.getText().isEmpty())
    {
        nu.setPromptText("Add a valid Username");
    }
    else
    {
        UserService.changeUsername(SessionService.getLoggedInUser(),nu.getText());
        initialize();
        nu.setText(null);
        nu.setPromptText("Type here the new Username");
    }
}
    public void changePassword() throws PasswordIncorrectException {
        if (op.getText().isEmpty()&& np.getText().isEmpty()) {
            cpm.setText("A required field is empty!");
        } else if (!CommService.isPasswordValid(np.getText())) {
            cpm.setText("The password must be at least 8 characters long!");
        } else {
            try {
                UserService.changePasswordAfterLogin(
                        op.getText(),
                        np.getText()

                );
                op.setText("");
                np.setText("");
                cpm.setText("Password change successful!");
            } catch (Exception e) {
                cpm.setText(e.getMessage());
            }
        }
    }

}
