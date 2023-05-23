package com.example.nbaanalytics;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MejJugPosController implements Initializable{

    private String[] posiciones = {"alero","pivot","base","ala-pivot","escolta"};


    @FXML
    private Button btnMostrar;

    @FXML
    private ComboBox<String> cbPosiciones;

    @FXML
    private TableColumn<String, String> columnaAsistencias;

    @FXML
    private TableColumn<String, String> columnaPuntos;

    @FXML
    private TableColumn<String, String> columnaRebotes;

    @FXML
    private TableColumn<String, String> columnaTapones;

    @FXML
    private TableView<String> tablaEstadisticas;

    @FXML
    private TextField txtResultadoJugador;



    @FXML
    void mostrar(ActionEvent event) throws SQLException {

        String posicion = cbPosiciones.getValue();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String sql = "SELECT * FROM usuarios";

        ResultSet rs = stmt.executeQuery(sql);

        switch (posicion){
            case "pivot":

                ResultSet mejorPivot = stmt.executeQuery("select jugadores.Nombre\n" +
                        " from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo where Posicion = \"C\" \n" +
                        " group by jugadores.Nombre order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1;");


                if (mejorPivot.next()) {
                    String nombreMejorPivot = mejorPivot.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }


                mejorPivot.close();

                break;

            case "alero":

                ResultSet mejorAlero = stmt.executeQuery("select jugadores.Nombre\n" +
                        " from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo where Posicion = \"F\" \n" +
                        " group by jugadores.Nombre order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1;");


                if (mejorAlero.next()) {
                    String nombreMejorPivot = mejorAlero.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorAlero.close();
                break;

            case "base":

                ResultSet mejorBase = stmt.executeQuery("select jugadores.Nombre\n" +
                        " from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo where Posicion = \"G\" \n" +
                        " group by jugadores.Nombre order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1;");


                if (mejorBase.next()) {
                    String nombreMejorPivot = mejorBase.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorBase.close();

                break;

            case "ala-pivot":
                ResultSet mejorAlaPivot = stmt.executeQuery("select jugadores.Nombre\n" +
                        " from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo where Posicion = \"C-F\" or Posicion = \"F-C\" \n" +
                        " group by jugadores.Nombre order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1;");


                if (mejorAlaPivot.next()) {
                    String nombreMejorPivot = mejorAlaPivot.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorAlaPivot.close();
                break;

            case "escolta":
                ResultSet mejorEscolta = stmt.executeQuery("select jugadores.Nombre\n" +
                        " from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo where Posicion = \"G-F\" or Posicion = \"F-G\" \n" +
                        " group by jugadores.Nombre order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1;");


                if (mejorEscolta.next()) {
                    String nombreMejorPivot = mejorEscolta.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorEscolta.close();
                break;
        }




    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPosiciones.getItems().addAll(posiciones);

        cbPosiciones.setValue("Elige posición");



    }

}
