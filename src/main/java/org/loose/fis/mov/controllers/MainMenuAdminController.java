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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.*;

import java.io.IOException;
import java.util.List;


import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.dizitart.no2.objects.filters.ObjectFilters.gt;

public class MainMenuAdminController extends AbstractMenusController {
    private static final int CELL_SIZE = 30;

    @FXML
    private Text pageTitle;
    @FXML
    private ListView<Screening> list;

    @FXML
    public void initialize() {
        Cinema cinema = CinemaService.findCinemaForAdmin(SessionService.getLoggedInUser());

        pageTitle.setText("Future screenings for " + cinema.getName());
        ObservableList<Screening> observableList = FXCollections
                .observableList(
                        ScreeningService
                                .findAllFutureScreeningsForCinema(cinema)
                );
        list.setFixedCellSize(CELL_SIZE);

        /* changing the ListView to use our custom List Cells instead of the default ones */
        list.setCellFactory(param -> new ScreeningListCell());
        list.setItems(observableList);
        list.setPrefHeight(observableList.size() * list.getFixedCellSize() + 2);
    }

//    @FXML
//    public void handleMenuLogout(ActionEvent event) throws SessionDoesNotExistException, IOException {
//        UserService.logout();
//        changeScene(event, "login.fxml");
//    }

    @FXML
    public void handleMenuHome(ActionEvent event) throws IOException {
        changeScene(event, "mainMenuAdmin.fxml");
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) throws IOException {
        changeScene(event, "userProfile.fxml");
    }

    public void handleMenuAddScreening(ActionEvent event) throws IOException {
        changeScene(event, "addScreening.fxml");
    }

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
     */

    private class ScreeningListCell extends ListCell<Screening> {
        HBox hbox = new HBox();
        Label movieTitle = new Label("(empty)");
        Label screeningTime = new Label("(empty)");
        Pane pane = new Pane(); // this pane is using to create space between elements - more of them may be added
        Button bookingsButton = new Button("Bookings");
        Button deleteScreeningButton = new Button("Delete this screening");
        boolean isCancelled = false;

        public ScreeningListCell() {
            super();
            screeningTime.setTextFill(Color.GREY);
            hbox.getChildren().addAll(movieTitle, screeningTime, pane, bookingsButton, deleteScreeningButton);
            HBox.setHgrow(pane, Priority.ALWAYS);

            bookingsButton.setOnAction(event -> {
                Screening cell = getItem();
                try {
                    SessionService.setSelectedScreening(cell);
                    changeScene(event, "bookingList.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            deleteScreeningButton.setOnAction(event -> {
                Screening screening = getItem();
                ScreeningService.deleteScreening(screening);
                List<User> bookedUsers = BookingService.findUsersWithBookingAtScreening(screening);

                BookingService.findBookingsAtScreening(screening).forEach(
                        BookingService::deleteBooking);

                CommService.sendMail(
                        bookedUsers,
                        "Booking cancelled.",
                        "Your booking for "
                                + screening.getMovieTitle()
                                + " at "
                                + screening.getCinemaName()
                                + " on "
                                + CommService.extractDate(screening.getDate())
                                + " " + CommService
                                .extractTime(screening.getDate())
                                + " was cancelled.\nWe are sorry!"
                );


                isCancelled = true;
                screeningTime.setTextFill(Color.CRIMSON);
                screeningTime.setText("Cancelled");
                bookingsButton.setVisible(false);
                deleteScreeningButton.setVisible(false);
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
                screeningTime.setText(
                        item != null ?
                                !isCancelled ?
                                        CommService.extractDate(item.getDate()) + " " + CommService.extractTime(item.getDate()) :
                                        "Cancelled"
                                :
                                "<null>"
                );
                /* this method call actually sets the appearance of our custom cell */
                setGraphic(hbox);
            }
        }
    }
}
