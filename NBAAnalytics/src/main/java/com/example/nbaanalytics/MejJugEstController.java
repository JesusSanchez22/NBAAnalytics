package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MejJugEstController implements Initializable{

    private String[] posiciones = {"anotador","asistente","rebotador","taponador"};


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
            case "anotador":

                ResultSet mejorAnotador = stmt.executeQuery("Select Nombre from mejores_jugadores inner join jugadores \n" +
                        "on jugadores.codigo = mejores_jugadores.Mejor_anotador order by mejor_anotador desc limit 1;");


                if (mejorAnotador.next()) {
                    String nombreMejorPivot = mejorAnotador.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }


                mejorAnotador.close();

                break;

            case "asistente":

                ResultSet mejorAsistente = stmt.executeQuery("Select Nombre from mejores_jugadores inner join jugadores \n" +
                        "on jugadores.codigo = mejores_jugadores.Mejor_asistente order by Mejor_asistente desc limit 1;");


                if (mejorAsistente.next()) {
                    String nombreMejorPivot = mejorAsistente.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorAsistente.close();
                break;

            case "rebotador":

                ResultSet mejorRebotador = stmt.executeQuery("Select Nombre from mejores_jugadores inner join jugadores \n" +
                        "on jugadores.codigo = mejores_jugadores.Mejor_rebotador order by Mejor_rebotador desc limit 1;");


                if (mejorRebotador.next()) {
                    String nombreMejorPivot = mejorRebotador.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorRebotador.close();

                break;

            case "taponador":
                ResultSet mejorTaponador = stmt.executeQuery("Select Nombre from mejores_jugadores inner join jugadores \n" +
                        "on jugadores.codigo = mejores_jugadores.Mejor_taponeador order by Mejor_taponeador desc limit 1;");


                if (mejorTaponador.next()) {
                    String nombreMejorPivot = mejorTaponador.getString("Nombre");
                    txtResultadoJugador.setText(nombreMejorPivot);
                }

                mejorTaponador.close();
                break;


        }




    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPosiciones.getItems().addAll(posiciones);

        cbPosiciones.setValue("Elige estadística");



    }

}
