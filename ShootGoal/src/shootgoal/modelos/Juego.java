package shootgoal.modelos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shootgoal.controladores.Conexion;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Juego {
	


	private Tirador tirador;
	private Portero portero;
	private int status;
	private int posTiro;
	private int porParada;
	//public enum posicionRelativa{IZQUIERDA, CENTRO, DERECHA, FUERA};
	private boolean aceptado;
	private JSONArray json;
	private JSONObject item;
	//private Juego juego;
	private int puntajeJugador1;
	private int puntajeJugador2;
	
	public Juego(Tirador tirador, Portero portero, int status, int posTiro,
			int porParada, boolean aceptado, int puntajeJugador1, int puntajeJugador2) {
		super();
		this.tirador = tirador;
		this.portero = portero;
		this.status = status;
		this.posTiro = posTiro;
		this.porParada = porParada;
		this.aceptado = aceptado;
		this.puntajeJugador1 = puntajeJugador1;
		this.puntajeJugador2 = puntajeJugador2;
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
		this.tirador = tirador;
	}

	public Portero getPortero() {
		return portero;
	}

	public void setPortero(Portero portero) {
		this.portero = portero;
	}

	public JSONArray getJson() {
		return json;
	}

	public void setJson(JSONArray json) {
		this.json = json;
	}

	public JSONObject getItem() {
		return item;
	}

	public void setItem(JSONObject item) {
		this.item = item;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPosTiro() {
		return posTiro;
	}
	public void setPosTiro(int posTiro) {
		this.posTiro = posTiro;
	}
	public int getPorParada() {
		return porParada;
	}
	public void setPorParada(int porParada) {
		this.porParada = porParada;
	}
	public boolean isAceptado() {
		return aceptado;
	}
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	public void fetch(int idJugador1,int idJugador2){
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
		
		
	}
	public void jsonHandler(JSONArray json){
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
			    	puntajeJugador1 = item.getInt("puntajeJugador1");
			    	puntajeJugador2 = item.getInt("puntajeJugador2");
			    	//juego=new Juego(tirador,portero,status,posTiro,porParada,false,puntajeJugador1,puntajeJugador2);
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
