package com.example.shootgoal;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;

public class ControladorTirador extends Activity {

	WakeLock wakeLock;
	TiradorView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
		

	}


	@Override
	protected void onPause() {
		super.onPause();
		//Pausa la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		view.pause();
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
		view.resume();
		//Retoma el candado que protege a la pantalla
		//wakeLock.acquire();
	}

	/*private float x;
	private float y;

	public ControladorTirador(){
		this(0,0);
	}
	public ControladorTirador(float x,float y){
		this.x=x;
		this.y=y;
	}
	public boolean onTouchEvent(MotionEvent e){
		x=e.getX();
		y=e.getY();


		return true;

	}*/
	private void tiro(){
		try{


		}catch(Exception e){

		}

	}
	//este metodo crea el fondo de pantalla y toma posecion de toda la pantalla al iniciar el controlador 
	public void init(){
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
		view = new TiradorView(this);
		view.fondo = cuadro;		
		setContentView(view);


	}


}
