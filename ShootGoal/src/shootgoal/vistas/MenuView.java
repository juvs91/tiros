package shootgoal.vistas;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * Carga el fondo y las propiedades del juego
 * asi como tambien el layout principal del menu
 * @author Tuye
 *
 */
public class MenuView extends SurfaceView implements Runnable {

	Thread renderThread = null;             //Thread de la vista
    SurfaceHolder holder;                   //Contenedor de la vista
    Canvas canvas;                          //Canvas para dibujar
    volatile boolean running = false;       //Bandera para conocer el estado de la Actividad
    float tiempoTick = 0, tick = 0.1f;      //Controladores de tiempo
    public Bitmap frameBuffer;				//Objetos Bitmap para el manejo de imgenes
    public Bitmap fondo;
    Bitmap img;
	
    public MenuView(Context context) {
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
    
	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setMenuScreenContext(Bitmap img){
		this.img = img;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        Rect dstRect = new Rect();

        while (running) {
            //Verifica que exista una vista valida
            if(!holder.getSurface().isValid()) continue;

            canvas.drawRGB(0, 255, 0);
            Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
            canvas.drawBitmap(resized, 0, 0, null);
            Canvas pantalla = holder.lockCanvas();
            
            //Determina la resolucin de la pantalla
            pantalla.getClipBounds(dstRect);
            //Dibuja el buffer en la pantalla con el tamanio de la pantalla
            pantalla.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(pantalla);
        }
	}

	public void resume() {
        //La bandera indica que la Actividad esta en ejecucion

        running = true;
        //Crea un nuevo thread para la vista
        renderThread = new Thread(this);
        //Inicializa el thread de la vista
        renderThread.start();
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
