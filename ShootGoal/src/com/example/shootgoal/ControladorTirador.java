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
import android.view.WindowManager;

public class ControladorTirador extends Activity {
	WakeLock wakeLock;
	PorteroView viewPortero;
	TiradorView viewTirador;
	Portero portero;
	float scaleX, scaleY,scaleXBalon, scaleYBalon;
	Tirador tirador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		viewPortero = new PorteroView(this);
		viewPortero.fondo = cuadro;		
		viewTirador= new TiradorView(this);

		Point point = new Point();
		Point pointBalon=new Point();

		getWindowManager().getDefaultDisplay().getSize(point);

		//Escala en x basada en el ancho del buffer y el ancho de la pantalla del dispositivo
		scaleX = (float) viewPortero.frameBuffer.getWidth() / point.x;
		//Escala en y basada en el alto del buffre y el alto de la pantalla del dispositivo
		scaleY = (float) viewPortero.frameBuffer.getHeight() / point.y;
		
		scaleXBalon=(float) viewTirador.frameBuffer.getWidth() / pointBalon.x;
		scaleYBalon=(float) viewTirador.frameBuffer.getHeight() / pointBalon.y;


		Point porteroPos = new Point(viewPortero.frameBuffer.getWidth()/2, viewPortero.frameBuffer.getHeight());
		portero = new Portero(porteroPos, getAssets());
		viewPortero.setPorteroScreenContext(portero.animacion.getCuadro(), portero.posicion);
		setContentView(viewPortero);

		Point balonPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight());
		tirador = new Tirador(balonPos, getAssets());
		viewTirador.setTiradorScreenContext(tirador.animacion.getCuadro(), tirador.posicion);
		setContentView(viewTirador);


	}


	@Override
	protected void onPause() {
		super.onPause();
		//Pausa la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		viewPortero.pause();
		//Libera el candado que protege la pantalla
		//wakeLock.release();
	}

	/**
	 * M��todo onResume sobrescrito de la clase Activity
	 * LLamado cuando la Actividad vuelve a primer plano
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//Reanuda la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		viewPortero.resume();
		//Retoma el candado que protege a la pantalla
		//wakeLock.acquire();
	}

	private void tiro(){
		try{


		}catch(Exception e){

		}

	}


}
