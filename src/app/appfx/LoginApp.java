/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.appfx;

import app.appfx.MenuAdmin;
import app.appfx.Usuario.Registrar;
import app.appfx.Usuario.Registrar;
import app.clases.Login;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Campos para el login
        Label usernameLabel = new Label("Usuario:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Iniciar Sesión");
        // Botón de registro
        Button registerButton = new Button("Registrar");

        registerButton.setOnAction(e -> {
            registrar();  // Llama al método registrar
            primaryStage.close();  // Cierra la ventana actual
        });

        // Manejo del evento de clic en el botón de login
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Login user = new Login(username, password); // Crear un objeto User

            if (user.validateLogin()) {
                openMainApp();
                primaryStage.close(); // Cierra la ventana de login
            } else {
                showError("Usuario o contraseña incorrectos.");
            }
        });

        VBox layout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton);
        layout.setPrefWidth(300);
        layout.setPrefHeight(200);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");

        Scene scene = new Scene(layout);
        primaryStage.setTitle("Iniciar Sesión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para mostrar un mensaje de error
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error al Ingresar al Sistema");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openMainApp() {
        MenuAdmin menuAdmin = new MenuAdmin();
        Stage menuStage = new Stage();
        try {
            menuAdmin.start(menuStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registrar() {

        Registrar regForm = new Registrar();
        Stage regStage = new Stage();

        try {
            regForm.start(regStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
