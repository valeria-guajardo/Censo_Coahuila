package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class FormularioUsuarios extends JFrame {
    private JTextField txtNombre, txtApellidos, txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;

    public FormularioUsuarios(){
        setTitle("Registro de Usuario");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245,245,250));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30,30,100,25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140,30,200,25);
        add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(30,70,100,25);
        add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(140,70,200,25);
        add(txtApellidos);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30,110,100,25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140,110,200,25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30,150,100,25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140,150,200,25);
        add(txtPassword);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140,200,100,30);
        btnRegistrar.setBackground(new Color(70,130,180));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI",Font.BOLD,13));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarUsuarioAsync());
    }

    private void registrarUsuarioAsync(){
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if(nombre.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Completa todos los campos");
            return;
        }

        new Thread(() -> {
            new UsuarioDAO().insertar(new Usuario(0,usuario,password,nombre,apellidos));
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,"Usuario registrado correctamente");
                dispose();
                new LoginVista().setVisible(true);
            });
        }).start();
    }
}
