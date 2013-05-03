package shootgoal.modelos;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;

import shootgoal.controladores.Conexion;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import android.widget.Toast;


/**
 * Clase hija del jugador pero que le agrega funcionalidades al tirador
 *
 */
public class Tirador extends Jugador {
	//public AnimaBalon animacion;
	Bitmap cuadro;
	
	public Tirador(Point TiradorPos, AssetManager assetManager){
		//AnimaBalon animacionBalon = new AnimaBalon();
		try{
			InputStream is = null;
				is = assetManager.open("soccerballanimated.gif");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
			//animacionBalon.sumaCuadro(cuadro, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*this.animacion = animacionBalon;
		this.animacion.balon = this;
		TiradorPos.x -= animacion.getCuadro().getWidth()/3/2;
		TiradorPos.y -= animacion.getCuadro().getHeight()/3/2;*/
		this.posicion = TiradorPos;
	}
	
	public boolean save (Juego juego){
		Conexion.tiro(juego.getJugador1(),juego.getJugador2(),juego.getStatus(),juego.getPosTiro(),juego.isAceptado(),juego.getPorParada(), new JsonHttpResponseHandler(){
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
