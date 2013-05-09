package build.vistas;


import build.controladores.ControladorPortero;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * Renderiza todo lo que tiene que ver con el portero
 *
 */
public class PorteroView extends SurfaceView implements Runnable {

	Thread renderThread = null;             //Thread de la vista
	SurfaceHolder holder;                   //Contenedor de la vista
	Canvas canvas;                          //Canvas para dibujar
	volatile boolean running = false;       //Bandera para conocer el estado de la Actividad
	float tiempoTick = 0, tick = 0.1f;      //Controladores de tiempo
	public Bitmap frameBuffer;				//Objetos Bitmap para el manejo de imagenes
	public Bitmap fondo;
	public ControladorPortero controlador;
	public boolean paraPorIzquierda = false;
	public boolean paraPorDerecha = false;
	public boolean paraPorCentro = false;
	public boolean bloqueado = false;
	public boolean balonBloqueado = false;
	public Bitmap botonGo;
	public Bitmap letrasGol;
	public Point posFinalBalon;
	public float tiempoAux;
	public int iteraciones;
	public int alfa;

	public PorteroView(Context context) {
		super(context);

		//Determina la orientacion del dispositivo y crea un buffer en base a esta
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		//Ancho del buffer
		int frameBufferWidth = isLandscape ? 480 : 320;
		//Alto del buffer
		int frameBufferHeight = isLandscape ? 320 : 480;
		//Crea el buffer
		frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);

