package org.loose.fis.mov.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.loose.fis.mov.model.Movie;
import org.loose.fis.mov.model.Screening;
import org.loose.fis.mov.services.CommService;
import org.loose.fis.mov.services.MovieService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuMAINClientController extends AbstractMenusController implements Initializable {

    public void maintoprofile(ActionEvent event) throws IOException {

    changeScene(event,"MainMenuPROFILEClient.fxml");
    }
    public void maintobooking(ActionEvent event) throws IOException {

        changeScene(event,"MainMenuBOOKINGClient.fxml");
    }
    @FXML
    private Slider MCSlider;
    private static int CELL_SIZE=20;




    private class MovieCell extends ListCell<Movie>{
        HBox hbox = new HBox();
        Label movieTitle = new Label("(empty)");
        Pane pane = new Pane();
        Button more=new Button("...");

        public MovieCell(){
            super();
            hbox.getChildren().addAll(movieTitle, pane, more);
            HBox.setHgrow(pane, Priority.ALWAYS);
            more.setOnAction(event -> {
                this.buttonAction();
            });
        }

        public void buttonAction(){
            //cand este apasat un buton afiseaza datele acelui film in dreapta.
            System.out.println("Broo stiu ce e sexu da nu iti zic");
        }
        protected void updateItem(Movie item, boolean empty) {
            /* the inherited elements of the cell are left empty */
            super.updateItem(item, empty);
            setText(null);

            /* setting the fields specific for the custom cell */
            if (empty) {
                setGraphic(null);
            } else {
                movieTitle.setText(item != null ? item.getTitle()  : "<null>");
                /* this method call actually sets the appearance of our custom cell */
                setGraphic(hbox);
            }
        }
    }




    @FXML
    private ListView<Movie> MCList;

    private int curentlist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {




    MCSlider.valueProperty().addListener(new ChangeListener<Number>() {
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if((int)MCSlider.getValue()==0)
        {
            ObservableList<Movie> movies= FXCollections.observableList(MovieService.getAllMovies());
            MCList.setFixedCellSize(CELL_SIZE);
            MCList.setCellFactory(param -> new MovieCell());
            MCList.setItems(movies);

            MCList.setPrefHeight(movies.size() * MCList.getFixedCellSize() + 2);
        }
        else
        {
            curentlist=1;
            //change list to cinemas

        }
        System.out.println(curentlist);
    }
});
    //here

    }

}
