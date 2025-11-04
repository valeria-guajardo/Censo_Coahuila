package test;

import dao.ConexionBD;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection conn = ConexionBD.getConexion();
        if (conn != null) {
            System.out.println("Conexión exitosa a MySQL");
        } else {
            System.out.println("No se pudo conectar a la base de datos");
        }
    }
}
