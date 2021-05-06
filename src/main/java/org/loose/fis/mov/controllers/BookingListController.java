package org.loose.fis.mov.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.*;

import java.io.IOException;
import java.util.List;

public class BookingListController extends AbstractController {

    @FXML
    private Text pageTitle;
    @FXML
    public TableColumn<Booking, String> firstNameColumn;
    @FXML
    public TableColumn<Booking, String> lastNameColumn;
    @FXML
    public TableColumn<Booking, String> emailNameColumn;
    @FXML
    public TableColumn<Booking, Integer> seatNumberColumn;
    @FXML
    private TableView<Booking> table;

    @FXML
    public void initialize() {
        Screening selectedScreening = SessionService.getSelectedScreening();
        pageTitle.setText(
                "Bookings for movie "
                        + selectedScreening.getMovieTitle()
                        + " on "
                        + CommService.extractDate(selectedScreening.getDate())
                        + " "
                        + CommService.extractTime(selectedScreening.getDate())
        );
        ObservableList<Booking> observableList =
                FXCollections.observableList(BookingService.findBookingsAtScreening(selectedScreening));

        /* setting the appropiate info for each column - not yet */

        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientName()));
        emailNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientName()));
        seatNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getNumberOfSeats()));
        table.setItems(observableList);
    }

    @FXML
    public void handleMenuHome(ActionEvent event) throws IOException {
        changeScene(event, "mainMenuAdmin.fxml");
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) throws IOException {
        changeScene(event, "userProfile.fxml");
    }

    @FXML
    public void handleMenuAddScreening(ActionEvent event) throws IOException {
        changeScene(event, "addScreening.fxml");
    }

    @FXML
    public void handleMenuLogout(ActionEvent event) throws Exception {
        UserService.logout();
        changeScene(event, "login.fxml");
    }
}

