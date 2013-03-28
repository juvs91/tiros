package com.example.shootgoal;

public class Portero extends Jugador{
	public Portero(){
		this(0,0,false);
	}
	public Portero(int x, int y, boolean b){
		posx= x;
		posy= y;
		tirando= b;
	}

}
