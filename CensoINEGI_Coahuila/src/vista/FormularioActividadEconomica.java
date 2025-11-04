package vista;

import dao.ActividadEconomicaDAO;
import modelo.ActividadEconomica;
import modelo.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioActividadEconomica extends JFrame {
    private JTextField txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado=-1;

    public FormularioActividadEconomica(){
        setTitle("Catálogo: Actividades Económicas");
        setSize(500,400);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245,245,250));

        JLabel lblNombre = new JLabel("Actividad:");
        lblNombre.setBounds(20,20,80,25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100,20,200,25);
        add(txtNombre);

        btnGuardar = crearBoton("Guardar",320,20,new Color(70,130,180));
        btnActualizar = crearBoton("Actualizar",320,60,new Color(255,165,0));
        btnEliminar = crearBoton("Eliminar",320,100,new Color(220,53,69));
        btnLimpiar = crearBoton("Limpiar",320,140,new Color(128,128,128));

        add(btnGuardar); add(btnActualizar); add(btnEliminar); add(btnLimpiar);

        modelo = new DefaultTableModel(new String[]{"ID","Actividad"},0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20,180,440,150);
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
            String nombre = txtNombre.getText().trim();
            if(!nombre.isEmpty()){
                new Thread(() -> {
                    new ActividadEconomicaDAO().insertar(new ActividadEconomica(0,nombre));
                    cargarTablaAsync();
                }).start();
                limpiar();
            }
        });

        btnActualizar.addActionListener(e -> {
            if(idSeleccionado!=-1){
                String nombre = txtNombre.getText().trim();
                if(!nombre.isEmpty()){
                    new Thread(() -> {
                        new ActividadEconomicaDAO().actualizar(new ActividadEconomica(idSeleccionado,nombre));
                        cargarTablaAsync();
                    }).start();
                    limpiar();
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if(idSeleccionado!=-1){
                int confirmar = JOptionPane.showConfirmDialog(this,"¿Eliminar esta actividad?","Confirmar",JOptionPane.YES_NO_OPTION);
                if(confirmar==JOptionPane.YES_OPTION){
                    new Thread(() -> {
                        new ActividadEconomicaDAO().eliminar(idSeleccionado);
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
                    idSeleccionado=(int)modelo.getValueAt(fila,0);
                    txtNombre.setText((String)modelo.getValueAt(fila,1));
                }
            }
        });
    }

    private void limpiar(){
        txtNombre.setText("");
        idSeleccionado=-1;
        tabla.clearSelection();
    }

    private void cargarTablaAsync(){
        SwingWorker<List<ActividadEconomica>,Void> worker = new SwingWorker<>(){
            @Override
            protected List<ActividadEconomica> doInBackground(){ return new ActividadEconomicaDAO().listar(); }
            @Override
            protected void done(){
                try{
                    List<ActividadEconomica> lista = get();
                    modelo.setRowCount(0);
                    for(ActividadEconomica a: lista) modelo.addRow(new Object[]{a.getId(),a.getNombre()});
                }catch(Exception e){ e.printStackTrace(); }
            }
        };
        worker.execute();
    }
}
