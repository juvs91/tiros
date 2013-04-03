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

public class NuevoJuegoControlador  extends Activity  {
	WakeLock wakeLock;
	TiradorView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
		
	}
	public void init(){
		//setContentView(R.layout.activity_main);
		   getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	       AssetManager assetManager = getAssets();
	       InputStream is;
	       Bitmap cuadro = null;
			try {
				is = assetManager.open("fondo/fondoShotComp.png");
				cuadro = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//is.close();
			}
			
	       view = new PorteroView(this);
	       view.fondo = cuadro;		
			setContentView(view);
	}
	
}
