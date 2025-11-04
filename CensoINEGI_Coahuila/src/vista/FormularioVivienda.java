package vista;

import dao.*;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormularioVivienda extends JFrame {
    private JTextField txtDireccion;
    private JComboBox<Municipio> comboMunicipio;
    private JComboBox<Localidad> comboLocalidad;
    private JComboBox<TipoVivienda> comboTipo;
    private JComboBox<ActividadEconomica> comboActividad;
    private JButton btnGuardar, btnLimpiar;

    public FormularioVivienda() {
        setTitle("Registrar Vivienda");
        setSize(500, 400);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDireccion = new JLabel("Dirección:");
        JLabel lblMunicipio = new JLabel("Municipio:");
        JLabel lblLocalidad = new JLabel("Localidad:");
        JLabel lblTipo = new JLabel("Tipo de vivienda:");
        JLabel lblActividad = new JLabel("Actividad económica:");

        txtDireccion = new JTextField(20);
        comboMunicipio = new JComboBox<>();
        comboLocalidad = new JComboBox<>();
        comboTipo = new JComboBox<>();
        comboActividad = new JComboBox<>();

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        // Fila 1 - Dirección
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblDireccion, gbc);
        gbc.gridx = 1;
        add(txtDireccion, gbc);

        // Fila 2 - Municipio
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblMunicipio, gbc);
        gbc.gridx = 1;
        add(comboMunicipio, gbc);

        // Fila 3 - Localidad
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblLocalidad, gbc);
        gbc.gridx = 1;
        add(comboLocalidad, gbc);

        // Fila 4 - Tipo
        gbc.gridx = 0; gbc.gridy = 3;
        add(lblTipo, gbc);
        gbc.gridx = 1;
        add(comboTipo, gbc);

        // Fila 5 - Actividad
        gbc.gridx = 0; gbc.gridy = 4;
        add(lblActividad, gbc);
        gbc.gridx = 1;
        add(comboActividad, gbc);

        // Fila 6 - Botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(panelBotones, gbc);

        // Eventos
        btnGuardar.addActionListener(e -> guardarViviendaAsync());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        comboMunicipio.addActionListener(e -> cargarLocalidadesAsync());

        // Cargar datos iniciales
        cargarMunicipiosAsync();
        cargarTiposAsync();
        cargarActividadesAsync();
    }

    private void cargarMunicipiosAsync() {
        SwingWorker<List<Municipio>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Municipio> doInBackground() {
                return new MunicipioDAO().listar();
            }

            @Override
            protected void done() {
                try {
                    comboMunicipio.removeAllItems();
                    for (Municipio m : get()) comboMunicipio.addItem(m);
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private void cargarLocalidadesAsync() {
        Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();
        if (municipio == null) return;

        SwingWorker<List<Localidad>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Localidad> doInBackground() {
                return new LocalidadDAO().listarPorMunicipio(municipio.getId());
            }

            @Override
            protected void done() {
                try {
                    comboLocalidad.removeAllItems();
                    for (Localidad l : get()) comboLocalidad.addItem(l);
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private void cargarTiposAsync() {
        SwingWorker<List<TipoVivienda>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<TipoVivienda> doInBackground() {
                return new TipoViviendaDAO().listar();
            }

            @Override
            protected void done() {
                try {
                    comboTipo.removeAllItems();
                    for (TipoVivienda t : get()) comboTipo.addItem(t);
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private void cargarActividadesAsync() {
        SwingWorker<List<ActividadEconomica>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ActividadEconomica> doInBackground() {
                return new ActividadEconomicaDAO().listar();
            }

            @Override
            protected void done() {
                try {
                    comboActividad.removeAllItems();
                    for (ActividadEconomica a : get()) comboActividad.addItem(a);
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private void guardarViviendaAsync() {
        String direccion = txtDireccion.getText().trim();
        Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();
        Localidad localidad = (Localidad) comboLocalidad.getSelectedItem();
        TipoVivienda tipo = (TipoVivienda) comboTipo.getSelectedItem();
        ActividadEconomica actividad = (ActividadEconomica) comboActividad.getSelectedItem();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa la dirección.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            txtDireccion.requestFocus();
            return;
        }
        if (municipio == null || localidad == null || tipo == null || actividad == null) {
            JOptionPane.showMessageDialog(this, "Selecciona todas las opciones.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            Vivienda vivienda = new Vivienda(0, direccion, tipo.getId(), municipio.getId(), localidad.getId(), actividad.getId());
            new ViviendaDAO().insertar(vivienda);

            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Vivienda registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE)
            );
        }).start();

        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDireccion.setText("");
        comboMunicipio.setSelectedIndex(0);
        comboLocalidad.removeAllItems();
        comboTipo.setSelectedIndex(0);
        comboActividad.setSelectedIndex(0);
    }
}
