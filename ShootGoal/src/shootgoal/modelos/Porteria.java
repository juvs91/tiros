package shootgoal.modelos;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * El modelo de la imagen de la porteria
 * su posicion y su tamanio
 *
 */
public class Porteria {
	public Point posicion;
	public Bitmap imagen;
	//protected Bitmap imagen; //es la imagen que se va a utilizar , en este caso el portero y el tirador
	//protected float tiempo;  //es el tiempo que va a ir durando la animacion 
	
	public Porteria(Point porteroPos, AssetManager assetManager){
		try{
			InputStream is = null;
			is = assetManager.open("PorteriaAlone.png");
			imagen = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		porteroPos.x -= imagen.getWidth()/2/2;
		porteroPos.y -= imagen.getHeight()/2/2;
		this.posicion = porteroPos;
	}
	
	/**
	 * @return the posicion
	 */
	public Point getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}
}
