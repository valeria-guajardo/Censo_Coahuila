package modelo;

public class Localidad {
    private int id;
    private String nombre;
    private int municipioId;

    // Constructor
    public Localidad(int id, String nombre, int municipioId) {
        this.id = id;
        this.nombre = nombre;
        this.municipioId = municipioId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    // Para que aparezca el nombre en los JComboBox
    @Override
    public String toString() {
        return nombre;
    }
}
