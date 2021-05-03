package org.loose.fis.mov.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.mov.exceptions.CinemaCapacityNotUnsignedIntegerException;
import org.loose.fis.mov.exceptions.DateInThePastException;
import org.loose.fis.mov.exceptions.EmptyFieldException;
import org.loose.fis.mov.exceptions.MovieLengthNotUnsignedIntegerException;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.User;
import org.loose.fis.mov.services.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AddScreeningController extends AbstractController{
    @FXML
    private TabPane tabs;
    @FXML
    private TextField movieTitleField;
    @FXML
    private TextField movieLengthField;
    @FXML
    private TextArea movieDescriptionField;
    @FXML
    private ComboBox<Integer> screeningDayField;
    @FXML
    private ComboBox<Integer> screeningMonthField;
    @FXML
    private ComboBox<Integer> screeningYearField;
    @FXML
    private ComboBox<Integer> screeningHourField;
    @FXML
    private ComboBox<Integer> screeningMinuteField;
    @FXML
    private ComboBox<String> availableMoviesField;
    @FXML
    private Text message;

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
        try {
            checkFieldsForNull();
            Calendar calendar = new GregorianCalendar(
                    screeningYearField.getValue(),
                    screeningMonthField.getValue() - 1,
                    screeningDayField.getValue(),
                    screeningHourField.getValue(),
                    screeningMinuteField.getValue()
            );
            // setting the calendar mode to non-lenient so invalid dates are rejected;
            calendar.setLenient(false);
            Date screeningDate = calendar.getTime();
            // checking if the inputted date is not a past date;
            if (CommService.isDateInThePast(screeningDate) == true) {
                throw new DateInThePastException();
            }

            // adding new movie;
            if (tabs.getSelectionModel().isSelected(0)) {
                checkMovieLengthNumeric();
                ScreeningService.addScreening(
                        movieTitleField.getText(),
                        movieDescriptionField.getText(),
                        Integer.parseInt(movieLengthField.getText()),
                        screeningDate
                );
            }
            // adding already existing movie;
            else {
                ScreeningService.addScreening(
                        availableMoviesField.getValue(),
                        "placeholder",
                        0,
                        screeningDate
                );
            }
            message.setText("Successfully added screening!");
        } catch (IllegalArgumentException e) {
            message.setText("The date you entered is invalid!");
        } catch (Exception e) {
            message.setText(e.getMessage());
        }
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

    private void checkFieldsForNull() throws EmptyFieldException {
        if (tabs.getSelectionModel().isSelected(0)) {
            if (movieTitleField.getText().isEmpty() || movieDescriptionField.getText().isEmpty() || movieLengthField.getText().isEmpty()) {
                throw new EmptyFieldException();
            }
        } else {
            if (Objects.equals(availableMoviesField.getValue(), "")) {
                throw new EmptyFieldException();
            }
        }
        if (Objects.equals(screeningDayField.getValue(), null)
                || Objects.equals(screeningMonthField.getValue(), null)
                || Objects.equals(screeningYearField.getValue(), null)
                || Objects.equals(screeningHourField.getValue(), null)
                || Objects.equals(screeningMinuteField.getValue(), null)) {
            throw new EmptyFieldException();
        }
    }

    private void checkMovieLengthNumeric() throws MovieLengthNotUnsignedIntegerException {
        Pattern numberPattern = Pattern.compile("^[1-9][0-9]*$");
        if (!numberPattern.matcher(movieLengthField.getText()).find()) {
            throw new MovieLengthNotUnsignedIntegerException();
        }
    }
}
