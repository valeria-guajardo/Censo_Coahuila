package dao;

import modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario validarUsuario(String usuario, String password) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
            stmt.setString(1, usuario.trim());
            stmt.setString(2, password.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellidos")
                );
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertar(Usuario usuario) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO usuarios (usuario, password, nombre, apellidos, rol) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellidos());
            stmt.setString(5, usuario.getRol());
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}