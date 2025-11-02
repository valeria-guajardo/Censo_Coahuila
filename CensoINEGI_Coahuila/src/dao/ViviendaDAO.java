package dao;

import modelo.Vivienda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViviendaDAO {
    public void insertar(Vivienda vivienda) {
        try {
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO viviendas (direccion, tipo_id, municipio_id, localidad_id) VALUES (?, ?, ?, ?)");
            stmt.setString(1, vivienda.getDireccion());
            stmt.setInt(2, vivienda.getTipoId());
            stmt.setInt(3, vivienda.getMunicipioId());
            stmt.setInt(4, vivienda.getLocalidadId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Vivienda> listar() {
        List<Vivienda> lista = new ArrayList<>();
        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM viviendas");
            while (rs.next()) {
                Vivienda v = new Vivienda(
                    rs.getInt("id"),
                    rs.getString("direccion"),
                    rs.getInt("tipo_id"),
                    rs.getInt("municipio_id"),
                    rs.getInt("localidad_id")
                );
                lista.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}