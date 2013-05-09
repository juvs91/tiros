package build.controladores;

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

import build.controladores.ControladorPortero.ControllerStatus;
import build.modelos.Juego;
import build.modelos.Jugador;
import build.modelos.Porteria;
import build.modelos.Portero;
import build.modelos.Tirador;
import build.vistas.TiradorView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * Es el controlador de todas las acciones del tirador
 * @author CookiesNCode
 *
 */
public class ControladorTirador extends Activity implements OnTouchListener{
	WakeLock wakeLock;
	public TiradorView viewTirador;
	public Portero portero;
	public Porteria porteria;
	float scaleX, scaleY;
	public Tirador tirador;
	Point point=new Point();
	private int anchoCancha=0;
	private int altoCancha=0;
	private int tercera;
	Point balonPos;
	Bitmap botonGo;
	boolean bloqueado;
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AssetManager assetManager = getAssets();
		InputStream is,porteriaImagen;
		bloqueado = false;
		Bitmap cuadro = null, cuadroPorteria = null;
		try {
			is = assetManager.open("fondo/FondoShotComp.png");
			cuadro = BitmapFactory.decodeStream(is);
			porteriaImagen = assetManager.open("PorteriaAlone.png");
			cuadroPorteria=BitmapFactory.decodeStream(porteriaImagen);
			is = assetManager.open("porteritoNaranjaGo.png");
			botonGo = BitmapFactory.decodeStream(is);

			is.close();
			porteriaImagen.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		}		

		viewTirador= new TiradorView(this);
		viewTirador.controlador = this;
		viewTirador.fondo =cuadro;
		viewTirador.porteria=cuadroPorteria;
		viewTirador.setOnTouchListener(this);
		viewTirador.botonGo = botonGo;


		Point pointBalon=new Point();

		getWindowManager().getDefaultDisplay().getSize(pointBalon);

		scaleX = (float) viewTirador.frameBuffer.getWidth() / pointBalon.x;
		scaleY = (float) viewTirador.frameBuffer.getHeight() / pointBalon.y;

