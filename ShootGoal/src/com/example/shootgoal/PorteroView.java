package com.example.shootgoal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class PorteroView extends TiradorView {
	private int x;
	private int y;
	public PorteroView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Objeto Rect crea un rectangulo
        Rect dstRect = new Rect();
        //El ciclo se ejecuta cuando la Actividad esta en ejecucion
        while (running) {
            //Verifica que exista una vista valida
            if(!holder.getSurface().isValid())
                continue;
            canvas.drawRGB(0, 255, 0);
            Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
            canvas.drawBitmap(resized, 0, 0, null);
            Canvas pantalla = holder.lockCanvas();
            //Determina la resolucion de la pantalla
            pantalla.getClipBounds(dstRect);
            //Dibuja el buffer en la pantalla con el tamano de la pantalla
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
        //La bandera indica que la Actividad esta en ejecucion
        running = true;
        //Crea un nuevo thread para la vista
        renderThread = new Thread(this);
        //Inicializa el thread de la vista
        renderThread.start();
    }
    public void posicionTiro(MotionEvent e){
    	this.x=(int)e.getX();
    	this.y=(int)e.getY();
    }
    
    public void pause() {
        //La bandera indica que la Actividad no esta en ejecucion
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
