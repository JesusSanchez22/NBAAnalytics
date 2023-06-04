package com.example.nbaanalytics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MejJugEstController implements Initializable{

    private String[] posiciones = {"Mejor_anotador","Mejor_asistente","Mejor_rebotador","Mejor_taponeador"};


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
    private TableColumn<Jugador, String> columnaTemporada;

    @FXML
    private TableColumn<Jugador, String> columnaNombre;
    private ObservableList<Jugador> puntuaciones;


    @FXML
    void mostrar(ActionEvent event) throws SQLException {

        puntuaciones.clear();

        String posicion = cbPosiciones.getValue();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


        String estadisticasQuery = "Select temporada, (Select nombre from jugadores where codigo = " + posicion + ") as nombre, round(sum(Puntos_por_partido)) as puntos, \n" +
                "                round(sum(Asistencias_por_partido)) as asistencias, round(sum(Tapones_por_partido))\n" +
                "\t\t\t\tas tapones, round(sum(Rebotes_por_partido)) as rebotes\n" +
                " from estadisticas natural join mejores_jugadores " +
                "where jugador = " + posicion + " " +
                "group by Temporada," + posicion + ";";



        ResultSet estadisticasRS = stmt.executeQuery(estadisticasQuery);


        do {
            try{
                String temporada = estadisticasRS.getString("temporada");
                String nombre = estadisticasRS.getString("nombre");
                int puntos = estadisticasRS.getInt("puntos");
                int asistencias = estadisticasRS.getInt("asistencias");
                int tapones = estadisticasRS.getInt("tapones");
                int rebotes = estadisticasRS.getInt("rebotes");

                Jugador jugador = new Jugador(temporada,nombre,puntos,asistencias,tapones,rebotes);
                puntuaciones.add(jugador);
            }catch(SQLException e){

            }
        }while(estadisticasRS.next());

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPosiciones.getItems().addAll(posiciones);

        cbPosiciones.setValue("Elige estadística");


        puntuaciones = FXCollections.observableArrayList();

        this.columnaTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));
        this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.columnaPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        this.columnaAsistencias.setCellValueFactory(new PropertyValueFactory<>("asistencias"));
        this.columnaRebotes.setCellValueFactory(new PropertyValueFactory<>("rebotes"));
        this.columnaTapones.setCellValueFactory(new PropertyValueFactory<>("tapones"));

        tablaEstadisticas.setItems(puntuaciones);

    }

}
