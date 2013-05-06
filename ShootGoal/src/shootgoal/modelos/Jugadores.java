package shootgoal.modelos;
/**
 * Carga los jugadores con los que puedes jugar
 *
 */
public class Jugadores {
	private String nombre;
	private String mail;
	private int puntaje;
	private int id;
	private int estado;
	private int idJugador1;
	private int idJugador2;
    private  int puntajeJugador1;
	private  int puntajeJugador2;
	private int posTiro;
	private int posParo;
	public Jugadores(){

	}
	public  int getPuntajeJugador1() {
		return puntajeJugador1;
	}
	public int getPosTiro() {
		return posTiro;
	}
	public void setPosTiro(int posTiro) {
		this.posTiro = posTiro;
	}
	public int getPosParo() {
		return posParo;
	}
	public void setPosParo(int posParo) {
		this.posParo = posParo;
	}

	public  void setPuntajeJugador1(int puntajeJugador1) {
		this.puntajeJugador1 = puntajeJugador1;
	}

	public  int getPuntajeJugador2() {
		return puntajeJugador2;
	}

	public void setPuntajeJugador2(int puntajeJugador2) {
		this.puntajeJugador2 = puntajeJugador2;
	}

	// constructor
	public Jugadores(int puntaje, String nombre,int id){
		this.puntaje = puntaje;
		this.nombre = nombre; 
		this.id=id;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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