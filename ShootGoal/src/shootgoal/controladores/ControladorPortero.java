package shootgoal.controladores;

import java.io.IOException;
import java.io.InputStream;

import shootgoal.modelos.Portero;
import shootgoal.vistas.PorteroView;



import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class ControladorPortero extends Activity implements OnTouchListener {
	WakeLock wakeLock;
	PorteroView view;
	public Portero portero;
	float scaleX, scaleY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AssetManager assetManager = getAssets();
		InputStream is,porteriaImagen;
		Bitmap cuadro = null,cuadroPorteria=null;
		try {
			is = assetManager.open("fondo/FondoShotComp.png");
			porteriaImagen=assetManager.open("PorteriaAlone.png");
			cuadro = BitmapFactory.decodeStream(is);
			cuadroPorteria=BitmapFactory.decodeStream(porteriaImagen);
			is.close();
			porteriaImagen.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		view = new PorteroView(this);
		view.fondo = cuadro;	
		view.porteria=cuadroPorteria;
		view.controlador = this;
		view.setOnTouchListener(this);
		
		Point point = new Point();

		getWindowManager().getDefaultDisplay().getSize(point);

		//Escala en x basada en el ancho del buffer y el ancho de la pantalla del dispositivo
		scaleX = (float) view.frameBuffer.getWidth() / point.x;
		//Escala en y basada en el alto del buffre y el alto de la pantalla del dispositivo
		scaleY = (float) view.frameBuffer.getHeight() / point.y;

		Point porteroPos = new Point(view.frameBuffer.getWidth()/2, view.frameBuffer.getHeight()/2);
		portero = new Portero(porteroPos, getAssets());
		//escoger portero solido posicion neutral
		portero.animacion.indice = 1;
		//view.setPorteroScreenContext(portero.animacion.getCuadro(), portero.posicion);
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
		Point point=new Point();
		point.set((int)(event.getX()*scaleX-portero.animacion.getCuadro().getWidth()/3/2),(int)(event.getY()*scaleY-portero.animacion.getCuadro().getHeight()/3/2));
		if(!view.bloqueado){
			view.paraPorIzquierda = true;
		}
		Log.v("tiro x", String.valueOf(point.x));
		Log.v("tiro y", String.valueOf(point.y));
		//Maneja los eventos de contacto
		/*switch (event.getAction()) {
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
			//Cambia la direccin de movimiento del objeto Elefante
			//dependiendo del punto de contacto
			portero.posicion.x = (int)(event.getX() * scaleX);
			portero.posicion.y = (int)(event.getY() * scaleY);
		}*/
		return true;
	}
}
