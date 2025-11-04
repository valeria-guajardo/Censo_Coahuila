package vista;

import dao.TipoViviendaDAO;
import modelo.TipoVivienda;
import modelo.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioTipoVivienda extends JFrame {
    private JTextField txtTipo;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;

    public FormularioTipoVivienda() {
        setTitle("Catálogo: Tipos de Vivienda");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245,245,250));

        JLabel lblTipo = new JLabel("Tipo de vivienda:");
        lblTipo.setBounds(20,20,120,25);
        add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(150,20,200,25);
        add(txtTipo);

        btnGuardar = crearBoton("Guardar",370,20,new Color(70,130,180));
        btnActualizar = crearBoton("Actualizar",370,60,new Color(255,165,0));
        btnEliminar = crearBoton("Eliminar",370,100,new Color(220,53,69));
        btnLimpiar = crearBoton("Limpiar",370,140,new Color(128,128,128));

        add(btnGuardar); add(btnActualizar); add(btnEliminar); add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"ID","Tipo"},0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20,180,450,150);
        add(scroll);

        if(!Sesion.rol.equals("admin")){
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        configurarEventos();
        cargarTablaAsync();
    }

    private JButton crearBoton(String texto,int x,int y,Color color){
        JButton btn = new JButton(texto);
        btn.setBounds(x,y,100,30);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void configurarEventos(){
        btnGuardar.addActionListener(e -> {
            String tipo = txtTipo.getText().trim();
            if(!tipo.isEmpty()){
                new Thread(() -> {
                    new TipoViviendaDAO().insertar(new TipoVivienda(0,tipo));
                    cargarTablaAsync();
                }).start();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if(idSeleccionado!=-1){
                String tipo = txtTipo.getText().trim();
                if(!tipo.isEmpty()){
                    new Thread(() -> {
                        new TipoViviendaDAO().actualizar(new TipoVivienda(idSeleccionado,tipo));
                        cargarTablaAsync();
                    }).start();
                    limpiar();
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if(idSeleccionado!=-1){
                int confirmar = JOptionPane.showConfirmDialog(this,"¿Eliminar este tipo de vivienda?","Confirmar",JOptionPane.YES_NO_OPTION);
                if(confirmar==JOptionPane.YES_OPTION){
                    new Thread(() -> {
                        new TipoViviendaDAO().eliminar(idSeleccionado);
                        cargarTablaAsync();
                    }).start();
                    limpiar();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                int fila = tabla.getSelectedRow();
                if(fila!=-1){
                    idSeleccionado = (int)modelo.getValueAt(fila,0);
                    txtTipo.setText((String)modelo.getValueAt(fila,1));
                }
            }
        });
    }

    private void limpiar(){
        txtTipo.setText("");
        idSeleccionado=-1;
        tabla.clearSelection();
    }

    private void cargarTablaAsync(){
        SwingWorker<List<TipoVivienda>,Void> worker = new SwingWorker<>(){
            @Override
            protected List<TipoVivienda> doInBackground() { return new TipoViviendaDAO().listar(); }
            @Override
            protected void done(){
                try{
                    List<TipoVivienda> lista = get();
                    modelo.setRowCount(0);
                    for(TipoVivienda t: lista) modelo.addRow(new Object[]{t.getId(),t.getTipo()});
                }catch(Exception e){ e.printStackTrace(); }
            }
        };
        worker.execute();
    }
}
