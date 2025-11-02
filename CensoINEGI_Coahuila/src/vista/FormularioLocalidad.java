package vista;

import modelo.Localidad;
import modelo.Municipio;
import dao.LocalidadDAO;
import dao.MunicipioDAO;
import modelo.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormularioLocalidad extends JFrame {
    private JTextField txtNombre;
    private JComboBox<Municipio> comboMunicipios;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    public FormularioLocalidad() {
        setTitle("Catálogo: Localidades");
        setSize(550, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        add(txtNombre);

        JLabel lblMunicipio = new JLabel("Municipio:");
        lblMunicipio.setBounds(20, 60, 80, 25);
        add(lblMunicipio);

        comboMunicipios = new JComboBox<>();
        comboMunicipios.setBounds(100, 60, 200, 25);
        add(comboMunicipios);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(320, 20, 100, 25);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(320, 60, 100, 25);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(320, 100, 100, 25);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(320, 140, 100, 25);
        add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Municipio"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 500, 200);
        add(scroll);

        cargarMunicipios();
        cargarTabla();
        configurarEventos();

        if (!Sesion.rol.equals("admin")) {
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }

    private void cargarMunicipios() {
        MunicipioDAO dao = new MunicipioDAO();
        List<Municipio> lista = dao.listar();
        for (Municipio m : lista) {
            comboMunicipios.addItem(m);
        }
    }

    private void cargarTabla() {
        LocalidadDAO dao = new LocalidadDAO();
        List<Localidad> lista = dao.listar();
        modelo.setRowCount(0);
        for (Localidad l : lista) {
            Municipio m = new MunicipioDAO().buscarPorId(l.getMunicipioId());
            modelo.addRow(new Object[]{l.getId(), l.getNombre(), m.getNombre()});
        }
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
            if (!nombre.isEmpty() && municipio != null) {
                new LocalidadDAO().insertar(new Localidad(0, nombre, municipio.getId()));
                cargarTabla();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                String nombre = txtNombre.getText().trim();
                Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
                new LocalidadDAO().actualizar(new Localidad(idSeleccionado, nombre, municipio.getId()));
                cargarTabla();
                limpiar();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                new LocalidadDAO().eliminar(idSeleccionado);
                cargarTabla();
                limpiar();
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) modelo.getValueAt(fila, 0);
                    txtNombre.setText((String) modelo.getValueAt(fila, 1));

                    String nombreMunicipio = (String) modelo.getValueAt(fila, 2);
                    for (int i = 0; i < comboMunicipios.getItemCount(); i++) {
                        if (comboMunicipios.getItemAt(i).getNombre().equals(nombreMunicipio)) {
                            comboMunicipios.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        comboMunicipios.setSelectedIndex(0);
        idSeleccionado = -1;
        tabla.clearSelection();
    }
}