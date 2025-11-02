package dao;

import modelo.Localidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalidadDAO {

    public void insertar(Localidad l) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO localidades (nombre, municipio_id) VALUES (?, ?)"
            );
            ps.setString(1, l.getNombre());
            ps.setInt(2, l.getMunicipioId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Localidad> listar() {
        List<Localidad> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM localidades");
            while (rs.next()) {
                Localidad l = new Localidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("municipio_id")
                );
                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Localidad l) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE localidades SET nombre = ?, municipio_id = ? WHERE id = ?"
            );
            ps.setString(1, l.getNombre());
            ps.setInt(2, l.getMunicipioId());
            ps.setInt(3, l.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM localidades WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Localidad> listarPorMunicipio(int municipioId) {
        List<Localidad> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM localidades WHERE municipio_id = ?"
            );
            stmt.setInt(1, municipioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Localidad l = new Localidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("municipio_id")
                );
                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}