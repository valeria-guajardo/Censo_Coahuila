package vista;

import dao.HabitanteDAO;
import dao.ViviendaDAO;
import modelo.Habitante;
import modelo.Vivienda;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormularioHabitante extends JFrame {
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> comboGenero;
    private JComboBox<Vivienda> comboVivienda;
    private JButton btnGuardar, btnCancelar;

    public FormularioHabitante() {
        setTitle("Registrar Habitante");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 200, 25);
        add(txtNombre);

        // edad
        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(30, 70, 100, 25);
        add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(140, 70, 200, 25);
        add(txtEdad);

        //genero
        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(30, 110, 100, 25);
        add(lblGenero);

        comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        comboGenero.setBounds(140, 110, 200, 25);
        add(comboGenero);

        //vivienda
        JLabel lblVivienda = new JLabel("Vivienda:");
        lblVivienda.setBounds(30, 150, 100, 25);
        add(lblVivienda);

        comboVivienda = new JComboBox<>();
        comboVivienda.setBounds(140, 150, 200, 25);
        add(comboVivienda);


        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 200, 100, 30);
        btnGuardar.setBackground(new Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(300, 200, 100, 30);
        btnCancelar.setBackground(new Color(220, 53, 69));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCancelar);

        cargarViviendas();
        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarHabitante());
    }

    private void cargarViviendas() {
        ViviendaDAO dao = new ViviendaDAO();
        List<Vivienda> lista = dao.listar();
        comboVivienda.removeAllItems();
        for (Vivienda v : lista) {
            comboVivienda.addItem(v);
        }
    }

    private void guardarHabitante() {
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String genero = (String) comboGenero.getSelectedItem();
        Vivienda vivienda = (Vivienda) comboVivienda.getSelectedItem();

        if (nombre.isEmpty() || edadStr.isEmpty() || genero == null || vivienda == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            if (edad <= 0) {
                JOptionPane.showMessageDialog(this, "La edad debe ser mayor a 0", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Habitante h = new Habitante(0, nombre, edad, genero, vivienda.getId());
            HabitanteDAO dao = new HabitanteDAO();
            dao.insertar(h);
            JOptionPane.showMessageDialog(this, "Habitante registrado correctamente");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioHabitante().setVisible(true));
    }
}
