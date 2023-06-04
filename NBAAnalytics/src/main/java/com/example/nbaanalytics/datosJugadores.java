package com.example.nbaanalytics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class datosJugadores implements Initializable {

    @FXML
    private TableColumn<JugadorData, String> columnaAltura;

    @FXML
    private TableColumn<JugadorData, Integer> columnaCodigo;

    @FXML
    private TableColumn<JugadorData, String> columnaEquipo;

    @FXML
    private TableColumn<JugadorData, String> columnaNombre;

    @FXML
    private TableColumn<JugadorData, Integer> columnaPeso;

    @FXML
    private TableColumn<JugadorData, String> columnaPosicion;

    @FXML
    private TableColumn<JugadorData, String> columnaProcedencia;

    @FXML
    private TableView<JugadorData> tablaInfo;

    private ObservableList<JugadorData> infoJugadores;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoJugadores = FXCollections.observableArrayList();
        this.columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.columnaProcedencia.setCellValueFactory(new PropertyValueFactory<>("Procedencia"));
        this.columnaAltura.setCellValueFactory(new PropertyValueFactory<>("Altura"));
        this.columnaPeso.setCellValueFactory(new PropertyValueFactory<>("Peso"));
        this.columnaPosicion.setCellValueFactory(new PropertyValueFactory<>("Posicion"));
        this.columnaEquipo.setCellValueFactory(new PropertyValueFactory<>("Nombre_equipo"));

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql =  "Select * from jugadores";
            ResultSet rs = stmt.executeQuery(sql);

            do {
                try{
                    int codigo = rs.getInt("codigo");
                    String nombre = rs.getString("Nombre");
                    String procedencia = rs.getString("Procedencia");
                    String altura = rs.getString("Altura");
                    int peso = rs.getInt("Peso");
                    String posicion = rs.getString("Posicion");
                    String equipo = rs.getString("Nombre_equipo");


                    JugadorData jugador1 = new JugadorData(codigo,nombre,procedencia,altura,peso,posicion,equipo);
                    infoJugadores.add(jugador1);
                    tablaInfo.setItems(infoJugadores);
                }catch(SQLException e){}


            }while(rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
