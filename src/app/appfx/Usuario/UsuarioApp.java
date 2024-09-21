/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.appfx.Usuario;

import app.clases.Usuario;
import app.dao.UsuarioDAO;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author Usuario
 */
public class UsuarioApp extends Application {

@Override
public void start(Stage primaryStage) {
    // Crear una TableView para mostrar usuarios
    TableView<Usuario> tableView = new TableView<>();

    // Definir las columnas
    TableColumn<Usuario, String> codigoCol = new TableColumn<>("Código");
    codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));

    TableColumn<Usuario, String> nombreCol = new TableColumn<>("Nombre");
    nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

    TableColumn<Usuario, String> apellidoCol = new TableColumn<>("Apellido");
    apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));

    // Agregar columnas a la TableView
    tableView.getColumns().addAll(codigoCol, nombreCol, apellidoCol);

    // Obtener usuarios desde el DAO
    UsuarioDAO usuarioDAO = new UsuarioDAO(); // Asegúrate de instanciar tu DAO
    List<Usuario> usuarios = usuarioDAO.getAllUsuarios();

    // Añadir los usuarios a la TableView
    tableView.getItems().addAll(usuarios);

    // Configurar y mostrar la TableView en un VBox
    VBox vbox = new VBox(tableView);
    Scene scene = new Scene(vbox);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Listado de Usuarios");
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }


}
