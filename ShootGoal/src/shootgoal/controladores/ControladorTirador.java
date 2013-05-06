package shootgoal.controladores;

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

import shootgoal.controladores.ControladorPortero.ControllerStatus;
import shootgoal.modelos.Juego;
import shootgoal.modelos.Jugador;
import shootgoal.modelos.Porteria;
import shootgoal.modelos.Portero;
import shootgoal.modelos.Tirador;
import shootgoal.vistas.TiradorView;
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
 * @author Tuye
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
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AssetManager assetManager = getAssets();
		InputStream is,porteriaImagen;
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
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		viewTirador.pause();
		//Libera el candado que protege la pantalla
		//wakeLock.release();
	}

	/**
	 * Metodo onResume sobrescrito de la clase Activity
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
	
	/**
	 * Mueve el balon hacia la posicion y actualiza la posicion del tiro
	 * en el servidor
	 * @param point
	 */
	/*private void tiro(Point point){
		try{
		/*
		anchoCancha=viewTirador.porteria.getWidth();
		altoCancha=viewTirador.porteria.getHeight();
		*
		
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
            Log.v("RESPONSE","fallo");
		}

	}*/
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {

    	/*Point touchPoint = new Point();
		touchPoint.set((int)(event.getX()*scaleX-tirador.animacion.getCuadro().getWidth()/3/2),
				(int)(event.getY()*scaleY-tirador.animacion.getCuadro().getHeight()/3/2));

    	Point point=new Point();
		point.set((int)(event.getX()*scaleX-tirador.animacion.getCuadro().getWidth()/3/2),(int)(event.getY()*scaleY-tirador.animacion.getCuadro().getHeight()/3/2));

		
		Log.w("tiro x", String.valueOf(touchPoint.x));
		Log.w("tiro y", String.valueOf(touchPoint.y));
		
		Point diferencia = new Point();
		
		if(!tirar(point)) {
			diferencia.set((int) (point.x - balonPos.x), point.y - balonPos.y);
			tirador.setPosicion(diferencia);
			//viewTirador.setTiradorScreenContext(tirador.animacion.getCuadro(), touchPoint,tirador.posicion,balonPos);

		tirador.setPosicion(diferencia);

		//tiro(point);
		} else {
			Log.e("App", "Si Tiro");
			
			Tirador.PosicionRelativa posRel = posicionRelativaBasadaEnPuntoReal(touchPoint);
			Log.e("App", "lala: " + posRel);
			
		}*/
		
		Point touchPoint = new Point();
		touchPoint.set((int)(event.getX()*scaleX), (int)(event.getY()*scaleY));
		
		//if(status==ControllerStatus.juegoActualDecidiendoPosicion){
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
		//int porteriaOrigenY = porteria.posicion.y;
		//int porteriaScaledHeight = porteria.imagen.getHeight()/2;
		//int porteriaExtremoY = porteriaOrigenY + porteriaScaledHeight;
		
		//punto.y = (porteriaExtremoY-porteriaOrigenY)/2+porteriaOrigenY;
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
				/*if(!view.bloqueado){
					view.paraPorIzquierda = true;
				}*/
				Log.v("posicion", "izquierda");
				return Tirador.PosicionRelativa.IZQUIERDA;
			} else if(touchPoint.x >= porteriaExtremoX-tamanoDivisionX && touchPoint.x <= porteriaExtremoX){
				/*if(!view.bloqueado){
					view.paraPorDerecha = true;
				}*/
				Log.v("posicion", "derecha");
				return Tirador.PosicionRelativa.DERECHA;
			} else {
				Log.v("posicion", "centro");
				return Tirador.PosicionRelativa.CENTRO;
			}
		}
	}

	/*private boolean tirar(Point touchPoint) {
		// TODO Auto-generated method stub
		Bitmap botonGo = viewTirador.botonGo;
		if((touchPoint.x >= viewTirador.frameBuffer.getWidth()-botonGo.getWidth()/5-20 &&
				touchPoint.x <= viewTirador.frameBuffer.getWidth()-20)
				&& (touchPoint.y >= viewTirador.frameBuffer.getHeight()-botonGo.getHeight()/5-20 &&
				touchPoint.y <= viewTirador.frameBuffer.getHeight()-20)) {
			return true;
		}

		return false;
	}*/
	
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
		/*Tirador tirador = new Tirador();*/
		tirador.setId(idTirador);
		/*Portero portero = new Portero();*/
		portero.setId(idPortero);
		int tiroPos = Jugador.PosicionRelativa.getPosicionValue(tirador.posRelativa);
		
		Juego juego = new Juego(tirador,portero,status,tiroPos,0,true,puntajeJugador1,puntajeJugador2);
		juego.update();
		finish();
	}
	
	public void moverBalonAPosicionElegida(Point touchPoint){
		//if(!viewTirador.bloqueado){
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
				if(tirador.posRelativa != Tirador.PosicionRelativa.FUERA){
					guardarTiro();
					//finish();
				}
				/*switch(portero.posRelativa){
					case CENTRO:
						view.bloqueado = true;
						tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
						Point posFinalBalon = obtenerCoordenadasReales(tirador.posRelativa);
						posFinalBalon.x -= tirador.animacion.getCuadro().getWidth()/3/2;
						posFinalBalon.y -= tirador.animacion.getCuadro().getHeight()/3/2;
						view.posFinalBalon = posFinalBalon;
						if(portero.posRelativa==tirador.posRelativa){
							view.letrasGol = letrasFallo;
						} else {
							view.letrasGol = letrasGol;
						}
						
						view.paraPorCentro = true;
						break;
					case IZQUIERDA: 
						view.bloqueado = true;
						tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
						posFinalBalon = obtenerCoordenadasReales(tirador.posRelativa);
						posFinalBalon.x -= tirador.animacion.getCuadro().getWidth()/3/2;
						posFinalBalon.y -= tirador.animacion.getCuadro().getHeight()/3/2;
						view.posFinalBalon = posFinalBalon;
						if(portero.posRelativa==tirador.posRelativa){
							view.letrasGol = letrasFallo;
						} else {
							view.letrasGol = letrasGol;
						}
						portero.posRelativa = Jugador.PosicionRelativa.CENTRO;
						punto = obtenerCoordenadasReales(portero.posRelativa);
						punto.set(punto.x-portero.animacion.getCuadro().getWidth()/3/2,punto.y-portero.animacion.getCuadro().getHeight()/3/2);
						portero.posicion = punto;
						/*tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
						Point posFinalBalon = obtenerCoordenadasReales(tirador.posRelativa);
						posFinalBalon.x -= tirador.animacion.getCuadro().getWidth()/3/2;
						posFinalBalon.y -= tirador.animacion.getCuadro().getHeight()/3/2;
						view.posFinalBalon = posFinalBalon;
						if(portero.posRelativa==tirador.posRelativa){
							view.letrasGol = letrasFallo;
						} else {
							view.letrasGol = letrasGol;
						}*
						
						view.paraPorIzquierda = true;
						break;
					case DERECHA:
						view.bloqueado = true;
						tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
						posFinalBalon = obtenerCoordenadasReales(tirador.posRelativa);
						posFinalBalon.x -= tirador.animacion.getCuadro().getWidth()/3/2;
						posFinalBalon.y -= tirador.animacion.getCuadro().getHeight()/3/2;
						view.posFinalBalon = posFinalBalon;
						if(portero.posRelativa==tirador.posRelativa){
							view.letrasGol = letrasFallo;
						} else {
							view.letrasGol = letrasGol;
						}
						portero.posRelativa = Jugador.PosicionRelativa.CENTRO;
						punto = obtenerCoordenadasReales(portero.posRelativa);
						punto.set(punto.x-portero.animacion.getCuadro().getWidth()/3/2,punto.y-portero.animacion.getCuadro().getHeight()/3/2);
						portero.posicion = punto;
						
						view.paraPorDerecha = true;
						
						break;
				}*/
			}
		}
	//}


}
