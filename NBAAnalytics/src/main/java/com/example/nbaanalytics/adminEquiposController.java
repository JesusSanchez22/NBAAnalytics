package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminEquiposController implements Initializable {

    private String[] campos = {"Nombre","Ciudad","Conferencia","Division"};


    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnDatos;

    @FXML
    private Button btnInsertar;

    @FXML
    private ComboBox<String> cbCampo;

    @FXML
    private TextField txtCampoACambiar;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtConferencia;

    @FXML
    private TextField txtDivision;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNombreEquipo;

    @FXML
    private TextField txtRegistro;

    @FXML
    private TextField txtRegistro2;

    @FXML
    void goDatos(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EquiposVentana.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void actualizar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String nombreEquipo = txtRegistro2.getText();
        String campo = cbCampo.getValue();
        String cambio = txtCampoACambiar.getText();


        String sentenciaBusqueda = "SELECT * FROM equipos WHERE Nombre = '" + nombreEquipo + "'";
        ResultSet rs = stmt.executeQuery(sentenciaBusqueda);

        if (rs.next()) {

            rs.updateString(campo, cambio);
            rs.updateRow();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se encontró ningún equipo con el nombre " + nombreEquipo);
            alert.showAndWait();
        }
    }

    @FXML
    void borrar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String nombreEquipo = txtRegistro.getText();


        String sentenciaBusqueda = "SELECT * FROM equipos WHERE Nombre = '" + nombreEquipo + "'";
        ResultSet rs = stmt.executeQuery(sentenciaBusqueda);

        if (rs.next()) {

            rs.deleteRow();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se encontró ningún equipo con el nombre " + nombreEquipo);
            alert.showAndWait();
        }
    }

    @FXML
    void insertar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String nombre = txtNombre.getText();
        String ciudad = txtCiudad.getText();
        String conferencia = txtConferencia.getText();
        String division = txtDivision.getText();

        String sentenciaInsertar = "Select * from equipos";
        ResultSet rs = stmt.executeQuery(sentenciaInsertar);

        rs.moveToInsertRow();
        rs.updateString("Nombre",nombre);
        rs.updateString("Ciudad",ciudad);
        rs.updateString("Conferencia",conferencia);
        rs.updateString("Division",division);
        rs.insertRow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCampo.getItems().addAll(campos);

        cbCampo.setValue("Elige campo");

    }
}