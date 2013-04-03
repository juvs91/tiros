package com.example.shootgoal;

public class Tirador extends Jugador {
	public Tirador(){
		this(0, 0, true);
	}
	public Tirador(int x, int y, boolean b){
		posicion.set(x, y);
	}
	
}
