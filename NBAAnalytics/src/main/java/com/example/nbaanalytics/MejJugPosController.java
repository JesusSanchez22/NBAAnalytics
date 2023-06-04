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

    @FXML
    private TextField txtTemporada;

    private ObservableList<Jugador> puntuaciones;

    @FXML
    void mostrar(ActionEvent event) throws SQLException {

        puntuaciones.clear();

        String posicion = cbPosiciones.getValue();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String sql = "Select temporada,nombre,\n" +
                "    round((Puntos_por_partido + Asistencias_por_partido + Tapones_por_partido + Rebotes_por_partido)) as puntuacion\n" +
                "    from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo \n" +
                "    where posicion='"+ posicion + "' order by puntuacion desc limit 1;";

        ResultSet rs = stmt.executeQuery(sql);

        String nombreMejorJugador = "";
        String temporada = "";

        if(rs.next()){
            nombreMejorJugador = rs.getString("Nombre");
            temporada = rs.getString("temporada");
            txtResultadoJugador.setText(nombreMejorJugador);
            txtTemporada.setText(temporada);
        }

        String estadisticasQuery = "Select puntos_por_partido, asistencias_por_partido, tapones_por_partido, rebotes_por_partido \n" +
                "    from estadisticas inner join jugadores on estadisticas.jugador=jugadores.codigo \n" +
                "    where nombre='" + nombreMejorJugador + "' and temporada='" + temporada + "';";

        ResultSet estadisticasRS = stmt.executeQuery(estadisticasQuery);

        if (estadisticasRS.next()){
            int puntos = estadisticasRS.getInt("puntos_por_partido");
            int asistencias = estadisticasRS.getInt("asistencias_por_partido");
            int tapones = estadisticasRS.getInt("tapones_por_partido");
            int rebotes = estadisticasRS.getInt("rebotes_por_partido");

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
