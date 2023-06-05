package com.example.nbaanalytics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;


public class VentanaRegisterController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void logear(ActionEvent event) throws SQLException {

        String nombre = txtUser.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Por favor, ingrese un nombre de usuario y una contraseña válidos");
            alert.showAndWait();
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT * FROM usuarios";

            ResultSet rs = stmt.executeQuery(sql);

            boolean usuarioExistente = false;

            while (rs.next()) {
                String siguienteNombre = rs.getString("Nombre");

                if (nombre.equals(siguienteNombre)) {
                    usuarioExistente = true;
                    break;
                }
            }

            if (usuarioExistente) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setContentText("Este usuario ya existe");
                alert.showAndWait();
            } else {
                rs.moveToInsertRow();
                rs.updateString("Nombre", nombre);
                rs.updateString("Contraseña", password);
                rs.insertRow();

                stmt.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("REGISTRADO");
                alert.setContentText("Tu usuario se ha registrado correctamente");
                alert.showAndWait();
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de restricción de integridad");
            alert.setContentText("Ya existe un usuario con el mismo nombre");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}