		holder = getHolder();
		//Crea un nuevo objeto canvas con el buffer creado en la Actividad
		canvas = new Canvas(frameBuffer);
		iteraciones = 1;
		// TODO Auto-generated constructor stub
	}

	public PorteroView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PorteroView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Objeto Rect crea un rectngulo
		Rect dstRect = new Rect();
		long tiempoI = System.nanoTime();
		//Obtiene el tiempo actual
		//long tiempoI = System.nanoTime();
		//El ciclo se ejecuta cuando la Actividad esta en ejecucn
		while (running) {
			//Verifica que exista una vista vlida
			if(!holder.getSurface().isValid())
				continue;

			//Calcula el tiempo transcurrido
			float tiempo = (System.nanoTime() - tiempoI) / 1000000000.0f;
			//Obtiene el tiempo actual
			tiempoI = System.nanoTime();

			Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
			canvas.drawBitmap(resized, 0, 0, null);

			Bitmap porteriaImagen = controlador.porteria.imagen;
			resized= Bitmap.createScaledBitmap(porteriaImagen, porteriaImagen.getWidth()/2,porteriaImagen.getHeight()/2, true);
			canvas.drawBitmap(resized, controlador.porteria.posicion.x , controlador.porteria.posicion.y,null);

			if(!bloqueado){
				resized = Bitmap.createScaledBitmap(botonGo, botonGo.getWidth()/5, botonGo.getHeight()/5, true);
				canvas.drawBitmap(resized, frameBuffer.getWidth()-resized.getWidth()-20, frameBuffer.getHeight()-resized.getHeight()-20, null);
			}

			if(paraPorCentro){
				controlador.portero.animacion.pararEnElCentro(tiempo);
				bloqueado = paraPorCentro = controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo);
				int mostrar = mostrarLetreroResultados(tiempo);
				if(mostrar>0){
					resized = Bitmap.createScaledBitmap(letrasGol, (int)(letrasGol.getWidth()/1.5), (int)(letrasGol.getHeight()/1.5), true);
					Paint paint = new Paint();
					paint.setAlpha(mostrar);
					canvas.drawBitmap(resized, (int)(frameBuffer.getWidth()/2-letrasGol.getWidth()/2/1.5), (int)(frameBuffer.getHeight()/2-letrasGol.getHeight()/2/1.5-20), paint);
				}
				/*if(!balonBloqueado && !controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo)){
            		balonBloqueado = true;
            	}*/
				if(!bloqueado){
					controlador.finish();

					//balonBloqueado = false;
				}
			}

			if(paraPorIzquierda){
				//bloqueado = paraPorIzquierda = controlador.portero.animacion.pararEnLaIzquierda(tiempo);
				controlador.portero.animacion.pararEnLaIzquierda(tiempo);
				bloqueado = paraPorIzquierda = controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo);
				int mostrar = mostrarLetreroResultados(tiempo);
				if(mostrar>0){
					resized = Bitmap.createScaledBitmap(letrasGol, (int)(letrasGol.getWidth()/1.5), (int)(letrasGol.getHeight()/1.5), true);
					Paint paint = new Paint();
					paint.setAlpha(mostrar);
					canvas.drawBitmap(resized, (int)(frameBuffer.getWidth()/2-letrasGol.getWidth()/2/1.5), (int)(frameBuffer.getHeight()/2-letrasGol.getHeight()/2/1.5-20), paint);
				}
				/*if(!balonBloqueado && !controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo)){
            		balonBloqueado = true;
            	}*/
				if(!bloqueado){
					controlador.finish();

					//balonBloqueado = false;
				}
			}

			if(paraPorDerecha){
				//bloqueado = paraPorDerecha = controlador.portero.animacion.pararEnLaDerecha(tiempo);
				controlador.portero.animacion.pararEnLaDerecha(tiempo);
				bloqueado = paraPorDerecha = controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo);
				/*if(!balonBloqueado && !controlador.tirador.animacion.moverBalonAPosicion(posFinalBalon, tiempo)){
            		balonBloqueado = true;
            	}*/
				int mostrar = mostrarLetreroResultados(tiempo);
				if(mostrar>0){
					resized = Bitmap.createScaledBitmap(letrasGol, (int)(letrasGol.getWidth()/1.5), (int)(letrasGol.getHeight()/1.5), true);
					Paint paint = new Paint();
					paint.setAlpha(mostrar);
					canvas.drawBitmap(resized, (int)(frameBuffer.getWidth()/2-letrasGol.getWidth()/2/1.5), (int)(frameBuffer.getHeight()/2-letrasGol.getHeight()/2/1.5-20), paint);
				}

				if(!bloqueado){
					controlador.finish();
					//balonBloqueado = false;
				}
			}

			Bitmap porteroImagen = controlador.portero.animacion.getCuadro();
			resized = Bitmap.createScaledBitmap(porteroImagen, porteroImagen.getWidth()/3, porteroImagen.getHeight()/3, true);
			canvas.drawBitmap(resized, controlador.portero.posicion.x, controlador.portero.posicion.y, null);
			Canvas pantalla = holder.lockCanvas();

			Bitmap balonImagen = controlador.tirador.animacion.getCuadro();
			resized = Bitmap.createScaledBitmap(balonImagen, balonImagen.getWidth()/3, balonImagen.getHeight()/3, true);
			canvas.drawBitmap(resized, controlador.tirador.posicion.x, controlador.tirador.posicion.y, null);



			//Determina la resolucin de la pantalla
			pantalla.getClipBounds(dstRect);
			//Dibuja el buffer en la pantalla con el tamao de la pantalla
			pantalla.drawBitmap(frameBuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(pantalla);
		}

	}
	/**
	 * GEnera un letrero de si anotaste o fallaste
	 * @param tiempo
	 * @return
	 */
	public synchronized int mostrarLetreroResultados(double tiempo){

		if(iteraciones == 1){
			alfa = 0;
		}

		if (iteraciones >= 14) {
			return 255;
		}


		if(iteraciones>=11){

			alfa += 255/4;

		}
		iteraciones++;

	return alfa;
	}

	/**
	 * Metodo resume
	 * Llamado cuando la Actividad vuelve a primer plano,
	 * inicia el thread de la vista
	 */
	public void resume() {
		//La bandera indica que la Actividad esta en ejecucion

		running = true;
		//Crea un nuevo thread para la vista
		renderThread = new Thread(this);
		//Inicializa el thread de la vista
		renderThread.start();
	}

	public void pause() {
		//La bandera indica que la Actividad no esta en ejecucin

		running = false;
		//Espera a que el thread de la vista se detenga
		while(true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				//
			}
		}
	}


}
