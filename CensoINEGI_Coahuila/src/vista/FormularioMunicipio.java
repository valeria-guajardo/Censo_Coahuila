package vista;

import modelo.Municipio;
import dao.MunicipioDAO;
import modelo.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
        getContentPane().setBackground(new Color(245,245,250));

        // Campo Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        add(txtNombre);

        // Botones
        btnGuardar = crearBoton("Guardar", 320, 20, new Color(70,130,180));
        btnActualizar = crearBoton("Actualizar", 320, 60, new Color(255,165,0));
        btnEliminar = crearBoton("Eliminar", 320, 100, new Color(220,53,69));
        btnLimpiar = crearBoton("Limpiar", 320, 140, new Color(128,128,128));

        add(btnGuardar); add(btnActualizar); add(btnEliminar); add(btnLimpiar);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 180, 440, 150);
        add(scroll);

        // Deshabilitar botones si no es admin
        if (!Sesion.rol.equals("admin")) {
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        configurarEventos();

        // Cargar datos en background
        cargarTablaAsync();
    }

    private JButton crearBoton(String texto, int x, int y, Color color) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 100, 30);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                new Thread(() -> {
                    new MunicipioDAO().insertar(new Municipio(0, nombre));
                    cargarTablaAsync();
                }).start();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                String nombre = txtNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    new Thread(() -> {
                        new MunicipioDAO().actualizar(new Municipio(idSeleccionado, nombre));
                        cargarTablaAsync();
                    }).start();
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
                    new Thread(() -> {
                        new MunicipioDAO().eliminar(idSeleccionado);
                        cargarTablaAsync();
                    }).start();
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

    private void cargarTablaAsync() {
        SwingWorker<List<Municipio>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Municipio> doInBackground() {
                return new MunicipioDAO().listar();
            }
            @Override
            protected void done() {
                try {
                    List<Municipio> lista = get();
                    modelo.setRowCount(0);
                    for (Municipio m : lista) {
                        modelo.addRow(new Object[]{m.getId(), m.getNombre()});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}
