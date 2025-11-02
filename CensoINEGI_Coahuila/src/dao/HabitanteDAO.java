package dao;

import modelo.Habitante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitanteDAO {
    public void insertar(Habitante h) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO habitantes (nombre, edad, genero, vivienda_id) VALUES (?, ?, ?, ?)");
            stmt.setString(1, h.getNombre());
            stmt.setInt(2, h.getEdad());
            stmt.setString(3, h.getGenero());
            stmt.setInt(4, h.getViviendaId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Habitante> listarPorVivienda(int viviendaId) {
        List<Habitante> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM habitantes WHERE vivienda_id = ?");
            stmt.setInt(1, viviendaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Habitante h = new Habitante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getString("genero"),
                    rs.getInt("vivienda_id")
                );
                lista.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}