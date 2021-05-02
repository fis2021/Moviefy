package org.loose.fis.mov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.services.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AddScreeningController extends AbstractController{
    @FXML
    public TextField movieTitleField;
    @FXML
    public TextField movieLengthField;
    @FXML
    public TextArea movieDescriptionField;
    @FXML
    public ComboBox<Integer> screeningDayField;
    @FXML
    public ComboBox<Integer> screeningMonthField;
    @FXML
    public ComboBox<Integer> screeningYearField;
    @FXML
    public ComboBox<Integer> screeningHourField;
    @FXML
    public ComboBox<Integer> screeningMinuteField;
    @FXML
    public ComboBox<String> availableMoviesField;

    @FXML
        public void initialize() {
            ObservableList<Integer> dayList = FXCollections.observableList(IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList()));
            screeningDayField.setItems(dayList);
            ObservableList<Integer> monthList = FXCollections.observableList(IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList()));
            screeningMonthField.setItems(monthList);
            ObservableList<Integer> yearList = FXCollections.observableList(IntStream.rangeClosed(2021, 2031).boxed().collect(Collectors.toList()));
            screeningYearField.setItems(yearList);
            ObservableList<Integer> hourList = FXCollections.observableList(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
            screeningHourField.setItems(hourList);
            ObservableList<Integer> minuteList = FXCollections.observableList(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
        screeningMinuteField.setItems(minuteList);

        ObservableList<String> availableMovieList = FXCollections.observableList(
                DatabaseService.getMovieRepo().find().toList().stream().
                        map(Movie::getTitle).collect(Collectors.toList())
        );
        availableMoviesField.setItems(availableMovieList);
    }

    @FXML
    public void handleAddScreening(ActionEvent event) {
        System.out.println("Not yet implemented");
    }

    @FXML
    public void handleMenuHome(ActionEvent event) {
        System.out.println("Not yet implemented");
    }

    @FXML
    public void handleMenuMyProfile(ActionEvent event) {
        System.out.println("Not yet implemented");
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
