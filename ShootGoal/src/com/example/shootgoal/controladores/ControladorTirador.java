package com.example.shootgoal.controladores;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.shootgoal.modelos.Portero;
import com.example.shootgoal.modelos.Tirador;
import com.example.shootgoal.vistas.TiradorView;

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

public class ControladorTirador extends Activity implements OnTouchListener{
	WakeLock wakeLock;
	TiradorView viewTirador;
	Portero portero;
	float scaleX, scaleY;
	Tirador tirador;
	Point point=new Point();
	private int anchoCancha=0;
	private int altoCancha=0;
	private int tercera;
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
		
		
			
		viewTirador= new TiradorView(this);
		viewTirador.fondo =cuadro;
		viewTirador.porteria=cuadroPorteria;
		viewTirador.setOnTouchListener(this);
		
		Point pointBalon=new Point();

		getWindowManager().getDefaultDisplay().getSize(pointBalon);

		
		
		scaleX=(float) viewTirador.frameBuffer.getWidth() / pointBalon.x;
		scaleY=(float) viewTirador.frameBuffer.getHeight() / pointBalon.y;


		Point porteroPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight()/2);
		portero = new Portero(porteroPos, getAssets());
		//escoger el portero transparente
		portero.animacion.indice = 0;
		viewTirador.setPorteroScreenContext(portero.animacion.getCuadro(), portero.posicion);
		
		
		Point balonPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight()/2+120);
		tirador = new Tirador(balonPos, getAssets());
		viewTirador.setTiradorScreenContext(tirador.animacion.getCuadro(), tirador.posicion);
		setContentView(viewTirador);


	}


	@Override
	protected void onPause() {
		super.onPause();
		//Pausa la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		viewTirador.pause();
		//Libera el candado que protege la pantalla
		//wakeLock.release();
	}

	/**
	 * M������todo onResume sobrescrito de la clase Activity
	 * LLamado cuando la Actividad vuelve a primer plano
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//Reanuda la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		viewTirador.resume();
		//Retoma el candado que protege a la pantalla
		//wakeLock.acquire();
	}
	
	private void tiro(Point point){
		try{
		/*
		anchoCancha=viewTirador.porteria.getWidth();
		altoCancha=viewTirador.porteria.getHeight();
		tercera=anchoCancha/3;*/
		
		//se empiesa a madnar los datos del tiro 
		HttpClient client = new DefaultHttpClient();  
		String postURL = "http://localhost/servidorShootGoal/tirador.php";
		HttpPost post = new HttpPost(postURL); 
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tiro", "valorDelTiro"));
        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
        post.setEntity(ent);
        HttpResponse responsePOST = client.execute(post);  
        HttpEntity resEntity = responsePOST.getEntity();  
        Log.v("entro en tiro","asdf");
        if (resEntity != null) {    
            Log.i("RESPONSE",EntityUtils.toString(resEntity));
        }

		}catch(Exception e){

		}

	}


	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
    	Point point=new Point();
		point.set((int)(event.getX()*scaleX-tirador.animacion.getCuadro().getWidth()/3/2),(int)(event.getY()*scaleY-tirador.animacion.getCuadro().getHeight()/3/2));
		tirador.setPosicion(point);
		viewTirador.setTiradorScreenContext(tirador.animacion.getCuadro(), tirador.posicion);
		tiro(point);
		Log.v("tiro x", String.valueOf(point.x));
		Log.v("tiro y", String.valueOf(point.y));
		return true;
		// TODO Auto-generated method stub
	}


}
