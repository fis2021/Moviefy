package org.loose.fis.mov.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Review;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuMAINClientController extends AbstractMenusController implements Initializable {

    private static int CELL_SIZE = 20;
    @FXML
    private Slider MCSlider;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;
    @FXML
    private Label text;
    @FXML
    private ListView MCList;
    @FXML
    private ListView RMList;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private int curentlist;

    public void maintoprofile(ActionEvent event)
    throws IOException {

        changeScene(event, "MainMenuPROFILEClient.fxml");
    }

    public void maintobooking(ActionEvent event)
    throws IOException {

        changeScene(event, "MainMenuBOOKINGClient.fxml");
    }
    private void makeLabelsNull(){
        title.setText(" ");
        subtitle.setText(" ");
        text.setText(" ");
    }
    public void addButtonAction(ActionEvent event) throws IOException {
        changeScene(event,"PopUpReview.fxml");
    }
    public void editButtonAction(){

    }
    public void deleteButtonAction(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        MCSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                if ((int) MCSlider.getValue() == 0) {
                    makeLabelsNull();
                    RMList.setItems(null);
                    MCList.setItems(null);
                    ObservableList<?> movies = FXCollections
                            .observableList(MovieService.getAllMovies());
                    MCList.setFixedCellSize(CELL_SIZE);
                    MCList.setCellFactory(param -> new MovieCell());
                    MCList.setItems(movies);

                    MCList.setPrefHeight(movies.size() * MCList
                            .getFixedCellSize() + 2);
                } else {
                    addButton.setVisible(false);
                    addButton.setDisable(true);
                    editButton.setVisible(false);
                    editButton.setDisable(true);
                    deleteButton.setVisible(false);
                    deleteButton.setDisable(true);
                    makeLabelsNull();
                    RMList.setItems(null);
                    MCList.setItems(null);
                    ObservableList<?> cinemas = FXCollections
                            .observableList(CinemaService.getAllCinema());
                    MCList.setFixedCellSize(CELL_SIZE);
                    MCList.setCellFactory(param -> new CinemaCell());
                    MCList.setItems(cinemas);

                }
            }
        });
    }


    private class MovieCell extends ListCell {

        HBox hbox = new HBox();
        Label movieTitle = new Label("(empty)");
        Pane pane = new Pane();
        Button more = new Button("...");

        public MovieCell() {
            super();
            hbox.getChildren().addAll(movieTitle, pane, more);
            HBox.setHgrow(pane, Priority.ALWAYS);
            more.setOnAction(event -> {
                this.buttonAction();
            });
        }
        public void buttonAction() {
            title.setText(movieTitle.getText());
            addButton.setVisible(true);
            addButton.setDisable(false);
            //trebuie implementat partea de reviewuri
            ObservableList<?> Reviewlist=FXCollections
                    .observableList(ReviewService.findReviewsForMovie(movieTitle.getText()));
            //////////////
            RMList.setFixedCellSize(CELL_SIZE);
            RMList.setCellFactory(param -> new ReviewCell());
            RMList.setItems(Reviewlist);
            RMList.setPrefHeight(Reviewlist.size() *RMList
                    .getFixedCellSize() + 2);
//                editButton.setVisible(true);
//                editButton.setDisable(false);
//                deleteButton.setVisible(true);
//                deleteButton.setDisable(false);
            title.setText(movieTitle.getText());
            Movie film = null;

            film=MovieService.getMovieByTitle(movieTitle.getText());
            SessionService.setSelectedString(film);
            subtitle.setText(film.getDescription());
            text.setText(String.valueOf(film.getLength()));
        }

        protected void updateItem(Object item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                if (item instanceof Movie) {
                    movieTitle.setText(item != null ?
                                               ((Movie) item).getTitle() :
                                               "<null>");
                }
                /* this method call actually sets the appearance of our custom cell */
                setGraphic(hbox);
            }
        }
    }
    private class ReviewCell extends ListCell{
        HBox hbox = new HBox();
        Label review = new Label("(empty)");
        public ReviewCell() {
            super();
            hbox.getChildren().addAll(review);
            HBox.setHgrow(review,Priority.ALWAYS);
        }
        protected void updateItem(Object item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                if (item instanceof Review) {
                    review.setText(item != null ?
                            ((Review) item).getText() :
                            "<null>");
                    /* this method call actually sets the appearance of our custom cell */
                    setGraphic(hbox);
                }
            }
        }
    }


    private class ScreeningCell extends ListCell{
        HBox hbox = new HBox();
        Label MovieName = new Label("(empty)");
        Pane pane = new Pane();
        Button bookmovie = new Button("Book seats");
        public ScreeningCell() {
            super();
            hbox.getChildren().addAll(MovieName, pane, bookmovie);
            HBox.setHgrow(pane, Priority.ALWAYS);
            bookmovie.setOnAction(event -> {
                Screening cell = (Screening) getItem();
                try {
                    SessionService.setSelectedScreening(cell);
                    changeScene(event,"BookMovie.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        protected void updateItem(Object item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                if (item instanceof Screening) {
                    MovieName.setText(item != null ?
                            ((Screening) item).getMovieTitle() :
                            "<null>");
                    /* this method call actually sets the appearance of our custom cell */
                    setGraphic(hbox);
                }
            }
        }
    }

    private class CinemaCell extends ListCell {

        HBox hbox = new HBox();
        Label cinemaName = new Label("(empty)");
        Pane pane = new Pane();
        Button bookmovie = new Button("Browse Movie");

        public CinemaCell() {
            super();
            hbox.getChildren().addAll(cinemaName, pane, bookmovie);
            HBox.setHgrow(pane, Priority.ALWAYS);
            bookmovie.setOnAction(event -> {
                this.buttonAction();
            });
        }

        public void buttonAction() {
            title.setText(cinemaName.getText());
            Cinema cinem = null;
            cinem=CinemaService.findCinemaByName(cinemaName.getText());
            ObservableList<?> Screeninglist=FXCollections
                    .observableList(ScreeningService.findAllScreeningsForCinema(cinem));
            //////////////
            RMList.setFixedCellSize(CELL_SIZE);
            RMList.setCellFactory(param -> new ScreeningCell());
            RMList.setItems(Screeninglist);
            RMList.setPrefHeight(Screeninglist.size() *RMList
                    .getFixedCellSize() + 2);
        }

        protected void updateItem(Object item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                if (item instanceof Cinema) {
                    cinemaName.setText(item != null ?
                                               ((Cinema) item).getName() :
                                               "<null>");
                    /* this method call actually sets the appearance of our custom cell */
                    setGraphic(hbox);
                }
            }
        }
    }
}
