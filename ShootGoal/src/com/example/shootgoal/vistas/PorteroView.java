package com.example.shootgoal.vistas;


import com.example.shootgoal.controladores.ControladorPortero;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PorteroView extends SurfaceView implements Runnable {

	Thread renderThread = null;             //Thread de la vista
    SurfaceHolder holder;                   //Contenedor de la vista
    Canvas canvas;                          //Canvas para dibujar
    volatile boolean running = false;       //Bandera para conocer el estado de la Actividad
    float tiempoTick = 0, tick = 0.1f;      //Controladores de tiempo
    public Bitmap frameBuffer;				//Objetos Bitmap para el manejo de im��genes
    public Bitmap fondo;
    public Bitmap porteria;
    //Bitmap porteroImagen;
    //Point porteroPos;
    public ControladorPortero controlador;
    public boolean paraPorIzquierda = false;
    public boolean bloqueado = false;
    
    
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
	
	/*public void setPorteroScreenContext(AnimacionPortero porteroAnimacion, Point porteroPos){
		this.porteroImagen = porteroAnimacion.cuadros;
		this.porteroPos = porteroPos;
	}*/

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

            if(paraPorIzquierda){
            	bloqueado = paraPorIzquierda = controlador.portero.animacion.pararEnLaIzquierda(tiempo);
            }
            
            //Pinta un fondo amarillo
            //canvas.drawRGB(200, 200, 245);
            //canvas.drawRGB(0, 255, 0);
            Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
            canvas.drawBitmap(resized, 0, 0, null);
            //resized = Bitmap.createScaledBitmap(porteroImagen, porteroImagen.getWidth()/3, porteroImagen.getHeight()/3, true);
            //canvas.drawBitmap(resized, porteroPos.x, porteroPos.y, null);
            resized= Bitmap.createScaledBitmap(porteria, porteria.getWidth()/2,porteria.getHeight()/2, true);
            canvas.drawBitmap(resized, porteria.getWidth()/15,porteria.getHeight()/8,null);
            
            Bitmap porteroImagen = controlador.portero.animacion.getCuadro();
            resized = Bitmap.createScaledBitmap(porteroImagen, porteroImagen.getWidth()/3, porteroImagen.getHeight()/3, true);
            canvas.drawBitmap(resized, controlador.portero.posicion.x, controlador.portero.posicion.y, null);
            Canvas pantalla = holder.lockCanvas();
            
            
            
            //Determina la resolucin de la pantalla
            pantalla.getClipBounds(dstRect);
            //Dibuja el buffer en la pantalla con el tamao de la pantalla
            pantalla.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(pantalla);
        }

	}
	
	/**
     * Metodo resume
     * Llamado cuando la Actividad vuelve a primer plano,
     * inicia el thread de la vista
     */
    public void resume() {
        //La bandera indica que la Actividad esta en ejecuci��n

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
