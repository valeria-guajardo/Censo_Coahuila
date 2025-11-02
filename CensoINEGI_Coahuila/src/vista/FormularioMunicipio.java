package vista;

import modelo.Municipio;
import dao.MunicipioDAO;
import modelo.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormularioMunicipio extends JFrame {
    private JTextField txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    public FormularioMunicipio() {
        setTitle("Catálogo: Municipios");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        add(txtNombre);

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

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 440, 150);
        add(scroll);

        cargarTabla();
        configurarEventos();

        if (!Sesion.rol.equals("admin")) {
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }

    private void cargarTabla() {
        MunicipioDAO dao = new MunicipioDAO();
        List<Municipio> lista = dao.listar();
        modelo.setRowCount(0);
        for (Municipio m : lista) {
            modelo.addRow(new Object[]{m.getId(), m.getNombre()});
        }
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                new MunicipioDAO().insertar(new Municipio(0, nombre));
                cargarTabla();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                String nombre = txtNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    new MunicipioDAO().actualizar(new Municipio(idSeleccionado, nombre));
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "El campo nombre no puede estar vacío.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un municipio para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este municipio?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    new MunicipioDAO().eliminar(idSeleccionado);
                    cargarTabla();
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un municipio para eliminar.");
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    idSeleccionado = (int) modelo.getValueAt(fila, 0);
                    txtNombre.setText((String) modelo.getValueAt(fila, 1));
                }
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        idSeleccionado = -1;
        tabla.clearSelection();
    }
}