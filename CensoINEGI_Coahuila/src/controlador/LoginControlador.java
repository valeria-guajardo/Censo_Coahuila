package controlador;

import modelo.Sesion;
import java.sql.*;
import dao.ConexionBD; 

public class LoginControlador {

    public boolean autenticar(String usuario, String password) {
        try {
            Connection conn = ConexionBD.getConexion(); // aquí está el cambio
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM usuarios WHERE usuario = ? AND password = ?"
            );
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Sesion.usuario = rs.getString("usuario");
                Sesion.rol = rs.getString("rol");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}