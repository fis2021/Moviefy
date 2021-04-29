package org.loose.fis.mov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.SessionDoesNotExistException;
import org.loose.fis.mov.exceptions.UserNotAdminException;
import org.loose.fis.mov.exceptions.UserNotRegisteredException;
import org.loose.fis.mov.model.ScreeningListCell;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.dizitart.no2.objects.filters.ObjectFilters.gt;

public class MainMenuAdminController extends AbstractController {
    private static final int CELL_SIZE = 30;
    private ObservableList<Screening> observableList;

    @FXML
    private Text pageTitle;
    @FXML
    private ListView<Screening> list;

    @FXML
    public void initialize() {
        try {
            observableList = FXCollections.observableList(
                    ScreeningService.findAllFutureScreeningsForCinema(
                            CinemaService.findCinemaForAdmin(
                                    SessionService.getLoggedInUser()
                            )
                    )
            );
            list.setFixedCellSize(CELL_SIZE);

            /* changing the ListView to use our custom List Cells instead of the default ones */
            list.setCellFactory(param -> new ScreeningListCell());
            list.setItems(observableList);
            list.setPrefHeight(observableList.size() * list.getFixedCellSize() + 2);
        } catch (UserNotAdminException e) {
            pageTitle.setText(e.getMessage());
        }
    }

    @FXML
    public void handleLogout(ActionEvent event) throws SessionDoesNotExistException, IOException {
        UserService.logout();
        changeScene(event, "login.fxml");
    }

    @FXML
    public void handleHome(ActionEvent event) throws IOException {
        changeScene(event, "mainMenuAdmin.fxml");
    }

    @FXML
    public void handleMyProfile(ActionEvent actionEvent) {
        System.out.println("This would have taken you to your profile. It's a shame that page doesn't even exist yet.");
    }

    public void handleAddScreening(ActionEvent actionEvent) {
        System.out.println("This would have taken you to the Add Screening page. We do not have such capabilities yet.");
    }
}
