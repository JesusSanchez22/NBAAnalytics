package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MejJugPosController implements Initializable{

    private String[] posiciones = {"alero","pivot","base","ala-pivot","escolta"};


    @FXML
    private Button btnMostrar;

    @FXML
    private ComboBox<String> cbPosiciones;

    @FXML
    private TableColumn<String, ?> columnaAsistencias;

    @FXML
    private TableColumn<String, ?> columnaPuntos;

    @FXML
    private TableColumn<String, ?> columnaRebotes;

    @FXML
    private TableColumn<String, ?> columnaTapones;

    @FXML
    private TableView<String> tablaEstadisticas;

    @FXML
    private TextField txtResultado;

    @FXML
    void mostrar(ActionEvent event) {

        String posicion = cbPosiciones.getValue();

        txtResultado.setText(posicion);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPosiciones.getItems().addAll(posiciones);

        cbPosiciones.setValue("Elige posición");

    }

}
