package com.example.shootgoal;

public abstract class Jugador {
	protected int posx;
	protected int posy;
	protected boolean tirando;

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
	
	public boolean tirando(){
		return tirando;
	}
}
