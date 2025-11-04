package vista;

import controlador.LoginControlador;
import modelo.Sesion;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    private final LoginControlador controlador;

    public LoginVista() {
        controlador = new LoginControlador();
        initUI();
    }

    private void initUI() {
        setTitle("? INEGI - Censo Coahuila");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 250);
        setResizable(false);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 153));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField(15);

        gbc.gridx = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField(15);

        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2;
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 102, 153));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);

        panel.add(btnIngresar, gbc);

        add(panel);

        btnIngresar.addActionListener(e -> autenticar());
    }

    // Código en LoginVista.java
    // Código en LoginVista.java
    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. DESHABILITAR el botón y la interfaz para evitar clicks mientras se conecta
        btnIngresar.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        // 2. CREAR UN HILO SEPARADO para la operación de red (conexión a DB)
        new Thread(() -> {
            // Ejecutar la operación que bloquea (conexión a DB)
            LoginControlador controlador = new LoginControlador();
            boolean exito = controlador.autenticar(usuario, password);

            // 3. Volver al Hilo de Interfaz (EDT) para actualizar la UI
            SwingUtilities.invokeLater(() -> {
                btnIngresar.setEnabled(true); // Habilitar el botón de nuevo
                setCursor(Cursor.getDefaultCursor()); // Restaurar cursor

                if (exito) {
                    // Si llegaste a este punto, usa las credenciales correctas: admin / 123
                    JOptionPane.showMessageDialog(this, "Acceso concedido como " + Sesion.rol);
                    dispose();
                    new MenuPrincipal().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start(); // ¡Iniciar el hilo!
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVista().setVisible(true));
    }
}