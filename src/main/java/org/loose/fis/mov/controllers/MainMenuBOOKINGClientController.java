package org.loose.fis.mov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.dizitart.no2.NitriteId;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.BookingService;
import org.loose.fis.mov.services.ScreeningService;
import org.loose.fis.mov.services.SessionService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuBOOKINGClientController extends AbstractMenusController{

    @FXML
    private ListView list;

    public void bookingtoprofile(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuPROFILEClient.fxml");

    }
    public void bookingtomain(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuMAINClient.fxml");
    }

    public void initialize() {
            ObservableList<?> bookinglist= FXCollections.observableList(BookingService.findBookingofUser(SessionService.getLoggedInUser()));

        list.setCellFactory(param -> new BookingCell());
        list.setItems(bookinglist);
        list.setPrefHeight(bookinglist.size() * list
                .getFixedCellSize() + 2);
    }


    private class BookingCell extends ListCell {
        private NitriteId Id;
        HBox hbox = new HBox();
        Label movieTitle = new Label("(empty)");
        Label nrseats = new Label("(empty)");
        Pane pane = new Pane();
        Button delete = new Button("Delete");
        public BookingCell() {
            super();
            hbox.getChildren().addAll(movieTitle,nrseats,pane, delete);
            HBox.setHgrow(pane, Priority.ALWAYS);
            delete.setOnAction(event -> {
                this.deleteAction();
            });
        }

        public void deleteAction(){
            //E nevoie de un booking dar in functie de ce?ID!
            BookingService.deleteBooking(BookingService.findBookingByID(Id));
            delete.setDisable(true);
        }
        protected void updateItem(Object item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                if (item instanceof Booking) {
                    Id=(((Booking) item).getId());
                    movieTitle.setText(item != null ?
                            ScreeningService.findScreeningByID(((Booking) item).getScreeningId()).getMovieTitle()+"     Seats:":
                            "<null>");
                   nrseats.setText(item != null ?
                            String.valueOf(((Booking) item).getNumberOfSeats() ):
                            "<null>");

                }
                setGraphic(hbox);
            }
        }

    }
    }
