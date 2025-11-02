package vista;

import javax.swing.*;
import factory.FormularioFactory;
import modelo.Sesion;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Menú Principal INEGI");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // Centra la ventana

        // Botones principales
        JButton btnViviendas = new JButton("Registrar Viviendas");
        btnViviendas.setBounds(120, 30, 200, 30);
        add(btnViviendas);

        JButton btnHabitantes = new JButton("Registrar Habitantes");
        btnHabitantes.setBounds(120, 70, 200, 30);
        add(btnHabitantes);

        JButton btnReportes = new JButton("Ver Reportes");
        btnReportes.setBounds(120, 110, 200, 30);
        add(btnReportes);

        JButton btnTiposVivienda = new JButton("Tipos de Vivienda");
        btnTiposVivienda.setBounds(120, 150, 200, 30);
        add(btnTiposVivienda);

        JButton btnMunicipios = new JButton("Municipios");
        btnMunicipios.setBounds(120, 190, 200, 30);
        add(btnMunicipios);

        JButton btnLocalidades = new JButton("Localidades");
        btnLocalidades.setBounds(120, 230, 200, 30);
        add(btnLocalidades);

        JButton btnActividades = new JButton("Actividades Económicas");
        btnActividades.setBounds(120, 270, 200, 30);
        add(btnActividades);

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setBounds(120, 310, 200, 30);
        add(btnCerrar);

        // Acciones
        btnViviendas.addActionListener(e -> {
            FormularioFactory.crearFormulario("vivienda").setVisible(true);
        });

        btnHabitantes.addActionListener(e -> {
            FormularioFactory.crearFormulario("habitante").setVisible(true);
        });

        btnTiposVivienda.addActionListener(e -> {
            new FormularioTipoVivienda().setVisible(true);
        });

        btnMunicipios.addActionListener(e -> {
            new FormularioMunicipio().setVisible(true);
        });

        btnLocalidades.addActionListener(e -> {
            new FormularioLocalidad().setVisible(true);
        });

        btnActividades.addActionListener(e -> {
            new FormularioActividadEconomica().setVisible(true);
        });

        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada");
            dispose();
            new LoginVista().setVisible(true);
        });

        // Control de roles: ocultar catálogos si no eres admin
        if (!Sesion.rol.equals("admin")) {
            btnTiposVivienda.setVisible(false);
            btnMunicipios.setVisible(false);
            btnLocalidades.setVisible(false);
            btnActividades.setVisible(false);
        }
    }
}