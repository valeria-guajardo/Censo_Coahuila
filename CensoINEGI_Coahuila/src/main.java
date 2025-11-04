import vista.LoginVista;

public class main {
    public static void main(String[] args) {
        System.out.println("🔹 Iniciando aplicación Censo Coahuila...");

        // Ejecutar el GUI en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginVista login = new LoginVista();
            login.setVisible(true);
            System.out.println("🔹 LoginVista abierta correctamente");
        });
    }
}
