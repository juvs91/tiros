package shootgoal.modelos;

public class Juego {
	

	private Tirador portero;
	private Portero tirador;
	private int status;
	private int posTiro;
	private int porParada;
	public enum posicionRelativa{IZQUIERDA, CENTRO, DERECHA, FUERA};
	private boolean aceptado;
	
	public Tirador getPortero() {
		return portero;
	}
	public void setPortero(Tirador portero) {
		this.portero = portero;
	}
	public Portero getTirador() {
		return tirador;
	}
	public void setTirador(Portero tirador) {
		this.tirador = tirador;
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
	
	public static Juego fetch(int idJuegador1,int idJugador2){
		
		return null;
		
	}

	
	
}
