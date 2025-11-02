package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    private static Connection conexion;

    private ConexionBD() {
        // Constructor privado para evitar instanciación externa
    }

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/censo_coahuila", "root", "@Zelev2311");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }
}