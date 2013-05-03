package shootgoal.modelos;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;

import shootgoal.controladores.Conexion;
import shootgoal.general.AnimacionPortero;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Clase hija del jugador que le agrega mas especificaciones al portero
 *
 */
public class Portero extends Jugador{
	
	public AnimacionPortero animacion;
	Bitmap cuadro;
	
	public Portero(Point porteroPos, AssetManager assetManager){
		AnimacionPortero animacionPor = new AnimacionPortero();
		try{
			InputStream is = null;
			for (int i = 0; i <= 21; i++) {
				is = assetManager.open("portero/Frente" + i + ".png");
				cuadro = BitmapFactory.decodeStream(is);
				is.close();
				if (i!=0){
					animacionPor.sumaCuadro(cuadro, 0.2);
				} else {
					animacionPor.sumaCuadro(cuadro, 0);
					//animacion.duracion -= 0.3;
				}
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.animacion = animacionPor;
		porteroPos.x -= animacion.getCuadro().getWidth()/3/2;
		porteroPos.y -= animacion.getCuadro().getHeight()/3/2;
		this.posicion = porteroPos;
	}
	public boolean save (Juego juego){
		Conexion.paro(juego.getJugador1(),juego.getJugador2(),juego.getStatus(),juego.getPosTiro(),juego.isAceptado(),juego.getPorParada(), new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable arg0) {
				//Toast.makeText(getBaseContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray amigo) {
				jsonHandlerSave(amigo);
			}
		});
		return false;	
	}
	
	public void jsonHandlerSave(JSONArray amigo){
	
		
	}

	

}
