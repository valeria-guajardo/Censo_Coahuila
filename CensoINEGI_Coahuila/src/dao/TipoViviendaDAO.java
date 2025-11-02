package dao;

import modelo.TipoVivienda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoViviendaDAO {

    public void insertar(TipoVivienda tv) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tipos_vivienda (tipo) VALUES (?)");
            ps.setString(1, tv.getTipo());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TipoVivienda> listar() {
        List<TipoVivienda> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tipos_vivienda");
            while (rs.next()) {
                TipoVivienda tv = new TipoVivienda(rs.getInt("id"), rs.getString("tipo"));
                lista.add(tv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(TipoVivienda tv) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement("UPDATE tipos_vivienda SET tipo = ? WHERE id = ?");
            ps.setString(1, tv.getTipo());
            ps.setInt(2, tv.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM tipos_vivienda WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}