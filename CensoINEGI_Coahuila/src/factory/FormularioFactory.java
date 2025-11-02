package factory;

import javax.swing.JFrame;
import vista.FormularioVivienda;
import vista.FormularioHabitante;
import vista.FormularioUsuarios;

public class FormularioFactory {
    public static JFrame crearFormulario(String tipo) {
        switch (tipo.toLowerCase()) {
            case "vivienda":
                return new FormularioVivienda();
            case "habitante":
                return new FormularioHabitante();
            case "usuario":
                return new FormularioUsuarios();
            default:
                throw new IllegalArgumentException("Formulario desconocido: " + tipo);
        }
    }
}