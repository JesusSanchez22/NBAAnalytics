package com.example.nbaanalytics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class VentanaEquiposController implements Initializable {

    @FXML
    private Button btnImprimir;


    @FXML
    private TableColumn<Equipo, String> columnaCiudad;

    @FXML
    private TableColumn<Equipo, String> columnaConferencia;

    @FXML
    private TableColumn<Equipo, String> columnaDivision;

    @FXML
    private TableColumn<Equipo, String> columnaNombre;

    @FXML
    private TableView<Equipo> tablaInfo;

    @FXML
    private TextField txtRuta;

    private ObservableList<Equipo> infoEquipos;

    @FXML
    void imprimir(ActionEvent event) throws IOException, SQLException {

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql =  "Select * from equipos order by Conferencia";
        ResultSet rs = stmt.executeQuery(sql);
        String ruta = txtRuta.getText();

        if (ruta.isEmpty()){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error 404");
                alert.setContentText("La ruta está vacía o es incorrecta");
                alert.showAndWait();

        }else{
            File f = new File(ruta);

            FileWriter writer = new FileWriter(f);

            for (Equipo equipo : infoEquipos) {
                writer.write(equipo.getNombre() + " " + equipo.getCiudad() +
                        " " + equipo.getConferencia() + " " + equipo.getDivision() +
                        "\n");
            }

            writer.close();
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        infoEquipos = FXCollections.observableArrayList();
        this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.columnaCiudad.setCellValueFactory(new PropertyValueFactory<>("Ciudad"));
        this.columnaConferencia.setCellValueFactory(new PropertyValueFactory<>("Conferencia"));
        this.columnaDivision.setCellValueFactory(new PropertyValueFactory<>("Division"));



        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql =  "Select * from equipos order by Conferencia";
            ResultSet rs = stmt.executeQuery(sql);

            do {
                try{
                    String nombre = rs.getString("Nombre");
                    String ciudad = rs.getString("Ciudad");
                    String conferencia = rs.getString("Conferencia");
                    String division = rs.getString("Division");

                    Equipo equipo1 = new Equipo(nombre,ciudad,conferencia,division);
                    infoEquipos.add(equipo1);
                    tablaInfo.setItems(infoEquipos);
                }catch(SQLException e){}


            }while(rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
