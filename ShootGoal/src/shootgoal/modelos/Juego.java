package shootgoal.modelos;

public class Juego {
	private int jugador1;
	private int jugador2;
	private int status;
	private int posTiro;
	private int porParada;
	public enum posicionRelativa{IZQUIERDA, CENTRO, DERECHA, FUERA};
	private boolean aceptado;
	
	
	public int getJugador1() {
		return jugador1;
	}
	public void setJugador1(int jugador1) {
		this.jugador1 = jugador1;
	}
	public int getJugador2() {
		return jugador2;
	}
	public void setJugador2(int jugador2) {
		this.jugador2 = jugador2;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPosTiro() {
		return posTiro;
	}
	public void setPosTiro(int posTiro) {
		this.posTiro = posTiro;
	}
	public int getPorParada() {
		return porParada;
	}
	public void setPorParada(int porParada) {
		this.porParada = porParada;
	}
	public boolean isAceptado() {
		return aceptado;
	}
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	
	
}
