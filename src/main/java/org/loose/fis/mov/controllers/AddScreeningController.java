package org.loose.fis.mov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.loose.fis.mov.exceptions.SessionDoesNotExistException;
import org.loose.fis.mov.services.UserService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddScreeningController extends AbstractController{
    @FXML
    public TextField movieTitleField;
    @FXML
    public TextField movieLengthField;
    @FXML
    public TextArea movieDescriptionField;
    @FXML
    public ComboBox<Integer> screeningDay;
    @FXML
    public ComboBox<Integer> screeningMonth;
    @FXML
    public ComboBox<Integer> screeningYear;
    @FXML
    public ComboBox<Integer> screeningHour;
    @FXML
    public ComboBox<Integer> screeningMinute;

    @FXML
    public void initialize() {
        ObservableList<Integer> dayList = FXCollections.observableList(IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList()));
        screeningDay.setItems(dayList);
        ObservableList<Integer> monthList = FXCollections.observableList(IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList()));
        screeningMonth.setItems(monthList);
        ObservableList<Integer> yearList = FXCollections.observableList(IntStream.rangeClosed(2021, 2031).boxed().collect(Collectors.toList()));
        screeningYear.setItems(yearList);
        ObservableList<Integer> hourList = FXCollections.observableList(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
        screeningHour.setItems(hourList);
        ObservableList<Integer> minuteList = FXCollections.observableList(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
        screeningMinute.setItems(minuteList);

        
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
