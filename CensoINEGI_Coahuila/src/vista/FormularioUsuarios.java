package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;

public class FormularioUsuarios extends JFrame {
    private JTextField txtNombre, txtApellidos, txtUsuario;
    private JPasswordField txtPassword;

    public FormularioUsuarios() {
        setTitle("Registro de Usuario");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 200, 25);
        add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(30, 70, 100, 25);
        add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(140, 70, 200, 25);
        add(txtApellidos);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 110, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 110, 200, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 150, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 150, 200, 25);
        add(txtPassword);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140, 200, 100, 30);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            if (nombre.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos");
                return;
            }

            Usuario nuevo = new Usuario(0, usuario, password, nombre, apellidos);
            UsuarioDAO dao = new UsuarioDAO();
            dao.insertar(nuevo);

            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
            dispose();
            new LoginVista().setVisible(true);
        });
    }
}