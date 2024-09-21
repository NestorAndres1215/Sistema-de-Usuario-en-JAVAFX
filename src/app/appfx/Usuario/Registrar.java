package app.appfx.Usuario;

import app.clases.Usuario;
import app.dao.UsuarioDAO;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Registrar extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registrar Usuario");

        // Crear el layout en un GridPane
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Título
        Label titleLabel = new Label("Registrar Usuario");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Crear un HBox para centrar el título
        HBox titleBox = new HBox(titleLabel);
        titleBox.setStyle("-fx-alignment: center;"); // Centrar el título

        // Crear los campos de entrada
        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label apellidoLabel = new Label("Apellido:");
        TextField apellidoField = new TextField();

        Label usernameLabel = new Label("Nombre de Usuario:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Label correoLabel = new Label("Correo:");
        TextField correoField = new TextField();

        Label telefonoLabel = new Label("Teléfono:");
        TextField telefonoField = new TextField();

        Label direccionLabel = new Label("Dirección:");
        TextField direccionField = new TextField();
        Label fechaNacimientoLabel = new Label("Fecha de Nacimiento:");
        DatePicker fechaNacimientoPicker = new DatePicker(); // Campo de fecha
        Button registerButton = new Button("Registrar");

        // Agregar componentes al GridPane
        grid.add(titleBox, 0, 0, 2, 1); // Título ocupa 2 columnas
        grid.add(nombreLabel, 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(apellidoLabel, 0, 2);
        grid.add(apellidoField, 1, 2);
        grid.add(usernameLabel, 0, 3);
        grid.add(usernameField, 1, 3);
        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(correoLabel, 0, 5);
        grid.add(correoField, 1, 5);
        grid.add(telefonoLabel, 0, 6);
        grid.add(telefonoField, 1, 6);
        grid.add(direccionLabel, 0, 7);
        grid.add(direccionField, 1, 7);
        grid.add(fechaNacimientoLabel, 0, 8);
        grid.add(fechaNacimientoPicker, 1, 8);
        grid.add(registerButton, 1, 9); // Botón de registrar en la segunda columna

        // Configura las restricciones de columna
        ColumnConstraints col1 = new ColumnConstraints(150); // Ancho de la columna izquierda
        ColumnConstraints col2 = new ColumnConstraints(200); // Ancho de la columna derecha
        grid.getColumnConstraints().addAll(col1, col2);
        grid.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 10;");

        // Acción del botón de registrar
        registerButton.setOnAction(e -> {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText(); // Hashear la contraseña antes de guardarla
            String correo = correoField.getText();
            String telefono = telefonoField.getText();
            String direccion = direccionField.getText();
            String fechaNacimiento = fechaNacimientoPicker.getValue() != null ? fechaNacimientoPicker.getValue().toString() : "";
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String codigo = incrementarPrefijo(usuarioDAO.obtenerUltimoCodigo());
            if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || password.isEmpty()
                    || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || fechaNacimiento.isEmpty()) {
                showError("Todos los campos son obligatorios. Por favor, completa la información.");
                return; // Salir si hay campos vacíos
            }
            if (usuarioDAO.usernameExists(username)) {
                showError("El nombre de usuario ya está en uso. Por favor, elige otro.");
                return; // Salir del método si el usuario ya existe
            }
            if (usuarioDAO.telefonoExiste(telefono)) {
                showError("Por favor, introduce un correo electrónico válido.");
                return; // Salir si el correo no es válido
            }
            if (!isValidEmail(correo)) {
                showError("Por favor, introduce un correo electrónico válido.");
                return; // Salir si el correo no es válido
            }
            if (usuarioDAO.correoExiste(correo)) {
                showError("El correo de usuario ya está en uso. Por favor, elige otro.");
                return; // Salir del método si el usuario ya existe
            }
            if (!validar9digitos(telefono)) {
                showError("El telefono debe tener 9 digitos");
                return;
            }
            if (!isAdult(fechaNacimiento)) {
                showError("Debes ser mayor de 18 años para registrarte.");
                return; // Salir si no es mayor de edad
            }
            // Crear un nuevo usuario
            Usuario nuevoUsuario = new Usuario(codigo, nombre, apellido, username, password, correo, telefono, direccion, fechaNacimiento);

            // Registrar en la base de datos
            usuarioDAO.registrarUsuario(nuevoUsuario);

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado: " + nombre + " " + apellido);
            alert.showAndWait();
            nombreField.clear();
            apellidoField.clear();
            usernameField.clear();
            passwordField.clear(); // Considera limpiar solo el campo si es necesario
            correoField.clear();
            telefonoField.clear();
            direccionField.clear();
            fechaNacimientoPicker.setValue(null); // Limpia el selector de fecha
        });

        // Crear la escena y mostrarla
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static String incrementarPrefijo(String prefix) {
        char[] chars = prefix.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == 'Z') {
                chars[i] = 'A'; // Si es Z, lo convierto a A
            } else {
                chars[i]++; // Incremento el carácter
                break; // Rompo el bucle si no es Z
            }
        }
        return new String(chars);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validar9digitos(String phone) {
        return phone.length() == 9 && phone.matches("\\d{9}"); // 9 dígitos
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isAdult(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            return false; // Si no hay fecha, no se puede verificar
        }

        // Convertir el String a LocalDate
        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(fechaNacimiento); // Asegúrate de que el formato de fecha sea correcto
        } catch (DateTimeParseException e) {
            return false; // Retorna false si la fecha no es válida
        }

        LocalDate now = LocalDate.now(); // Obtiene la fecha actual
        int age = Period.between(fechaNac, now).getYears(); // Calcula la edad en años

        return age >= 18; // Retorna true si tiene 18 años o más
    }

}
