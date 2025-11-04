package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    private static Connection conexion;

    private ConexionBD() {}

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3307/censo_coahuila?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "1234"
                );
                System.out.println("Conexión establecida con la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("No se pudo conectar a la base de datos");
            }
        }
        return conexion;
    }
}
