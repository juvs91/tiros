package com.example.shootgoal;

import android.graphics.Bitmap;

public class Tirador extends Jugador {
	public Tirador(){
		this(0, 0,null,0);
	}
	
	public Tirador(int x, int y,Bitmap imagen,float tiempo){
		posicion.set(x, y);
		//this.imagen=imagen;
		//this.tiempo=tiempo;
	}
	
}
