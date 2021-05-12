package org.loose.fis.mov.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.services.ReviewService;
import org.loose.fis.mov.services.SessionService;


import java.io.IOException;

enum ReviewMode {
    ADD,
    EDIT
}

public class PopUpReviewController extends AbstractMenusController{
    public static ReviewMode currentMode = ReviewMode.ADD;

    @FXML
    private TextArea wreview;
    @FXML
    private Label error;

    @FXML
    void initialize() {
        if (currentMode == ReviewMode.EDIT) {
            wreview.setText(ReviewService.getClientReview(SessionService.getLoggedInUser().getUsername(), SessionService.getSelectedMovie().getTitle()).getText());
        }
    }

    public void saveButton(ActionEvent event ) throws IOException {
        if (wreview.getText().isEmpty()) {
            error.setText("You need to write a review before posting!");
        } else {
            switch (currentMode) {
                case ADD:
                    Review newReview = new Review(null, SessionService.getLoggedInUser().getUsername(),SessionService.getSelectedMovie().getTitle(),wreview.getText());
                    ReviewService.addReview(newReview);
                    break;
                case EDIT:
                    Review existingReview = ReviewService.getClientReview(SessionService.getLoggedInUser().getUsername(), SessionService.getSelectedMovie().getTitle());
                    ReviewService.updateReview(existingReview, wreview.getText());
                    break;
            }
            changeScene(event,"MainMenuMAINClient.fxml");
        }
    }

    public void closeButton(ActionEvent event) throws IOException {
        changeScene(event,"MainMenuMAINClient.fxml");
    }
}