		Point porteroPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight()/2);
		portero = new Portero(porteroPos, getAssets());
		//escoger el portero transparente
		portero.animacion.indice = 0;
		//viewTirador.setPorteroScreenContext(portero.animacion.getCuadro(), portero.posicion);

		balonPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight()/2+120);
		tirador = new Tirador(balonPos, getAssets());

		Point porteriaPos = new Point(viewTirador.frameBuffer.getWidth()/2, viewTirador.frameBuffer.getHeight()/2-25);
		porteria = new Porteria(porteriaPos, getAssets());
		//viewTirador.setTiradorScreenContext(tirador.animacion.getCuadro(), tirador.posicion,tirador.posicion,tirador.posicion);
		setContentView(viewTirador);

	}


	@Override
	protected void onPause() {
		super.onPause();
		//Pausa la vista de la Actividad
		viewTirador.pause();
		//Libera el candado que protege la pantalla
	}

	/**
	 * Metodo onResume sobrescrito de la clase Activity
	 * LLamado cuando la Actividad vuelve a primer plano
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//Reanuda la vista de la Actividad
		viewTirador.resume();
		//Retoma el candado que protege a la pantalla
	}

	/**
	 * Mueve el balon hacia la posicion y actualiza la posicion del tiro
	 * en el servidor
	 * @param point
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		Point touchPoint = new Point();
		touchPoint.set((int)(event.getX()*scaleX), (int)(event.getY()*scaleY));

		moverBalonAPosicionElegida(touchPoint);

		return true;
		// TODO Auto-generated method stub
	}

	/**
	 * Obtiene las coordenadas reales del punto IZQ DER o CENTRO
	 * para posicionar al portero
	 * @param posicionRelativa
	 * @return
	 */
	public Point obtenerCoordenadasReales(Portero.PosicionRelativa posicionRelativa){
		Point punto = new Point();

		int porteriaOrigenX = porteria.posicion.x;
		int porteriaScaledWidth = porteria.imagen.getWidth()/2;
		int porteriaExtremoX = porteriaOrigenX + porteriaScaledWidth;
		punto.y = viewTirador.frameBuffer.getHeight()/2;

		switch(posicionRelativa){
		case IZQUIERDA:
			punto.x = (int)(porteriaScaledWidth/3) - 25 + porteriaOrigenX;
			break;
		case CENTRO:
			punto.x = porteriaScaledWidth/2 + porteriaOrigenX;
			break;
		case DERECHA:
			punto.x = porteriaExtremoX - (int)(porteriaScaledWidth/3) + 25;
			break;
		}
		return punto;
	}

	/**
	 * Regresa el punto de las coordenadas en IZQ, DER o CENTRO
	 * @param touchPoint
	 * @return
	 */
	public Portero.PosicionRelativa posicionRelativaBasadaEnPuntoReal(Point touchPoint){
		int porteriaOrigenX = porteria.posicion.x;
		int porteriaExtremoX = porteriaOrigenX + porteria.imagen.getWidth()/2;
		int porteriaOrigenY = porteria.posicion.y;
		int porteriaExtremoY = porteriaOrigenY + porteria.imagen.getHeight()/2;
		if((touchPoint.x < porteriaOrigenX) || (touchPoint.x > porteriaExtremoX) || (touchPoint.y < porteriaOrigenY) || (touchPoint.y > porteriaExtremoY)){
			Log.v("dentroDePorteria","no");
			return Portero.PosicionRelativa.FUERA;

		} else {
			Log.v("dentroDePorteria", "si");
			int tamanoDivisionX = porteria.imagen.getWidth()/2/3;
			if(touchPoint.x >= porteriaOrigenX && touchPoint.x <= porteriaOrigenX+tamanoDivisionX){

				Log.v("posicion", "izquierda");
				return Tirador.PosicionRelativa.IZQUIERDA;
			} else if(touchPoint.x >= porteriaExtremoX-tamanoDivisionX && touchPoint.x <= porteriaExtremoX){

				Log.v("posicion", "derecha");
				return Tirador.PosicionRelativa.DERECHA;
			} else {
				Log.v("posicion", "centro");
				return Tirador.PosicionRelativa.CENTRO;
			}
		}
	}

	public void guardarTiro(){
		//guardar en base de datos y al terminar hacer un callback a finish();
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		int idTirador = prefs.getInt("id", 0);
		int idJugador1 = prefs.getInt("idJugador1", 0);
		int idJugador2 = prefs.getInt("idJugador2", 0);
		int idPortero;
		int puntajeJugador1 = prefs.getInt("puntajeJugador1", 0);
		int puntajeJugador2 = prefs.getInt("puntajeJugador2", 0);
		if(idTirador!=idJugador1){
			idPortero = idJugador1;
		} else {
			idPortero = idJugador2;
		}
		int status = prefs.getInt("status", 0);

		if(status == 4){
			status = 1;
		} else {
			status++;
		}
		tirador.setId(idTirador);
		portero.setId(idPortero);
		int tiroPos = Jugador.PosicionRelativa.getPosicionValue(tirador.posRelativa);

		Juego juego = new Juego(tirador,portero,status,tiroPos,0,true,puntajeJugador1,puntajeJugador2);
		juego.update();
		finish();
	}

	public void moverBalonAPosicionElegida(Point touchPoint){
		Point punto = null;
		Tirador.PosicionRelativa posRel = posicionRelativaBasadaEnPuntoReal(touchPoint);
		if(posRel!=Tirador.PosicionRelativa.FUERA){
			punto = obtenerCoordenadasReales(posRel);
			punto.set(punto.x-tirador.animacion.getCuadro().getWidth()/3/2,punto.y-tirador.animacion.getCuadro().getHeight()/3/2);
			tirador.posicion = punto;
			tirador.posRelativa = posRel;
			return;
		}
		//si se dio click en el boton
		if((touchPoint.x >= viewTirador.frameBuffer.getWidth()-botonGo.getWidth()/5-20 && touchPoint.x <= viewTirador.frameBuffer.getWidth()-20)
				&& (touchPoint.y >= viewTirador.frameBuffer.getHeight()-botonGo.getHeight()/5-20 && touchPoint.y <= viewTirador.frameBuffer.getHeight()-20)){
			if(tirador.posRelativa == null){
				tirador.posRelativa = Jugador.PosicionRelativa.FUERA;
			}
			if(!bloqueado && tirador.posRelativa != Tirador.PosicionRelativa.FUERA){
				bloqueado = true;
				guardarTiro();
			}
		}
	}



}
