package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaIndexController {

    @FXML
    private Button btnEquipos;

    @FXML
    private Button btnJugadas;

    @FXML
    private Button btnJugadores;

    @FXML
    private Button btnResultados;

    @FXML
    void goEquipos(ActionEvent event) {

    }

    @FXML
    void goJugadas(ActionEvent event) {

    }

    @FXML
    void goJugadores(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MejJugEstVentana.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goResultados(ActionEvent event) {

    }



}