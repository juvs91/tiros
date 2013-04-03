package com.example.shootgoal;

import android.graphics.Point;

public abstract class Jugador {
	protected Point posicion;

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
