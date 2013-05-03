package shootgoal.controladores;

import shootgoal.build.R;

import java.io.IOException;
import java.io.InputStream;

import shootgoal.vistas.MenuView;

import shootgoal.build.R;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ControladorMenu extends Activity implements OnClickListener {

	MenuView view;
	float scaleX, scaleY;
	boolean esPortero = true;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pr);
		AssetManager assetManager = getAssets();
		InputStream is;
		Bitmap cuadro = null;
		ImageButton botonPlay= ((ImageButton) findViewById(R.id.butPlay));
		botonPlay.setOnClickListener(this);
	
		
		try {
			is = assetManager.open("fondo/principal.jpg");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		view = new MenuView(this);
		view.fondo = cuadro;		

		Point point = new Point();

		getWindowManager().getDefaultDisplay().getSize(point);

		//Escala en x basada en el ancho del buffer y el ancho de la pantalla del dispositivo
		scaleX = (float) view.frameBuffer.getWidth() / point.x;
		//Escala en y basada en el alto del buffre y el alto de la pantalla del dispositivo
		scaleY = (float) view.frameBuffer.getHeight() / point.y;

		//setContentView(view);
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId()==R.id.butPlay){
			Intent launchGame = new Intent(this, ControladorNuevoJuego.class);
			startActivity(launchGame);	
		}
		
		
	}
}
