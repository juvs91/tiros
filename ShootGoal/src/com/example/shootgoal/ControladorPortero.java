package com.example.shootgoal;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class ControladorPortero extends Activity implements OnTouchListener {
	WakeLock wakeLock;
	PorteroView view;
	Portero portero;
	float scaleX, scaleY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AssetManager assetManager = getAssets();
        InputStream is;
        Bitmap cuadro = null;
		try {
			is = assetManager.open("fondo/FondoShotComp.png");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		
		
		
        
        view = new PorteroView(this);
        view.fondo = cuadro;		
		
		Point point = new Point();
		
		getWindowManager().getDefaultDisplay().getSize(point);
		
		//Escala en x basada en el ancho del buffer y el ancho de la pantalla del dispositivo
	      scaleX = (float) view.frameBuffer.getWidth() / point.x;
	      		//Escala en y basada en el alto del buffre y el alto de la pantalla del dispositivo
	      scaleY = (float) view.frameBuffer.getHeight() / point.y;
		//scaleX = (float) view.frameBuffer.getWidth()/getWindowManager().getDefaultDisplay().getWidth();
		//scaleY = (float) view.frameBuffer.getHeight() / getWindowManager().getDefaultDisplay().getHeight();
		
	      Point porteroPos = new Point(view.frameBuffer.getWidth()/2, view.frameBuffer.getHeight());
		portero = new Portero(porteroPos, getAssets());
		view.setPorteroScreenContext(portero.animacion.getCuadro(), portero.posicion);
		setContentView(view);
		
	}
	
	
	@Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }

    /**
     * Metodo onResume sobrescrito de la clase Activity
     * LLamado cuando la Actividad vuelve a primer plano
     */
    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }
    
    @Override
	public boolean onTouch(View v, MotionEvent event){
		//Maneja los eventos de contacto
		switch (event.getAction()) {
		//Cuando se hace contacto con el dedo
		case MotionEvent.ACTION_DOWN:
			//
			break;
			//Cuando se mueve el dedo haciendo contacto
		case MotionEvent.ACTION_MOVE:
			//
			break;
		case MotionEvent.ACTION_CANCEL:
			//
			break;
			//Cuando se deja de hacer contacto
		case MotionEvent.ACTION_UP:
			//Cambia la direcci√≥n de movimiento del objeto Elefante
			//dependiendo del punto de contacto
			portero.posicion.x = (int)(event.getX() * scaleX);
			portero.posicion.y = (int)(event.getY() * scaleY);
		}
		return true;
	}
}
