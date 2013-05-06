package shootgoal.modelos;
/**
 * Carga los jugadores con los que puedes jugar
 *
 */
public class Jugadores {
	private String nombre;
	private int puntaje;
	private int id;
	private int estado;
	private int idJugador1;
	private int idJugador2;
	public Jugadores(){

	}

	// constructor
	public Jugadores(int puntaje, String nombre,int id){
		this.puntaje = puntaje;
		this.nombre = nombre; 
		this.id=id;
	}
	public int getIdJugador1() {
		return idJugador1;
	}

	public void setIdJugador1(int idJugador1) {
		this.idJugador1 = idJugador1;
	}

	public int getIdJugador2() {
		return idJugador2;
	}

	public void setIdJugador2(int idJugador2) {
		this.idJugador2 = idJugador2;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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