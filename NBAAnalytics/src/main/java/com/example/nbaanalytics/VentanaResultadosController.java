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

public class VentanaResultadosController implements Initializable {
    private String[] temporadas = {"98/99","99/00","00/01","01/02","02/03","03/04","04/05","05/06","06/07","07/08"};

    @FXML
    private Button btnMostrar;

    @FXML
    private ComboBox<String> cbTemporadas;

    @FXML
    private TableColumn<Partido,Integer> columnaCod;

    @FXML
    private TableColumn<Partido,String> columnaLocal;

    @FXML
    private TableColumn<Partido,Integer> columnaPtsLocal;

    @FXML
    private TableColumn<Partido,String> columnaTemporada;

    @FXML
    private TableColumn<Partido,String> columnaVisitante;

    @FXML
    private TableColumn<Partido,String> columnaPtsVisitante;

    @FXML
    private TableView<Partido> tablaPartidos;

    @FXML
    private TextField txtEquipoLocal;

    @FXML
    private TextField txtEquipoVisitante;

    private ObservableList<Partido> partidos;


    @FXML
    void mostrar(ActionEvent event) throws SQLException {

        partidos.clear();

        String posicion = cbTemporadas.getValue();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String equipo_local_elegido= txtEquipoLocal.getText();
        String equipo_visitante_elegido= txtEquipoVisitante.getText();
        String temporada_elegida = cbTemporadas.getValue();


        String estadisticasQuery = "Select * from partidos where equipo_local= '" + equipo_local_elegido  +
                "' and equipo_visitante= '"+ equipo_visitante_elegido+  "' and temporada = " +
                "'" + temporada_elegida + "'";


        ResultSet estadisticasRS = stmt.executeQuery(estadisticasQuery);


        do {
            try{

                String equipo_local = estadisticasRS.getString("equipo_local");
                String equipo_visitante = estadisticasRS.getString("equipo_visitante");
                int puntos_local = estadisticasRS.getInt("puntos_local");
                int puntos_visitante = estadisticasRS.getInt("puntos_visitante");



                Partido partido1 = new Partido(equipo_local,equipo_visitante,puntos_local,
                        puntos_visitante);
                partidos.add(partido1);
            }catch(SQLException e){

            }
        }while(estadisticasRS.next());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbTemporadas.getItems().addAll(temporadas);

        cbTemporadas.setValue("Elige la temporada");


        partidos = FXCollections.observableArrayList();

        this.columnaLocal.setCellValueFactory(new PropertyValueFactory<>("equipo_local"));
        this.columnaVisitante.setCellValueFactory(new PropertyValueFactory<>("equipo_visitante"));
        this.columnaPtsLocal.setCellValueFactory(new PropertyValueFactory<>("puntos_local"));
        this.columnaPtsVisitante.setCellValueFactory(new PropertyValueFactory<>("puntos_visitante"));


        tablaPartidos.setItems(partidos);
    }
}
