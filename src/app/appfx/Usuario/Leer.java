/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.appfx.Usuario;
import app.clases.Usuario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Leer extends Application {
    private static Usuario usuarioSeleccionado; // Variable estática para almacenar el usuario
    // Constructor para pasar el usuario seleccionado a esta ventana
    public Leer(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Detalles del Usuario");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");

        // Crear etiquetas para mostrar los detalles del usuario
        Label lblCodigo = new Label("Código: " + usuarioSeleccionado.getCodigo());
        Label lblNombre = new Label("Nombre: " + usuarioSeleccionado.getNombre());
        Label lblApellido = new Label("Apellido: " + usuarioSeleccionado.getApellido());
        Label lblUsername = new Label("Usuario: " + usuarioSeleccionado.getUsername());
        Label lblCorreo = new Label("Correo: " + usuarioSeleccionado.getCorreo());
        Label lblTelefono = new Label("Teléfono: " + usuarioSeleccionado.getTelefono());
        Label lblDireccion = new Label("Dirección: " + usuarioSeleccionado.getDireccion());
        Label lblFechaNacimiento = new Label("Fecha de Nacimiento: " + usuarioSeleccionado.getFechaNacimiento());

        Button btnCerrar = new Button("Cerrar");
       
        btnCerrar.setOnAction(e -> primaryStage.close());

        vbox.getChildren().addAll(lblCodigo, lblNombre, lblApellido, lblUsername, lblCorreo, lblTelefono, lblDireccion, lblFechaNacimiento, btnCerrar);

        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
