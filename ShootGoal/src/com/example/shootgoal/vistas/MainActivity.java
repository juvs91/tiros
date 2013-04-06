package com.example.shootgoal.vistas;


import java.io.IOException;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.shootgoal.R;
import com.example.shootgoal.controladores.ControladorNuevoJuego;
import com.example.shootgoal.controladores.ControladorPortero;
import com.example.shootgoal.controladores.ControladorTirador;
public class MainActivity extends Activity implements OnClickListener {

	int vista = 0;//vista inicial 
	//boolean esPortero = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AssetManager assetManager = getAssets();
		InputStream is;
		Bitmap cuadro = null;
		try {
			is = assetManager.open("fondo/principal.jpg");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		setContentView(R.layout.pr);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


		ImageButton botonPlay= ((ImageButton) findViewById(R.id.butPlay));
		botonPlay.setOnClickListener(this);

		
	}


	@Override
	public void onClick(View arg0) {

		/*Intent launchGame = new Intent(this,ControladorTirador.class);
		startActivity(launchGame);	

		launchGame = null;

		if(esPortero){
			launchGame = new Intent(this, ControladorPortero.class);
		} else {
			launchGame = new Intent(this, ControladorTirador.class);
		}*/
		Intent launchGame = new Intent(this, ControladorNuevoJuego.class);
		startActivity(launchGame);
		//esPortero=!esPortero;

	}

}
