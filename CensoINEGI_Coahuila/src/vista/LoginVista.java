package vista;

import controlador.LoginControlador;

import javax.swing.*;
import modelo.Sesion;

public class LoginVista extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginVista() {
        setTitle("Login INEGI");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(20, 20, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 20, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(20, 60, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 60, 150, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(100, 100, 100, 30);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            LoginControlador controlador = new LoginControlador();

            if (controlador.autenticar(usuario, password)) {
                JOptionPane.showMessageDialog(this, "Acceso concedido como " + Sesion.rol);
                dispose(); // Cierra la ventana de login
                new MenuPrincipal().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }

            System.out.println("Usuario ingresado: [" + usuario + "]");
            System.out.println("Contraseña ingresada: [" + password + "]");
        });
    }

    public static void main(String[] args) {
        new LoginVista().setVisible(true);
    }
}