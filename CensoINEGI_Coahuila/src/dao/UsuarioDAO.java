package dao;

import modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public void insertar(Usuario usuario) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO usuarios (usuario, password, nombre, apellidos) VALUES (?, ?, ?, ?)");
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellidos());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validarUsuario(String usuario, String password) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}