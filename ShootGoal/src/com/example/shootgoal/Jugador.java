package com.example.shootgoal;

import android.graphics.Bitmap;

public abstract class Jugador {
	protected int posx;
	protected int posy;
	protected Bitmap imagen; //es la imagen que se va a utilizar , en este caso el portero y el tirador
	protected float tiempo;  //es el tiempo que va a ir durando la animacion 

	public int getPosx() {
		return posx;
	}
	
	public void setPosx(int posx) {
		this.posx = posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public void setPosy(int posy) {
		this.posy = posy;
	}	
	public Bitmap getImagen() {
		return imagen;
	}
	
	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}	
	public float getTiempo() {
		return tiempo;
	}
	
	public void setTiempo(float tiempo) {
		this.tiempo = tiempo;
	}	
	
}
