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

/*
 * instead of only having one label and one item, this custom cell also contains buttons with actions linked
 * to the screening
 *
 * it extends ListCell<Screening> and overwrites its constructor, so it can be used by the cellFactory to
 * generate cells
 *
 * the code was adapted from this:
 * https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
 *
 * */
public class ScreeningListCell extends ListCell<Screening> {
    HBox hbox = new HBox();
    Label movieTitle = new Label("(empty)");
    Label screeningTime = new Label("(empty)");
    Pane pane = new Pane(); // this pane is using to create space between elements - more of them may be added
    Button bookingsButton = new Button("Bookings");
    Button deleteScreeningButton = new Button("Delete this screening");

    public ScreeningListCell() {
        super();
        screeningTime.setTextFill(Color.GREY);
        hbox.getChildren().addAll(movieTitle, screeningTime, pane, bookingsButton, deleteScreeningButton);
        HBox.setHgrow(pane, Priority.ALWAYS);

        bookingsButton.setOnAction(event -> {
            Screening cell = getItem();
            System.out.println("This would take you to a list of bookings if it were implemented.");
        });

        deleteScreeningButton.setOnAction(event -> {
            Screening cell = getItem();
            System.out.println("This would delete the screening for " + cell.getMovieTitle() + " if it were implemented.");
        });
    }

    @Override
    protected void updateItem(Screening item, boolean empty) {
        /* the inherited elements of the cell are left empty */
        super.updateItem(item, empty);
        setText(null);

        /* setting the fields specific for the custom cell */
        if (empty) {
            setGraphic(null);
        } else {
            movieTitle.setText(item != null ? item.getMovieTitle()  : "<null>");
            screeningTime.setText(item != null ? CommService.extractTime(item.getDate()) : "<null>");
            /* this method call actually sets the appearance of our custom cell */
            setGraphic(hbox);
        }
    }
}
