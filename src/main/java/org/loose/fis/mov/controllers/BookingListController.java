package org.loose.fis.mov.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.Booking;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.BookingService;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.SessionService;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;

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

        firstNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    UserService.findUser(cellData.getValue().getClientName()).getFirstname()
            );
        });
        lastNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    UserService.findUser(cellData.getValue().getClientName()).getLastname()
            );
        });
        emailNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    UserService.findUser(cellData.getValue().getClientName()).getEmail()
            );
        });
        seatNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getNumberOfSeats()));
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

