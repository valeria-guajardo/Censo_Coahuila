package dao;

import modelo.Municipio;
import java.sql.*;
import java.util.*;

public class MunicipioDAO {

    public void insertar(Municipio m) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO municipios (nombre) VALUES (?)"
            );
            ps.setString(1, m.getNombre());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Municipio> listar() {
        List<Municipio> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM municipios");
            while (rs.next()) {
                lista.add(new Municipio(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Municipio m) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE municipios SET nombre = ? WHERE id = ?"
            );
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM municipios WHERE id = ?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método adicional para buscar municipio por ID
    public Municipio buscarPorId(int id) {
        Municipio municipio = null;
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM municipios WHERE id = ?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                municipio = new Municipio(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return municipio;
    }
}