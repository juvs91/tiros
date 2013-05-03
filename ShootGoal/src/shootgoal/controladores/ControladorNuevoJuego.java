package shootgoal.controladores;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.*;

import com.loopj.android.http.JsonHttpResponseHandler;

import shootgoal.modelos.Jugadores;
import shootgoal.vistas.TiradorView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import shootgoal.build.R;


public class ControladorNuevoJuego  extends Activity implements OnItemClickListener,OnClickListener  {

/**
 * 
 * Carga la lista de los jugadores con su score y se encarga de determinar si es 
 * portero o tirador.
 *
 */

	WakeLock wakeLock;
	TiradorView view;
	int currentView;
	List<Jugadores> listaJugadores;
	boolean esPortero = true;
	Conexion conexion;
	JSONArray json;
	JSONObject item;
    String nombre,puntaje,id;
    int estado;
    Jugadores jugador;
    ControladorNuevoJuego controlador;
    String mail;
    int idContrincante;
   


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
	 	setContentView(R.layout.nuevo_juego);
		 Button buscar= ((Button) findViewById(R.id.buscar_nuevo_juego));
		 buscar.setOnClickListener(this);

	     
			
			
		
	}
	/**
	 * inicializa los valores por default
	 */

	public void buscar(String mail){
		Conexion.buscar(mail, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(getBaseContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray amigo) {
				jsonHandlerBusqueda(amigo);
			}
		});
	}
	

	public void init(){
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		int id  = prefs.getInt("id", 0);
		
		Conexion.amigos(id, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(getBaseContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray amigos) {
				jsonHandler(amigos);
			}
		});
		
		//setContentView(R.layout.nuevo_juego);
	}
	
	public void errorAmigos(){
		Toast.makeText(getBaseContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
	}
	
	public void jsonHandlerBusqueda(JSONArray json){
		Log.v("json", json+"");
		if(json!=null){
			for(int i=0;i<json.length();i++){
			    try {
			      jugador=new Jugadores();
				  item = json.getJSONObject(i);
				  nombre=item.getString("nombre");
				  puntaje=item.getString("puntaje");
				  id=item.getString("id");
				  jugador.setId(Integer.parseInt(id));
				  jugador.setNombre(nombre);
				  jugador.setPuntaje(Integer.parseInt(puntaje));
				  listaJugadores.add(jugador);
				  Log.v("json", nombre+" "+puntaje+" "+id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.v("json", listaJugadores+"");
		}else{
			 jugador=new Jugadores(100,"gordo",1);
			  listaJugadores.add(jugador);
			
		}
		lista(listaJugadores);
	}
	
	
	
	public void jsonHandler(JSONArray json){
		Log.v("json", json+"");
		listaJugadores=new LinkedList<Jugadores>();
		if(json!=null){
			for(int i=0;i<json.length();i++){
			    try {
			      jugador=new Jugadores();
				  item = json.getJSONObject(i);
				  nombre=item.getString("nombre");
				  puntaje=item.getString("puntaje");
				  id=item.getString("id");
				  jugador.setId(Integer.parseInt(id));
				  jugador.setNombre(nombre);
				  jugador.setPuntaje(Integer.parseInt(puntaje));
				  listaJugadores.add(jugador);
				  Log.v("json", nombre+" "+puntaje+" "+id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.v("json", listaJugadores+"");
		}else{
			Jugadores jugador=new Jugadores(100,"gordo",1);
			listaJugadores.add(jugador);
		}
		lista(listaJugadores);
	}

	
	public synchronized void lista(List<Jugadores> listaJugadores){
		
		setContentView(R.layout.nuevo_juego);
		currentView = R.layout.nuevo_juego;
		final ListView lv = (ListView) findViewById(R.id.mylistview);
		lv.setAdapter(new ItemAdapter(this, R.layout.list_item ,listaJugadores));
		lv.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long idClick) {
		// TODO Auto-generated method stub
		
		idContrincante=listaJugadores.get(pos).getId();
		
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("id amigo", idContrincante);
		editor.commit();

		Intent launchGame = null;

		if(esPortero){
			launchGame = new Intent(this, ControladorPortero.class);
		} else {
			launchGame = new Intent(this, ControladorTirador.class);
		}
		esPortero=!esPortero;
		startActivity(launchGame);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.buscar_nuevo_juego){
			mail=((EditText) findViewById(R.id.buscar_mail)).getText().toString();
    		buscar(mail);
		}
		
	}
	
	

	
}
