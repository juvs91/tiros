package shootgoal.modelos;

import java.io.IOException;
import java.io.InputStream;

import shootgoal.general.AnimaBalon;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Tirador extends Jugador {
	public AnimaBalon animacion;
	Bitmap cuadro;
	
	public Tirador(Point TiradorPos, AssetManager assetManager){
		AnimaBalon animacionBalon = new AnimaBalon();
		try{
			InputStream is = null;
				is = assetManager.open("soccerballanimated.gif");
			cuadro = BitmapFactory.decodeStream(is);
			is.close();
			animacionBalon.sumaCuadro(cuadro, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.animacion = animacionBalon;
		this.animacion.balon = this;
		TiradorPos.x -= animacion.getCuadro().getWidth()/3/2;
		TiradorPos.y -= animacion.getCuadro().getHeight()/3/2;
		this.posicion = TiradorPos;
	}
	
}
