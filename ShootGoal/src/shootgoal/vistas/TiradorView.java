package shootgoal.vistas;

import com.example.shootgoal.controladores.ControladorTirador;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class TiradorView extends SurfaceView implements Runnable {

	
	Thread renderThread = null;             //Thread de la vista
    SurfaceHolder holder;                   //Contenedor de la vista
    Canvas canvas;                          //Canvas para dibujar
    volatile boolean running = false;       //Bandera para conocer el estado de la Actividad
    float tiempoTick = 0, tick = 0.1f;      //Controladores de tiempo
    public Bitmap frameBuffer;						//Objetos Bitmap para el manejo de imagenes
    public Bitmap fondo;
    public Bitmap porteria;
    Bitmap porteroImagen;
    Bitmap balonImagen;
    public Point balonPos;
    public Point porteroPos;
    public Point diferencia;
    public Point balonPosFinal;
    public ControladorTirador controlador;
    boolean tiro;
    
    
	public TiradorView(Context context) {
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

	public TiradorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TiradorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public void setPorteroScreenContext(Bitmap porteroImage, Point porteroPos){
		this.porteroImagen = porteroImage;
		this.porteroPos = porteroPos;
	}
	public void setTiradorScreenContext(Bitmap balonImage, Point balonPos,Point diferencia, Point balonPosFinal){
		this.balonImagen = balonImage;
		this.balonPos = balonPos;
		this.diferencia=diferencia;
		this.balonPosFinal=balonPosFinal;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Objeto Rect crea un rectangulo
        Rect dstRect = new Rect();
        //El ciclo se ejecuta cuando la Actividad esta en ejecucion
        while (running) {
            long tiempoI = System.nanoTime();
            //Verifica que exista una vista valida
            if(!holder.getSurface().isValid()) 
            	continue;
            
            //Calcula el tiempo transcurrido
            float tiempo = (System.nanoTime() - tiempoI) / 1000000000.0f;
            //Obtiene el tiempo actual
            tiempoI = System.nanoTime();
            
            if(balonPosFinal.x>balonPos.x||balonPosFinal.y>balonPos.y||balonPosFinal.x<balonPos.x||balonPosFinal.y<balonPos.y){
            	tiro=controlador.tirador.animacion.lanzaBalon(tiempo,balonPos,diferencia);
            }
            
            canvas.drawRGB(0, 255, 0);
            Bitmap resized = Bitmap.createScaledBitmap(fondo, frameBuffer.getWidth(), frameBuffer.getHeight(), true);
            canvas.drawBitmap(resized, 0, 0, null);
            
            resized= Bitmap.createScaledBitmap(porteria, porteria.getWidth()/2,porteria.getHeight()/2, true);
            canvas.drawBitmap(resized, porteria.getWidth()/15,porteria.getHeight()/8,null);
            
            resized = Bitmap.createScaledBitmap(porteroImagen, porteroImagen.getWidth()/3, porteroImagen.getHeight()/3, true);
            canvas.drawBitmap(resized, porteroPos.x, porteroPos.y, null);
            
            resized=Bitmap.createScaledBitmap(balonImagen, balonImagen.getWidth()/3, balonImagen.getHeight()/3, true);
            
            canvas.drawBitmap(resized, balonPos.x, balonPos.y, null);
            
            Canvas pantalla = holder.lockCanvas();
            
            //Determina la resoluci��n de la pantalla
            pantalla.getClipBounds(dstRect);
            //Dibuja el buffer en la pantalla con el tama��o de la pantalla
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
