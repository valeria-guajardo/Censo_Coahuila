package vista;

import dao.ConexionBD;
import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        Connection conn = ConexionBD.getConexion();
        if (conn != null) {
            System.out.println("La conexión está activa");
        } else {
            System.out.println("No se pudo establecer la conexión");
        }
    }
}