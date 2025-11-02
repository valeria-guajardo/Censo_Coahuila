package vista;

import modelo.TipoVivienda;
import dao.TipoViviendaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FormularioTipoVivienda extends JFrame {
    private JTextField txtTipo;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    public FormularioTipoVivienda() {
        setTitle("Catálogo: Tipos de Vivienda");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTipo = new JLabel("Tipo de vivienda:");
        lblTipo.setBounds(20, 20, 120, 25);
        add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(150, 20, 200, 25);
        add(txtTipo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(370, 20, 100, 25);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(370, 60, 100, 25);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(370, 100, 100, 25);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(370, 140, 100, 25);
        add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"ID", "Tipo"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 450, 150);
        add(scroll);

        cargarTabla();
        configurarEventos();
    }

    private void cargarTabla() {
        TipoViviendaDAO dao = new TipoViviendaDAO();
        List<TipoVivienda> lista = dao.listar();
        modelo.setRowCount(0);
        for (TipoVivienda tv : lista) {
            modelo.addRow(new Object[]{tv.getId(), tv.getTipo()});
        }
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            String tipo = txtTipo.getText().trim();
            if (!tipo.isEmpty()) {
                TipoViviendaDAO dao = new TipoViviendaDAO();
                dao.insertar(new TipoVivienda(0, tipo));
                cargarTabla();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                String tipo = txtTipo.getText().trim();
                TipoViviendaDAO dao = new TipoViviendaDAO();
                dao.actualizar(new TipoVivienda(idSeleccionado, tipo));
                cargarTabla();
                limpiar();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                TipoViviendaDAO dao = new TipoViviendaDAO();
                dao.eliminar(idSeleccionado);
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
                    txtTipo.setText((String) modelo.getValueAt(fila, 1));
                }
            }
        });
    }

    private void limpiar() {
        txtTipo.setText("");
        idSeleccionado = -1;
        tabla.clearSelection();
    }
}