package app.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestion_empleados?serverTimezone=UTC";
    private static final String DB_USER = "root"; // Cambia esto
    private static final String DB_PASSWORD = "12345"; // Cambia esto

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void main(String[] args) {
        try {
            // Intenta obtener una conexión
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
                connection.close(); // Cierra la conexión después de la prueba
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
