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
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.CinemaService;
import org.loose.fis.mov.services.MovieService;
import org.loose.fis.mov.services.ScreeningService;
import org.loose.fis.mov.services.SessionService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
            addButton.setVisible(true);
            addButton.setDisable(false);
            title.setText(movieTitle.getText());
            Movie film = null;
            film=MovieService.getMovieByTitle(movieTitle.getText());
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
