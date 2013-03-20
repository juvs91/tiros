package com.example.shootgoal;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.WindowManager;

public class ControladorTirador extends Activity {
	
	WakeLock wakeLock;
	TiradorView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		//Evita que el dispositivo se duerma
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        
        AssetManager assetManager = getAssets();
        InputStream is;
        Bitmap cuadro = null;
		try {
			is = assetManager.open("FondoShotComp.png");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		
		
		
        
        view = new TiradorView(this);
        view.fondo = cuadro;
        //wakeLock = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "Test");
		
		setContentView(view);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		
		
		
		return true;
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
    public void tiro(){
    	try{
    		
    		
    	}catch(Exception e){
    		
    	}
    	
    }
    
    
}
