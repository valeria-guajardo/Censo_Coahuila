package vista;

import dao.MunicipioDAO;
import dao.LocalidadDAO;
import dao.TipoViviendaDAO;
import dao.ViviendaDAO;
import modelo.Municipio;
import modelo.Localidad;
import modelo.TipoVivienda;
import modelo.Vivienda;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class FormularioVivienda extends JFrame {
    private JTextField txtDireccion;
    private JComboBox<Municipio> comboMunicipio;
    private JComboBox<Localidad> comboLocalidad;
    private JComboBox<TipoVivienda> comboTipo;
    private JButton btnGuardar;

    public FormularioVivienda() {
        setTitle("Registrar Vivienda");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(30, 30, 100, 25);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(140, 30, 200, 25);
        add(txtDireccion);

        JLabel lblMunicipio = new JLabel("Municipio:");
        lblMunicipio.setBounds(30, 70, 100, 25);
        add(lblMunicipio);

        comboMunicipio = new JComboBox<>();
        comboMunicipio.setBounds(140, 70, 200, 25);
        add(comboMunicipio);

        JLabel lblLocalidad = new JLabel("Localidad:");
        lblLocalidad.setBounds(30, 110, 100, 25);
        add(lblLocalidad);

        comboLocalidad = new JComboBox<>();
        comboLocalidad.setBounds(140, 110, 200, 25);
        add(comboLocalidad);

        JLabel lblTipo = new JLabel("Tipo de Vivienda:");
        lblTipo.setBounds(30, 150, 100, 25);
        add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setBounds(140, 150, 200, 25);
        add(comboTipo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 200, 100, 30);
        add(btnGuardar);

        cargarMunicipios();
        cargarTipos();

        comboMunicipio.addActionListener(e -> cargarLocalidades());

        btnGuardar.addActionListener(e -> guardarVivienda());
    }

    private void cargarMunicipios() {
        MunicipioDAO dao = new MunicipioDAO();
        List<Municipio> lista = dao.listar();
        comboMunicipio.removeAllItems();
        for (Municipio m : lista) {
            comboMunicipio.addItem(m);
        }
    }

    private void cargarLocalidades() {
        Municipio seleccionado = (Municipio) comboMunicipio.getSelectedItem();
        if (seleccionado != null) {
            LocalidadDAO dao = new LocalidadDAO();
            List<Localidad> lista = dao.listarPorMunicipio(seleccionado.getId());
            comboLocalidad.removeAllItems();
            for (Localidad l : lista) {
                comboLocalidad.addItem(l);
            }
        }
    }

    private void cargarTipos() {
        TipoViviendaDAO dao = new TipoViviendaDAO();
        List<TipoVivienda> lista = dao.listar();
        comboTipo.removeAllItems();
        for (TipoVivienda t : lista) {
            comboTipo.addItem(t);
        }
    }

    private void guardarVivienda() {
        String direccion = txtDireccion.getText();
        Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();
        Localidad localidad = (Localidad) comboLocalidad.getSelectedItem();
        TipoVivienda tipo = (TipoVivienda) comboTipo.getSelectedItem();

        if (direccion.isEmpty() || municipio == null || localidad == null || tipo == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos");
            return;
        }

        Vivienda vivienda = new Vivienda(0, direccion, tipo.getId(), municipio.getId(), localidad.getId());
        ViviendaDAO dao = new ViviendaDAO();
        dao.insertar(vivienda);
        JOptionPane.showMessageDialog(this, "Vivienda registrada correctamente");
        dispose();
    }
}