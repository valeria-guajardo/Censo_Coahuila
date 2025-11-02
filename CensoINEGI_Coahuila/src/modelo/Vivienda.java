package modelo;

public class Vivienda {
    private int id;
    private String direccion;
    private int tipoId;
    private int municipioId;
    private int localidadId;
    // Getters, setters, constructor
	public Vivienda(int id, String direccion, int tipoId, int municipioId, int localidadId) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.tipoId = tipoId;
		this.municipioId = municipioId;
		this.localidadId = localidadId;
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
    
    
}