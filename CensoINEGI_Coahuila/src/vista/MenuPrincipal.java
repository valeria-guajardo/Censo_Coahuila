package vista;

import javax.swing.*;
import java.awt.*;
import factory.FormularioFactory;
import modelo.Sesion;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("Menú Principal - INEGI");
        setSize(480, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 250)); // gris claro elegante
        add(panel);

        JLabel lblTitulo = new JLabel("Sistema INEGI", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(0, 20, 480, 30);
        lblTitulo.setForeground(new Color(45, 45, 45));
        panel.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setBounds(0, 50, 480, 25);
        lblSubtitulo.setForeground(new Color(90, 90, 90));
        panel.add(lblSubtitulo);

        JButton btnViviendas = crearBoton("Registrar Viviendas", 100);
        JButton btnHabitantes = crearBoton("Registrar Habitantes", 140);
        JButton btnReportes = crearBoton("Ver Reportes", 180);
        JButton btnTiposVivienda = crearBoton("Tipos de Vivienda", 220);
        JButton btnMunicipios = crearBoton("Municipios", 260);
        JButton btnLocalidades = crearBoton("Localidades", 300);
        JButton btnActividades = crearBoton("Actividades Económicas", 340);

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setBounds(150, 380, 180, 30);
        btnCerrar.setBackground(new Color(220, 53, 69));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnCerrar);

        panel.add(btnViviendas);
        panel.add(btnHabitantes);
        panel.add(btnReportes);
        panel.add(btnTiposVivienda);
        panel.add(btnMunicipios);
        panel.add(btnLocalidades);
        panel.add(btnActividades);

        JLabel lblUsuario = new JLabel("Conectado como: " + Sesion.usuario + " (" + Sesion.rol + ")");
        lblUsuario.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblUsuario.setBounds(10, 410, 300, 20);
        lblUsuario.setForeground(new Color(80, 80, 80));
        panel.add(lblUsuario);

        // --- Acciones de los botones ---
        btnViviendas.addActionListener(e -> FormularioFactory.crearFormulario("vivienda").setVisible(true));
        btnHabitantes.addActionListener(e -> FormularioFactory.crearFormulario("habitante").setVisible(true));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo de reportes en desarrollo."));
        btnTiposVivienda.addActionListener(e -> new FormularioTipoVivienda().setVisible(true));
        btnMunicipios.addActionListener(e -> new FormularioMunicipio().setVisible(true));
        btnLocalidades.addActionListener(e -> new FormularioLocalidad().setVisible(true));
        btnActividades.addActionListener(e -> new FormularioActividadEconomica().setVisible(true));


        btnCerrar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Sesión cerrada");
                dispose();
                new LoginVista().setVisible(true);
            }
        });

        if (!Sesion.rol.equalsIgnoreCase("admin")) {
            btnTiposVivienda.setVisible(false);
            btnMunicipios.setVisible(false);
            btnLocalidades.setVisible(false);
            btnActividades.setVisible(false);
        }
    }

    private JButton crearBoton(String texto, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(120, y, 240, 30);
        boton.setBackground(new Color(70, 130, 180)); // azul elegante
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(60, 120, 170));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(70, 130, 180));
            }
        });

        return boton;
    }

    public static void main(String[] args) {
        // Estilo moderno con FlatLaf (opcional)
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar FlatLaf, usando estilo por defecto.");
        }

        Sesion.usuario = "demoUser";
        Sesion.rol = "admin";

        new MenuPrincipal().setVisible(true);
    }
}
