package shootgoal.modelos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shootgoal.controladores.Conexion;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Juego {
	


	private static Tirador tirador;
	private static Portero portero;
	private static int status;
	private static int posTiro;
	private static int porParada;
	public enum posicionRelativa{IZQUIERDA, CENTRO, DERECHA, FUERA};
	private static boolean aceptado;
	private JSONArray json;
	private static JSONObject item;
	private static Juego juego;
	private  int puntajeJugador1;
	private  int puntajeJugador2;
	
	public Juego(Tirador tirador, Portero portero, int status, int posTiro,
			int porParada, boolean aceptado) {
		super();
		Juego.tirador = tirador;
		Juego.portero = portero;
		Juego.status = status;
		Juego.posTiro = posTiro;
		Juego.porParada = porParada;
		Juego.aceptado = aceptado;
	}
	
	public  int getPuntajeJugador1() {
		return puntajeJugador1;
	}
	public  void setPuntajeJugador1(int puntajeJugador1) {
		this.puntajeJugador1 = puntajeJugador1;
	}
	public  int getPuntajeJugador2() {
		return puntajeJugador2;
	}
	public  void setPuntajeJugador2(int puntajeJugador2) {
		this.puntajeJugador2 = puntajeJugador2;
	}
	public Tirador getTirador() {
		return tirador;
	}

	public void setTirador(Tirador tirador) {
		Juego.tirador = tirador;
	}

	public Portero getPortero() {
		return portero;
	}

	public void setPortero(Portero portero) {
		Juego.portero = portero;
	}

	public JSONArray getJson() {
		return json;
	}

	public void setJson(JSONArray json) {
		this.json = json;
	}

	public static JSONObject getItem() {
		return item;
	}

	public static void setItem(JSONObject item) {
		Juego.item = item;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		Juego.status = status;
	}
	public int getPosTiro() {
		return posTiro;
	}
	public void setPosTiro(int posTiro) {
		Juego.posTiro = posTiro;
	}
	public int getPorParada() {
		return porParada;
	}
	public void setPorParada(int porParada) {
		Juego.porParada = porParada;
	}
	public boolean isAceptado() {
		return aceptado;
	}
	public void setAceptado(boolean aceptado) {
		Juego.aceptado = aceptado;
	}
	
	public static Juego fetch(int idJugador1,int idJugador2){
		Conexion.juego(idJugador1,idJugador2, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				//Toast.makeText(this, "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray amigos) {
				jsonHandler(amigos);
			}
		});
		
		return juego;
		
	}
	public static void jsonHandler(JSONArray json){
		Log.v("json", json+"");
		if(json!=null){
			for(int i=0;i<json.length();i++){
			    try {
			    	tirador=new Tirador(null, null);
			    	portero=new Portero(null, null);
			    	item = json.getJSONObject(i);
			    	tirador.setId(item.getInt("jugador1"));
			    	portero.setId(item.getInt("jugador2"));
			    	status=item.getInt("estado");
			    	posTiro=item.getInt("posTiro");
			    	porParada=item.getInt("posParo");
			    	juego=new Juego(tirador,portero,status,posTiro,porParada,false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			Jugadores jugador=new Jugadores(100,"gordo",1);
		}
	}
	public void update(){
		Conexion.juegoUpdate(this.getTirador().getId(),this.getPortero().getId(),this.getStatus(),this.getPosTiro(),this.getPorParada(),this.getPuntajeJugador1(),this.getPuntajeJugador2(), new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				//Toast.makeText(this, "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray amigos) {
				jsonHandlerUpdate(amigos);
			}
		});
	}
	public  void jsonHandlerUpdate(JSONArray json){
		
	}

	
}
