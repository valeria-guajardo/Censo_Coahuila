package dao;

import modelo.ActividadEconomica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadEconomicaDAO {

    public void insertar(ActividadEconomica ae) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO actividades_economicas (nombre) VALUES (?)"
            );
            ps.setString(1, ae.getNombre());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ActividadEconomica> listar() {
        List<ActividadEconomica> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM actividades_economicas");
            while (rs.next()) {
                ActividadEconomica ae = new ActividadEconomica(
                    rs.getInt("id"),
                    rs.getString("nombre")
                );
                lista.add(ae);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(ActividadEconomica ae) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE actividades_economicas SET nombre = ? WHERE id = ?"
            );
            ps.setString(1, ae.getNombre());
            ps.setInt(2, ae.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM actividades_economicas WHERE id = ?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}