package com.example.shootgoal.modelos;

import java.io.IOException;
import java.io.InputStream;

import com.example.shootgoal.AnimacionPortero;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Portero extends Jugador{
	
	public AnimacionPortero animacion;
	Bitmap cuadro;
	
	public Portero(Point porteroPos, AssetManager assetManager){
		AnimacionPortero animacionPor = new AnimacionPortero();
		try{
			InputStream is = null;
			for (int i = 1; i <= 1; i++) {
				is = assetManager.open("portero/Mono" + i + ".png");
				
			} 
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
			animacionPor.sumaCuadro(cuadro, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.animacion = animacionPor;
		porteroPos.x -= animacion.getCuadro().getWidth()/2;
		porteroPos.y -= animacion.getCuadro().getHeight()/2;
		this.posicion = porteroPos;
	}

}
