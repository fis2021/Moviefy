package org.loose.fis.mov.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import org.loose.fis.mov.services.CommService;

public class MovieListCell extends ListCell<Screening> {
    HBox hbox = new HBox();
    Label movieTitle = new Label("(empty)");
    Label screeningTime = new Label("(empty)");
    Pane pane = new Pane();
    Button bookingsButton = new Button("Bookings");
    Button deleteScreeningButton = new Button("Delete this screening");

    public MovieListCell() {
        super();
        screeningTime.setTextFill(Color.GREY);
        hbox.getChildren().addAll(movieTitle, screeningTime, pane, bookingsButton, deleteScreeningButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        bookingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Screening cell = getItem();
                System.out.println("This would take you to a list of bookings if it were implemented.");
            }
        });

        deleteScreeningButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Screening cell = getItem();
                System.out.println("This would delete the screening for " + cell.getMovieTitle() + " if it were implemented.");
            }
        });
    }

    @Override
    protected void updateItem(Screening item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            setGraphic(null);
        } else {
            movieTitle.setText(item != null ? item.getMovieTitle()  : "<null>");
            screeningTime.setText(item != null ? CommService.extractTime(item.getDate()) : "<null>");
            setGraphic(hbox);
        }
    }
}
