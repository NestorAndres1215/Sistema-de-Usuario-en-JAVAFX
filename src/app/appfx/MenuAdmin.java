/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.appfx;

import app.appfx.Usuario.Actualizar;
import app.appfx.Usuario.Leer;
import app.clases.Usuario;
import app.dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuAdmin extends Application {

    private Stage primaryStage; // Declarar la variable primaryStage como un campo de instancia

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Inicializar primaryStage
        primaryStage.setTitle("Menú Principal");

        // Crear un BorderPane
        BorderPane borderPane = new BorderPane();

        // Crear menubar
        MenuBar menuBar = new MenuBar();
        Menu menuPrincipal = new Menu("Principal");

        // Menú de Usuarios
        Menu menuUsuarios = new Menu("Usuarios");
        MenuItem listarUsuariosItem = new MenuItem("Listar Usuarios");
        listarUsuariosItem.setOnAction(e -> mostrarListadoUsuarios()); // Acción para listar usuarios
        menuUsuarios.getItems().add(listarUsuariosItem);

        // Agregar menús a la barra de menú
        menuBar.getMenus().addAll(menuPrincipal, menuUsuarios);
        borderPane.setTop(menuBar);

        // Crear un VBox para el contenido principal
        VBox vBox = new VBox();

        try {
            Image image = new Image("file:D:/NetBeansProjects/AppAlumnos/src/app/img/img.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(1000); // Ajustar el tamaño de la imagen
            imageView.setFitHeight(1200);
            imageView.setPreserveRatio(true);

            // Añadir imageView al VBox
            vBox.getChildren().add(imageView);
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar el error si la imagen no se carga
        }

        borderPane.setCenter(vBox);

        // Crear la escena y mostrarla
        Scene scene = new Scene(borderPane, 1000, 500, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void mostrarListadoUsuarios() {
        // Crear un VBox para contener el título, la tabla y los botones
        VBox vBox = new VBox();
        vBox.setSpacing(10); // Espaciado entre los elementos

        // Crear el título
        Label titleLabel = new Label("Listado de Usuarios");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Crear una TableView para mostrar usuarios
        TableView<Usuario> tableView = new TableView<>();

        // Definir las columnas
        TableColumn<Usuario, String> codigoCol = new TableColumn<>("Código");
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Usuario, String> userCol = new TableColumn<>("Usuario");
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Usuario, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Usuario, String> apellidoCol = new TableColumn<>("Apellido");
        apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        TableColumn<Usuario, String> correoCol = new TableColumn<>("Correo");
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Usuario, String> telefonoCol = new TableColumn<>("Telefono");
        telefonoCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Usuario, String> direccionCol = new TableColumn<>("Direccion");
        direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Usuario, String> fechaCol = new TableColumn<>("Fecha de Nacimiento");
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        // Agregar columnas a la TableView
        tableView.getColumns().addAll(codigoCol, userCol, nombreCol, apellidoCol, correoCol, telefonoCol, direccionCol, fechaCol);

        // Obtener usuarios desde el DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();

        // Añadir los usuarios a la TableView
        tableView.getItems().addAll(usuarios);

        // Crear los botones de "Actualizar", "Leer" y "Eliminar"
        Button btnActualizar = new Button("Actualizar");
        Button btnLeer = new Button("Leer");
        Button btnEliminar = new Button("Eliminar");

        btnActualizar.setOnAction(e -> {
            Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();
            if (usuarioSeleccionado != null) {
                Actualizar actualizarForm = new Actualizar(usuarioSeleccionado); // Pasa la referencia
                Stage actualizarStage = new Stage();
                actualizarForm.start(actualizarStage);
            } else {
                // Mostrar un mensaje de error si no hay usuario seleccionado
                showError("Selecciona un usuario para actualizar.");
            }
        });

        btnLeer.setOnAction(e -> {
            Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();
            if (usuarioSeleccionado != null) {
             Leer actualizarForm = new Leer(usuarioSeleccionado); // Pasa la referencia
                Stage actualizarStage = new Stage();
                actualizarForm.start(actualizarStage); // Usar el método estático
            } else {
                showError("Por favor, selecciona un usuario para leer.");
            }
        });

        btnEliminar.setOnAction(e -> {
            // Lógica para eliminar el usuario seleccionado
            Usuario selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Implementa la lógica para eliminar
                usuarioDAO.eliminarUsuario(selectedUser.getCodigo());
                tableView.getItems().remove(selectedUser); // Remover de la tabla
                System.out.println("Usuario eliminado: " + selectedUser.getUsername());
                showAlert("Usuario eliminado: " + selectedUser.getUsername());
            } else {
                showAlert("Selecciona un usuario para eliminar.");
            }
        });

        // Crear un HBox para contener los botones
        HBox buttonBox = new HBox(10); // Espaciado de 10 entre los botones
        buttonBox.getChildren().addAll(btnActualizar, btnLeer, btnEliminar);

        // Añadir el título, la tabla y los botones al VBox
        vBox.getChildren().addAll(titleLabel, tableView, buttonBox);

        // Reemplazar el centro del BorderPane con el VBox
        BorderPane rootPane = (BorderPane) this.primaryStage.getScene().getRoot(); // Obtener el root
        rootPane.setCenter(vBox); // Colocar el VBox en el centro
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String selecciona_un_usuario_para_actualizar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
