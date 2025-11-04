package controlador;

import modelo.Sesion;
import java.sql.*;
import dao.ConexionBD;

public class LoginControlador {

    public boolean autenticar(String usuario, String password) {
        if (usuario == null || password == null) {
            System.out.println("Usuario o contraseña son null");
            return false;
        }

        // Quitamos espacios al inicio/final
        usuario = usuario.trim();
        password = password.trim();

        System.out.println("? Intentando login con:");
        System.out.println("Usuario: [" + usuario + "]");
        System.out.println("Password: [" + password + "]");

        try {
            Connection conn = ConexionBD.getConexion();

            if (conn == null) {
                System.out.println("Conexión a la base de datos es null");
                return false;
            }

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT usuario, password, rol FROM usuarios WHERE usuario = ? AND password = ?"
            );
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Sesion.usuario = rs.getString("usuario");
                Sesion.rol = rs.getString("rol");
                System.out.println("Login exitoso. Rol: " + Sesion.rol);
                return true;
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }

        } catch (Exception e) {
            System.out.println("Error en LoginControlador:");
            e.printStackTrace();
        }

        return false;
    }
}
