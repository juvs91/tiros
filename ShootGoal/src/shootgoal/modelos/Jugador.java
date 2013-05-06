package shootgoal.modelos;

//import android.graphics.Bitmap;


import android.graphics.Point;

/**
 * Modelo que contiene las caracteristicas del jugador asi como sus propiedas
 *
 */
public abstract class Jugador {
	public Point posicion;
	
	public Jugador(){}
	public enum PosicionRelativa{
		IZQUIERDA, CENTRO, DERECHA, FUERA;
		
		public static int getPosicionValue(PosicionRelativa pos){
			switch(pos){
			case IZQUIERDA:
				return 0;
			case CENTRO:
				return 1;
			case DERECHA:
				return 2;
			}
			return -1;
		}
		
		public static PosicionRelativa getPosicionRelativa(int value){
			switch(value){
			case 0:
				return IZQUIERDA;
			case 1:
				return CENTRO;
			case 2:
				return DERECHA;
			}
			return CENTRO;
		}
	}
	public PosicionRelativa posRelativa;
	public PosicionRelativa getPosRelativa() {
		return posRelativa;
	}

	public void setPosRelativa(PosicionRelativa posRelativa) {
		this.posRelativa = posRelativa;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getTipoJugador() {
		return tipoJugador;
	}

	public void setTipoJugador(int tipoJugador) {
		this.tipoJugador = tipoJugador;
	}


	private String nombre;
	private int puntaje;
	private int id;
	private String mail;
	private int tipoJugador;

	//protected Bitmap imagen; //es la imagen que se va a utilizar , en este caso el portero y el tirador
	//protected float tiempo;  //es el tiempo que va a ir durando la animacion 
	/**
	 * @return the posicion
	 */
	public Point getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}
}
