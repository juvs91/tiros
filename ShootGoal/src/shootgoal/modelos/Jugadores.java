package shootgoal.modelos;

public class Jugadores {
	private String nombre;
	private int puntaje;
	private int id;
	public Jugadores(){

	}
	
	// constructor
	public Jugadores(int puntaje, String nombre,int id){
		this.puntaje = puntaje;
		this.nombre = nombre; 
		this.id=id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
