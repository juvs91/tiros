package shootgoal.controladores;

import java.io.IOException;
import java.io.InputStream;

import shootgoal.modelos.Juego;
import shootgoal.modelos.Jugador;
import shootgoal.modelos.Porteria;
import shootgoal.modelos.Portero;
import shootgoal.modelos.Tirador;
import shootgoal.modelos.Jugador.PosicionRelativa;
import shootgoal.vistas.PorteroView;



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
 * 
 * Controla los moviemientos del portero y hacia donde se aventara
 *
 */
public class ControladorPortero extends Activity implements OnTouchListener {
	WakeLock wakeLock;
	PorteroView view;
	public Tirador tirador;
	public Portero portero;
	public Porteria porteria;
	float scaleX, scaleY;
	public enum ControllerStatus{
		juegoAnteriorAnimacion, juegoActualDecidiendoPosicion, juegoActualAnimacion
	} 
	public ControllerStatus status;
	public Bitmap botonGo;
	public Bitmap letrasGol;
	public Bitmap letrasFallo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AssetManager assetManager = getAssets();
		InputStream is;
		Bitmap cuadro = null;
		
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		int posTiro = prefs.getInt("posTiro", 0);
		tirador.posRelativa = Jugador.PosicionRelativa.getPosicionRelativa(posTiro);
		
		try {
			is = assetManager.open("fondo/FondoShotComp.png");
			//porteriaImagen=assetManager.open("PorteriaAlone.png");
			cuadro = BitmapFactory.decodeStream(is);
			is = assetManager.open("porteritoNaranjaGo.png");
			botonGo = BitmapFactory.decodeStream(is);			
			is = assetManager.open("LetrasGolRojas.png");
			letrasGol = BitmapFactory.decodeStream(is);
			
			is = assetManager.open("missed.png");
			letrasFallo = BitmapFactory.decodeStream(is);
			//cuadroPorteria=BitmapFactory.decodeStream(porteriaImagen);
			is.close();
			//porteriaImagen.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//is.close();
		} 
		
		view = new PorteroView(this);
		view.fondo = cuadro;
		view.botonGo = botonGo;
		//view.letrasGol = letrasGol;
		//view.porteria=cuadroPorteria;
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
		
		Point balonPos = new Point(view.frameBuffer.getWidth()/2, view.frameBuffer.getHeight()/2+120);
		tirador = new Tirador(balonPos, getAssets());
		
		Point porteriaPos = new Point(view.frameBuffer.getWidth()/2, view.frameBuffer.getHeight()/2-25);
		porteria = new Porteria(porteriaPos, getAssets());
		//escoger portero solido posicion neutral
		portero.animacion.indice = 1;
		status = ControllerStatus.juegoActualDecidiendoPosicion;
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
		punto.y = view.frameBuffer.getHeight()/2;
		
		switch(posicionRelativa){
			case IZQUIERDA:
				punto.x = (int)(porteriaScaledWidth/3) - 25 + porteriaOrigenX;
			break;
			case CENTRO:
				punto.x = (int)(porteriaScaledWidth/2) + porteriaOrigenX;
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
				return Portero.PosicionRelativa.IZQUIERDA;
			} else if(touchPoint.x >= porteriaExtremoX-tamanoDivisionX && touchPoint.x <= porteriaExtremoX){
				/*if(!view.bloqueado){
					view.paraPorDerecha = true;
				}*/
				return Portero.PosicionRelativa.DERECHA;
			} else {
				return Portero.PosicionRelativa.CENTRO;
			}
		}
	}
	
	public void saveDataToDatabase(){
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		int idPortero = prefs.getInt("id", 0);
		int idJugador1 = prefs.getInt("idJugador1", 0);
		int idJugador2 = prefs.getInt("idJugador2", 0);
		int idTirador;
		int puntajeJugador1 = prefs.getInt("puntajeJugador1", 0);
		int puntajeJugador2 = prefs.getInt("puntajeJugador2", 0);
		if(idPortero!=idJugador1){
			idTirador = idJugador1;
			if(tirador.posRelativa!=portero.posRelativa){
				puntajeJugador1++;
			}
		} else {
			idTirador = idJugador2;
			if(tirador.posRelativa!=portero.posRelativa){
				puntajeJugador2++;
			}
		}
		int status = prefs.getInt("status", 0);
		
		status++;
		/*Tirador tirador = new Tirador();*/
		tirador.setId(idTirador);
		/*Portero portero = new Portero();*/
		portero.setId(idPortero);
		int tiroPos = Jugador.PosicionRelativa.getPosicionValue(tirador.posRelativa);
		int paradaPos = Jugador.PosicionRelativa.getPosicionValue(portero.posRelativa);
		
		Juego juego = new Juego(tirador,portero,status,tiroPos,paradaPos,true,puntajeJugador1,puntajeJugador2);
		juego.update();
		//finish();
	}
	
	/**
	 * Mueve la posicion del portero hacia el punto 
	 * @param touchPoint
	 */
	public void moverPorteroAPosicionElegida(Point touchPoint){
		if(!view.bloqueado){
			Point punto = null;
			Portero.PosicionRelativa posRel = posicionRelativaBasadaEnPuntoReal(touchPoint);
			if(posRel!=Portero.PosicionRelativa.FUERA){
				punto = obtenerCoordenadasReales(posRel);
				punto.set(punto.x-portero.animacion.getCuadro().getWidth()/3/2,punto.y-portero.animacion.getCuadro().getHeight()/3/2);
				portero.posicion = punto;
				portero.posRelativa = posRel;
				return;
			}
			//si se dio click en el boton
			if((touchPoint.x >= view.frameBuffer.getWidth()-botonGo.getWidth()/5-20 && touchPoint.x <= view.frameBuffer.getWidth()-20)
					&& (touchPoint.y >= view.frameBuffer.getHeight()-botonGo.getHeight()/5-20 && touchPoint.y <= view.frameBuffer.getHeight()-20)){
				saveDataToDatabase();
				if(portero.posRelativa == null){
					portero.posRelativa = Jugador.PosicionRelativa.CENTRO;
				}
				switch(portero.posRelativa){
					case CENTRO:
						
						view.bloqueado = true;
						//tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
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
						//tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
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
						}*/
						
						view.paraPorIzquierda = true;
						break;
					case DERECHA:
						view.bloqueado = true;
						//tirador.posRelativa = Jugador.PosicionRelativa.DERECHA;
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
				}
			}
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event){
		//Point point=new Point();
		//point.set((int)(event.getX()*scaleX-portero.animacion.getCuadro().getWidth()/3/2),(int)(event.getY()*scaleY-portero.animacion.getCuadro().getHeight()/3/2));
		//Log.v("tiro x", String.valueOf(point.x));
		//Log.v("tiro y", String.valueOf(point.y));
		Point touchPoint = new Point();
		touchPoint.set((int)(event.getX()*scaleX), (int)(event.getY()*scaleY));
		
		if(status==ControllerStatus.juegoActualDecidiendoPosicion){
			moverPorteroAPosicionElegida(touchPoint);
		}
		
		return true;
	}
}
