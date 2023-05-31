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

        String sql = "Select * from estadisticas inner join jugadores " +
                "on estadisticas.jugador = jugadores.codigo where Posicion ='" + posicion + "'";

        ResultSet rs = stmt.executeQuery(sql);

        if(rs.next()){
            String nombreMejorJugador = rs.getString("Nombre");
            txtResultadoJugador.setText(nombreMejorJugador);
        }

        String estadisticasQuery = "Select round(sum(Puntos_por_partido)) as puntos, round(sum(Asistencias_por_partido)) as asistencias, round(sum(Tapones_por_partido))\n" +
                "as tapones, round(sum(Rebotes_por_partido)) as rebotes\n" +
                " from estadisticas where jugador = (select jugadores.codigo\n" +
                " from jugadores inner join estadisticas on estadisticas.jugador=jugadores.codigo where Posicion = '" + posicion + "'" +
                " group by jugadores.codigo order by round(sum(Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)/4,2) \n" +
                " desc limit 1);";

        ResultSet estadisticasRS = stmt.executeQuery(estadisticasQuery);

        if (estadisticasRS.next()){
            int puntos = estadisticasRS.getInt("puntos");
            int asistencias = estadisticasRS.getInt("asistencias");
            int tapones = estadisticasRS.getInt("tapones");
            int rebotes = estadisticasRS.getInt("rebotes");

            Jugador jugador = new Jugador(puntos,asistencias,tapones,rebotes);
            puntuaciones.clear();
            puntuaciones.add(jugador);
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

        tablaEstadisticas.setItems(puntuaciones);


    }

}
