package org.loose.fis.mov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.loose.fis.mov.model.Movie;

public class MainMenuAdminController extends AbstractController {

    private VBox[] movies = new VBox[10];

    private Text[] texts = new Text[10];

    @FXML
    private ScrollPane scroll;

    @FXML
    private ListView list;

    @FXML
    public void initialize() {
        int ROW_SIZE = 24;
        int ITEM_NO = 5;
        try {
            for (int i = 0; i < ITEM_NO; i++) {
                System.out.println("merge");
                list.getItems().add(new Movie("salut", "fff", 23).getTitle());
            }
            list.setPrefHeight(ITEM_NO * ROW_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
