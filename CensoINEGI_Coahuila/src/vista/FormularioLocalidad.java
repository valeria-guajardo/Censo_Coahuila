package vista;

import modelo.Localidad;
import modelo.Municipio;
import dao.LocalidadDAO;
import dao.MunicipioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
        getContentPane().setBackground(new Color(245, 245, 250));

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

        btnGuardar = crearBoton("Guardar", 320, 20, new Color(70, 130, 180));
        btnActualizar = crearBoton("Actualizar", 320, 60, new Color(255, 165, 0));
        btnEliminar = crearBoton("Eliminar", 320, 100, new Color(220, 53, 69));
        btnLimpiar = crearBoton("Limpiar", 320, 140, new Color(128, 128, 128));

        add(btnGuardar); add(btnActualizar); add(btnEliminar); add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Municipio"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 500, 200);
        add(scroll);

        cargarMunicipios();
        cargarTabla();
        configurarEventos();
    }

    private JButton crearBoton(String texto, int x, int y, Color color) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 100, 25);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cargarMunicipios() {
        SwingWorker<List<Municipio>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Municipio> doInBackground() { return new MunicipioDAO().listar(); }
            @Override
            protected void done() {
                try {
                    List<Municipio> lista = get();
                    comboMunicipios.removeAllItems();
                    for (Municipio m : lista) comboMunicipios.addItem(m);
                    if (!lista.isEmpty()) comboMunicipios.setSelectedIndex(0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(FormularioLocalidad.this, "Error al cargar municipios: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void cargarTabla() {
        SwingWorker<List<Localidad>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Localidad> doInBackground() { return new LocalidadDAO().listar(); }
            @Override
            protected void done() {
                try {
                    List<Localidad> lista = get();
                    modelo.setRowCount(0);
                    for (Localidad l : lista) {
                        Municipio m = new MunicipioDAO().buscarPorId(l.getMunicipioId());
                        modelo.addRow(new Object[]{l.getId(), l.getNombre(), m.getNombre()});
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(FormularioLocalidad.this, "Error al cargar localidades: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
            if (!nombre.isEmpty() && municipio != null) {
                new LocalidadDAO().insertar(new Localidad(0, nombre, municipio.getId()));
                cargarTabla(); limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                String nombre = txtNombre.getText().trim();
                Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
                new LocalidadDAO().actualizar(new Localidad(idSeleccionado, nombre, municipio.getId()));
                cargarTabla(); limpiar();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                int resp = JOptionPane.showConfirmDialog(this, "¿Eliminar esta localidad?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    new LocalidadDAO().eliminar(idSeleccionado);
                    cargarTabla(); limpiar();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void limpiar() {
        txtNombre.setText("");
        idSeleccionado = -1;
        comboMunicipios.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
