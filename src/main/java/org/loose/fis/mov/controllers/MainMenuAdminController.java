package org.loose.fis.mov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.loose.fis.mov.model.ScreeningListCell;
import org.loose.fis.mov.model.Screening;

import java.util.Date;

public class MainMenuAdminController extends AbstractController {
    private static final int CELL_SIZE = 30;

    @FXML
    private ListView<Screening> list;

    @FXML
    public void initialize() {
        /* these values w */
        list.setFixedCellSize(CELL_SIZE);
        int ITEM_NO = 5;

        /* changing the ListView to use our custom List Cells instead of the default ones */
        list.setCellFactory(param -> new ScreeningListCell());
        for (int i = 0; i < ITEM_NO; i++) {
            list.getItems().add(new Screening(null, new Date(System.currentTimeMillis()), "puerto niko", "cinema dacia", 12));
        }
        list.setPrefHeight(ITEM_NO * list.getFixedCellSize() + 2);

    }
}
