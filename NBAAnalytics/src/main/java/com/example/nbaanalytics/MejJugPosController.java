package com.example.nbaanalytics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.Statement;

public class MejJugPosController implements Initializable{

    private String[] posiciones = {"alero","pivot","base","ala-pivot","escolta"};


    @FXML
    private Button btnMostrar;

    @FXML
    private ComboBox<String> cbPosiciones;

    @FXML
    private TableColumn<Jugador,Integer> columnaAsistencias;

    @FXML
    private TableColumn<Jugador,Integer> columnaPuntos;

    @FXML
    private TableColumn<Jugador,Integer> columnaRebotes;

    @FXML
    private TableColumn<Jugador,Integer> columnaTapones;

    @FXML
    private TableView<Jugador> tablaEstadisticas;

    @FXML
    private TextField txtResultadoJugador;

    private ObservableList<Jugador> puntuaciones;

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

                ResultSet puntosPivot = stmt.executeQuery("Select round(sum(Puntos_por_partido)) from estadisticas where jugador = (select jugadores.codigo\n" +
                        " from jugadores inner join estadisticas on estadisticas.jugador=jugadores.codigo where Posicion = \"C\" \n" +
                        " group by jugadores.codigo order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1);");
                ResultSet asistenciasPivot = stmt.executeQuery(" Select round(sum(Asistencias_por_partido)) from estadisticas where jugador = (select jugadores.codigo\n" +
                        " from jugadores inner join estadisticas on estadisticas.jugador=jugadores.codigo where Posicion = \"C\" \n" +
                        " group by jugadores.codigo order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1);");
                ResultSet rebotesPivot = stmt.executeQuery(" Select round(sum(Rebotes_por_partido)) from estadisticas where jugador = (select jugadores.codigo\n" +
                        " from jugadores inner join estadisticas on estadisticas.jugador=jugadores.codigo where Posicion = \"C\" \n" +
                        " group by jugadores.codigo order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1);");
                ResultSet taponesPivot = stmt.executeQuery(" Select round(sum(Tapones_por_partido)) from estadisticas where jugador = (select jugadores.codigo\n" +
                        " from jugadores inner join estadisticas on estadisticas.jugador=jugadores.codigo where Posicion = \"C\" \n" +
                        " group by jugadores.codigo order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                        " desc limit 1);");

                // Obtengo los datos del formulario
                int puntos = 0;
                int asistencias = 0;
                int tapones = 0;
                int rebotes = 0;

                if (puntosPivot.next()) {
                    puntos = puntosPivot.getInt(1);
                }
                if (asistenciasPivot.next()) {
                    asistencias = asistenciasPivot.getInt(1);
                }
                if (rebotesPivot.next()) {
                    rebotes = rebotesPivot.getInt(1);
                }
                if (taponesPivot.next()) {
                    tapones = taponesPivot.getInt(1);
                }

                // Creo una persona
                Jugador jugador1 = new Jugador(puntos,asistencias,rebotes,tapones);
                // Lo añado a la lista
                this.puntuaciones.add(jugador1);
                // Seteo los items
                this.tablaEstadisticas.setItems(puntuaciones);

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


                break;
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPosiciones.getItems().addAll(posiciones);

        cbPosiciones.setValue("Elige posición");


        puntuaciones = FXCollections.observableArrayList();

        this.columnaPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        this.columnaAsistencias.setCellValueFactory(new PropertyValueFactory<>("asistencias"));
        this.columnaRebotes.setCellValueFactory(new PropertyValueFactory<>("rebotes"));
        this.columnaTapones.setCellValueFactory(new PropertyValueFactory<>("tapones"));


    }

}
