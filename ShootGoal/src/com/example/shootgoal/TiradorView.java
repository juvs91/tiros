package com.example.shootgoal;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TiradorView extends SurfaceView implements Runnable {
	
	Thread renderThread = null;             //Thread de la vista
    SurfaceHolder holder;                   //Contenedor de la vista
    Canvas canvas;                          //Canvas para dibujar
    volatile boolean running = false;       //Bandera para conocer el estado de la Actividad
    float tiempoTick = 0, tick = 0.1f;      //Controladores de tiempo
    Bitmap frameBuffer;						//Objetos Bitmap para el manejo de imágenes
    Bitmap fondo;

	public TiradorView(Context context) {
		super(context);
		
		//Determina la orientación del dispositivo y crea un buffer en base a esta
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

	public TiradorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TiradorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Objeto Rect crea un rectángulo
        Rect dstRect = new Rect();
        //Obtiene el tiempo actual
        //long tiempoI = System.nanoTime();
        //El ciclo se ejecuta cuando la Actividad esta en ejecución
        while (running) {
            //Verifica que exista una vista válida
            if(!holder.getSurface().isValid())
                continue;

            //Calcula el tiempo transcurrido
            //float tiempo = (System.nanoTime() - tiempoI) / 1000000000.0f;
            //Obtiene el tiempo actual
            //tiempoI = System.nanoTime();

            //Pinta un fondo amarillo
            //canvas.drawRGB(200, 200, 245);
            canvas.drawRGB(0, 255, 0);
            Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
            canvas.drawBitmap(resized, 0, 0, null);
            Canvas pantalla = holder.lockCanvas();
            //Determina la resolución de la pantalla
            pantalla.getClipBounds(dstRect);
            //Dibuja el buffer en la pantalla con el tamaño de la pantalla
            pantalla.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(pantalla);
        }

	}
	
	/**
     * Método resume
     * Llamado cuando la Actividad vuelve a primer plano,
     * inicia el thread de la vista
     */
    public void resume() {
        //La bandera indica que la Actividad esta en ejecución
        running = true;
        //Crea un nuevo thread para la vista
        renderThread = new Thread(this);
        //Inicializa el thread de la vista
        renderThread.start();
    }
    
    public void pause() {
        //La bandera indica que la Actividad no esta en ejecución
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
