
import vista.InicioVista;

public class main {
    public static void main(String[] args) {
        System.out.println("🔹 Iniciando aplicación Censo Coahuila...");

        // Ejecutar el GUI en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            InicioVista inicio = new InicioVista();
            inicio.setVisible(true);
            System.out.println("Inicio abierto correctamente");
        });
    }
}
