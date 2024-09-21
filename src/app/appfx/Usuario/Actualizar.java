/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.appfx.Usuario;

import app.appfx.MenuAdmin;
import app.clases.Usuario;
import app.dao.UsuarioDAO;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class Actualizar extends Application {

    private Usuario usuarioSeleccionado;

    // Constructor para pasar el usuario seleccionado a esta ventana
    public Actualizar(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Actualizar Usuario");
        // Título
        Label titleLabel = new Label("Actualizar Usuario");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Crear un HBox para centrar el título
        HBox titleBox = new HBox(titleLabel);
        titleBox.setStyle("-fx-alignment: center;"); // Centrar el título
        // Crear el formulario
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Etiquetas y campos de texto para los datos del usuario
        // Nombre
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField(usuarioSeleccionado.getNombre());

        // Apellido
        Label lblApellido = new Label("Apellido:");
        TextField txtApellido = new TextField(usuarioSeleccionado.getApellido());

        // Usuario (Username)
        Label lblUsername = new Label("Usuario:");
        TextField txtUsername = new TextField(usuarioSeleccionado.getUsername());

        // Correo
        Label lblCorreo = new Label("Correo:");
        TextField txtCorreo = new TextField(usuarioSeleccionado.getCorreo());

        // Teléfono
        Label lblTelefono = new Label("Teléfono:");
        TextField txtTelefono = new TextField(usuarioSeleccionado.getTelefono());

        // Dirección
        Label lblDireccion = new Label("Dirección:");
        TextField txtDireccion = new TextField(usuarioSeleccionado.getDireccion());

        Label lblFechaNacimiento = new Label("Fecha de Nacimiento:");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.parse(usuarioSeleccionado.getFechaNacimiento())); // Convierte String a LocalDate

        // Contraseña (opcional, si deseas permitir que se pueda actualizar)
        Label lblPassword = new Label("Contraseña:");
        PasswordField txtPassword = new PasswordField();
// Agregar los elementos al GridPane
        gridPane.add(lblNombre, 0, 0);
        gridPane.add(txtNombre, 1, 0);
        gridPane.add(lblApellido, 0, 1);
        gridPane.add(txtApellido, 1, 1);
        gridPane.add(lblUsername, 0, 2);
        gridPane.add(txtUsername, 1, 2);
        gridPane.add(lblCorreo, 0, 3);
        gridPane.add(txtCorreo, 1, 3);
        gridPane.add(lblTelefono, 0, 4);
        gridPane.add(txtTelefono, 1, 4);
        gridPane.add(lblDireccion, 0, 5);
        gridPane.add(txtDireccion, 1, 5);
        gridPane.add(lblFechaNacimiento, 0, 6);
        gridPane.add(datePicker, 1, 6);
        gridPane.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");
// Botón para guardar cambios
        Button btnGuardar = new Button("Guardar Cambios");
        gridPane.add(btnGuardar, 1, 7);

// Evento para guardar los cambios
        btnGuardar.setOnAction(e -> {
            // Actualizar los datos del usuario seleccionado
            usuarioSeleccionado.setNombre(txtNombre.getText());
            usuarioSeleccionado.setApellido(txtApellido.getText());
            usuarioSeleccionado.setUsername(txtUsername.getText());
            usuarioSeleccionado.setCorreo(txtCorreo.getText());
            usuarioSeleccionado.setTelefono(txtTelefono.getText());
            usuarioSeleccionado.setDireccion(txtDireccion.getText());
            usuarioSeleccionado.setFechaNacimiento(datePicker.getValue().toString()); // Obtiene la fecha del DatePicker

            // Llamar al DAO para actualizar en la base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.actualizarUsuario(usuarioSeleccionado);

            // Mostrar un mensaje o cerrar la ventana
            System.out.println("Usuario actualizado exitosamente.");
            primaryStage.close(); // Cierra la ventana después de actualizar
        });
        // Configurar la escena y mostrarla
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
