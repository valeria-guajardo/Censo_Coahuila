package modelo;

public class Vivienda {
    private int id;
    private String direccion;
    private int tipoId;
    private int municipioId;
    private int localidadId;
    private int actividadId;

    // Constructor
    public Vivienda(int id, String direccion, int tipoId, int municipioId, int localidadId, int actividadId) {
        this.id = id;
        this.direccion = direccion;
        this.tipoId = tipoId;
        this.municipioId = municipioId;
        this.localidadId = localidadId;
        this.actividadId = actividadId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    public int getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(int localidadId) {
        this.localidadId = localidadId;
    }

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    @Override
    public String toString() {
        return direccion;
    }
}
