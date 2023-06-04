package com.example.nbaanalytics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class VentanaLoginController {



    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void logear(ActionEvent event) {

        String nombre = txtUser.getText();
        String password = txtPassword.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba?serverTimezone=UTC", "root", "toor");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT * FROM usuarios";

            ResultSet rs = stmt.executeQuery(sql);

            boolean usuarioExistente = false;

            if (nombre.equals("admin") && password.equals("admin")){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adminIndexVentana.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }else {

                while (rs.next()) {
                    String siguienteNombre = rs.getString("Nombre");

                    if (nombre.equals(siguienteNombre)) {
                        usuarioExistente = true;
                        break;
                    }
                }

                if (usuarioExistente) {

                    ResultSet rs2 = stmt.executeQuery("SELECT Contraseña FROM usuarios WHERE Nombre='" + nombre + "'");
                    if (rs2.next()) {
                        String comprobarContraseña = rs2.getString("Contraseña");

                        if (password.equals(comprobarContraseña)) {

                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IndexVentana.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error 404");
                            alert.setContentText("La contraseña es incorrecta");
                            alert.showAndWait();
                        }
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error 404");
                    alert.setContentText("Este usuario no existe");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
        } catch (IOException e) {
        }
    }

    @FXML
    void registrar(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterVentana.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
        }
    }

}
