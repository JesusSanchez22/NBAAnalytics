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

public class adminJugadoresController implements Initializable {

    private String[] campos = {"Nombre","Procedencia","Altura","Peso","Posicion","Nombre_equipo"};

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
    private TextField txtAltura;

    @FXML
    private TextField txtCampoACambiar;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtEquipo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtPosicion;

    @FXML
    private TextField txtProcedencia;

    @FXML
    private TextField txtNombreBorrar;

    @FXML
    private TextField txtCodigoActualizar;

    @FXML
    void actualizar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String campo = cbCampo.getValue();
        String cambio = txtCampoACambiar.getText();
        String codigoJugadorStr = txtCodigoActualizar.getText();
        int codigoJugador = Integer.parseInt(codigoJugadorStr);

        String sentenciaBusqueda = "SELECT * FROM jugadores WHERE codigo = '" + codigoJugador + "'";
        ResultSet rs = stmt.executeQuery(sentenciaBusqueda);

        if (rs.next()) {

            rs.updateString(campo, cambio);
            rs.updateRow();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se encontró ningún jugador que se llame " + codigoJugador);
            alert.showAndWait();
        }
    }

    @FXML
    void borrar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String nombreJugador = txtNombreBorrar.getText();


        String sentenciaBusqueda = "SELECT * FROM jugadores WHERE nombre = '" + nombreJugador + "'";
        ResultSet rs = stmt.executeQuery(sentenciaBusqueda);

        if (rs.next()) {
            rs.deleteRow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se encontró ningún jugador que se llame " + nombreJugador);
            alert.showAndWait();
        }
    }

    @FXML
    void goDatos(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosJugadoresVentana.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void insertar(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String codigoStr = txtCodigo.getText();
        int codigo = Integer.parseInt(codigoStr);
        String nombre = txtNombre.getText();
        String procedencia = txtProcedencia.getText();
        String altura = txtAltura.getText();
        String pesoStr = txtPeso.getText();
        int peso = Integer.parseInt(pesoStr);
        String posicion = txtPosicion.getText();
        String equipo = txtEquipo.getText();


        String sentenciaInsertar = "Select * from jugadores";
        ResultSet rs = stmt.executeQuery(sentenciaInsertar);

        rs.moveToInsertRow();
        rs.updateInt("codigo",codigo);
        rs.updateString("Nombre",nombre);
        rs.updateString("Procedencia",procedencia);
        rs.updateString("Altura",altura);
        rs.updateInt("Peso",peso);
        rs.updateString("Posicion",posicion);
        rs.updateString("Nombre_equipo",equipo);
        rs.insertRow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCampo.getItems().addAll(campos);

        cbCampo.setValue("Elige campo");
    }
}