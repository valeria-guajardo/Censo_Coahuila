package modelo;

public class Habitante {
    private int id;
    private String nombre;
    private int edad;
    private String genero;
    private int viviendaId;
    // Constructor
	public Habitante(int id, String nombre, int edad, String genero, int viviendaId) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.genero = genero;
		this.viviendaId = viviendaId;
	}
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getViviendaId() {
		return viviendaId;
	}
	public void setViviendaId(int viviendaId) {
		this.viviendaId = viviendaId;
	}
    
	//Getters Y Setters
	
}