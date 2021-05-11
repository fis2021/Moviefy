package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.services.ReviewService;
import org.loose.fis.mov.services.SessionService;


import java.io.IOException;

public class PopUpReviewController extends AbstractMenusController{

    @FXML
    private TextField wreview;
    @FXML
    private Label eroare;
    public void saveButton(ActionEvent event ) throws IOException {

        if(wreview.getText().isEmpty())
        {
        eroare.setText("You need to write a review before posting!");
        }
        else{
            Review review= new Review(null, SessionService.getLoggedInUser().getUsername(),SessionService.getSelectedMovie().getTitle(),wreview.getText());
        changeScene(event,"MainMenuMAINClient.fxml");
        ReviewService.addReview(review);
    }
    }
    public void closeButton(ActionEvent event) throws IOException {
        changeScene(event,"MainMenuMAINClient.fxml");
    }
}
