package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminEquiposController implements Initializable {

    private String[] campos = {"Nombre","Ciudad","Conferencia","Divisions"};


    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnBorrar1;

    @FXML
    private Button btnBorrar2;

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
    void actualizar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String campo = cbCampo.getValue();
        String cambio = txtCampoACambiar.getText();


    }

    @FXML
    void borrar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


        String nombre = txtNombre.getText();
        String sentenciaBorrado = "select * from equipos";
        ResultSet rs = stmt.executeQuery(sentenciaBorrado);


        do{
            rs.next();
            String nombreBorrar = rs.getString("Nombre");

            if (nombreBorrar.equals(nombre)){
                int num = rs.getRow();
                rs.absolute(num);
                rs.deleteRow();
            }
        }while(rs.next());


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