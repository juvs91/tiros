package shootgoal.modelos;

//import android.graphics.Bitmap;


import android.graphics.Point;

public abstract class Jugador {
	public Point posicion;
	public enum PosicionRelativa{
		IZQUIERDA, CENTRO, DERECHA, FUERA;
	}
	public PosicionRelativa posRelativa;
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
