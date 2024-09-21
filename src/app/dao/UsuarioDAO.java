/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import app.clases.Usuario;
import app.configuracion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (codigo, nombre, apellido, username, password, correo, telefono, direccion, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getCodigo());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getUsername());
            pstmt.setString(5, usuario.getPassword()); // Asegúrate de hashear la contraseña antes
            pstmt.setString(6, usuario.getCorreo());
            pstmt.setString(7, usuario.getTelefono());
            pstmt.setString(8, usuario.getDireccion());
            pstmt.setString(9, usuario.getFechaNacimiento());

            pstmt.executeUpdate();
            System.out.println("Usuario registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public String obtenerUltimoCodigo() {
        String sql = "SELECT codigo FROM usuarios ORDER BY codigo DESC LIMIT 1"; // Asegúrate de que 'codigo' sea único

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el último código: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra ningún registro
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al comprobar si el usuario existe: " + e.getMessage());
        }
        return false; // Retorna false si no existe
    }

    public boolean telefonoExiste(String telefono) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE telefono = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, telefono);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al comprobar si el telefono existe: " + e.getMessage());
        }
        return false; // Retorna false si no existe
    }

    public boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al comprobar si el correo existe: " + e.getMessage());
        }
        return false; // Retorna false si no existe
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Crear un objeto Usuario y añadirlo a la lista
                Usuario usuario = new Usuario(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"), // Recuerda no exponer la contraseña
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("fecha_nacimiento")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }

        return usuarios; // Retorna la lista de usuarios
    }

    public void eliminarUsuario(String codigo) {
        String sql = "DELETE FROM usuarios WHERE codigo = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public void actualizarUsuario(Usuario usuarioSeleccionado) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, username = ?, correo = ?, telefono = ?, direccion = ?, fecha_nacimiento = ? WHERE codigo = ?";

        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Establecer los valores para la consulta
            pstmt.setString(1, usuarioSeleccionado.getNombre());
            pstmt.setString(2, usuarioSeleccionado.getApellido());
            pstmt.setString(3, usuarioSeleccionado.getUsername());
            pstmt.setString(4, usuarioSeleccionado.getCorreo());
            pstmt.setString(5, usuarioSeleccionado.getTelefono());
            pstmt.setString(6, usuarioSeleccionado.getDireccion());
            pstmt.setString(7, usuarioSeleccionado.getFechaNacimiento());
            pstmt.setString(8, usuarioSeleccionado.getCodigo()); // Se actualiza en base al código

            // Ejecutar la actualización
            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Usuario actualizado exitosamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        } 

    
        
    }

}
