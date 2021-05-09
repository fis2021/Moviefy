package org.loose.fis.mov.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.io.IOException;
import java.net.URL;
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
    @FXML
    private ListView<Object> MCList;
    private int checkChar(char list){
        if(list==0)return 0;
        else
            return 1;
    }
    private int curentlist=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    MCSlider.valueProperty().addListener(new ChangeListener<Number>() {
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if((int)MCSlider.getValue()==0)
        {
            curentlist=0;
        }
        else
        {
            curentlist=1;
        }
        System.out.println(curentlist);
    }
});
    //here

    }
}
