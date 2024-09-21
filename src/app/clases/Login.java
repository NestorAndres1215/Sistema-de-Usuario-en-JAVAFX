
package app.clases;

import app.configuracion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login {
   
    private String codigo;
    private String username;
    private String password;
    
     // Constructor
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Método para validar el login
    public boolean validateLogin() {
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE username = ? AND password = ?")) {
             
            statement.setString(1, username);
            statement.setString(2, password); // Considera usar hashing para la contraseña

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Si hay un resultado, el login es válido
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
