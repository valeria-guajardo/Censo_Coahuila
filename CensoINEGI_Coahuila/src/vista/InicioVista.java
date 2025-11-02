package vista;

import javax.swing.*;

public class InicioVista extends JFrame {
    public InicioVista() {
        setTitle("Bienvenido");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("CENSO INEGI");
        titulo.setBounds(100, 30, 200, 40);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(java.awt.Color.ORANGE);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        add(titulo);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(100, 100, 200, 30);
        add(btnLogin);

        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setBounds(100, 150, 200, 30);
        add(btnRegistro);

        btnLogin.addActionListener(e -> {
            dispose();
            new LoginVista().setVisible(true);
        });

        btnRegistro.addActionListener(e -> {
            dispose();
            new FormularioUsuarios().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new InicioVista().setVisible(true);
    }
}
