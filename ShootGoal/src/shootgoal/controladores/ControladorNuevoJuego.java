package shootgoal.controladores;


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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
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
    int miId;
    int idJugador1;
    int idJugador2;
    boolean soyJugador1;
    int statusGlobal;
    boolean mailExistente=false;
    int puntajeJugador1,puntajeJugador2;
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//listaJugadores=new LinkedList<Jugadores>();
		//this.init();

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
		listaJugadores=new LinkedList<Jugadores>();

		
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
				  mail=item.getString("mail");
				  jugador.setMail(mail);
				  jugador.setId(Integer.parseInt(id));
				  jugador.setNombre(nombre);
				  jugador.setPuntaje(Integer.parseInt(puntaje));
				  jugador.setEstado(-1);
				  jugador.setPosTiro(0);
				  jugador.setPosParo(0);
				  jugador.setPuntajeJugador1(0);
				  jugador.setPuntajeJugador2(0);
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
		if(json!=null){
			for(int i=0;i<json.length();i++){
			    try {
			      jugador=new Jugadores();
				  item = json.getJSONObject(i);
				  nombre=item.getString("nombre");
				  puntaje=item.getString("puntaje");
				  id=item.getString("id");
				  mail=item.getString("mail");
				  jugador.setMail(mail);
				  puntajeJugador1=Integer.parseInt(item.getString("puntajeJugador1"));
				  jugador.setPuntajeJugador1(puntajeJugador1);
				  puntajeJugador2=Integer.parseInt(item.getString("puntajeJugador2"));
				  jugador.setPuntajeJugador2(puntajeJugador2);
				  estado=Integer.parseInt(item.getString("estado"));
				  idJugador1=Integer.parseInt(item.getString("idJugador1"));
				  idJugador2=Integer.parseInt(item.getString("idJugador2"));
				  jugador.setIdJugador1(idJugador1);
				  jugador.setIdJugador2(idJugador2);
				  jugador.setId(Integer.parseInt(id));
				  jugador.setNombre(nombre);
				  jugador.setPuntaje(Integer.parseInt(puntaje));
				  jugador.setEstado(estado);
				  jugador.setPosTiro(Integer.parseInt(item.getString("posTiro")));
				  jugador.setPosParo(Integer.parseInt(item.getString("posParo")));
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
		miId = prefs.getInt("id", 0);
		statusGlobal=listaJugadores.get(pos).getEstado();
		editor.putInt("status", listaJugadores.get(pos).getEstado());
		editor.putInt("puntaje", listaJugadores.get(pos).getPuntaje());
		editor.putInt("idJugador1", listaJugadores.get(pos).getIdJugador1());
		editor.putInt("idJugador2", listaJugadores.get(pos).getIdJugador2());
		editor.putInt("puntajeJugador1", listaJugadores.get(pos).getPuntajeJugador1());
		editor.putInt("puntajeJugador2", listaJugadores.get(pos).getPuntajeJugador2());
		editor.putInt("posTiro", listaJugadores.get(pos).getPosTiro());
		editor.putInt("posParo", listaJugadores.get(pos).getPosParo());
		editor.commit();
		
		if(miId==listaJugadores.get(pos).getIdJugador1()){
			soyJugador1=true;
		}else{
			soyJugador1=false;
		}
		//statusGlobal = listaJu
		
		Intent launchGame = null;
		if(listaJugadores.get(pos).getEstado()==-1){
			//significa que fue un usuaria buscado y se agregara a la base de datos actual 
			Conexion.nuevoJuego(miId,idContrincante, new JsonHttpResponseHandler() {
				@Override
				public void onFailure(Throwable arg0) {
					Toast.makeText(getBaseContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
				}
				@Override
				public void onSuccess(JSONArray amigos) {
					agregoJuego(amigos);
				}
			});
		}else{
			if(!(soyJugador1&&(statusGlobal==1||statusGlobal==2)||!soyJugador1&&(statusGlobal==0||statusGlobal==3||statusGlobal==4))){
				
				if(soyJugador1&&statusGlobal==3||(!soyJugador1)&&statusGlobal==1){
					launchGame = new Intent(this, ControladorPortero.class);
				} else {
					launchGame = new Intent(this, ControladorTirador.class);
				}
				startActivity(launchGame);
			}else{
				Toast.makeText(getBaseContext(), "esperando al otro jugador.",Toast.LENGTH_LONG).show();

			}
		}
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v.getId()==R.id.buscar_nuevo_juego){
			for(Jugadores jugador:listaJugadores){
				if(jugador.getMail()==((EditText) findViewById(R.id.buscar_mail)).getText().toString()){
					mailExistente=true;
				}
			}
			if(!mailExistente){
				mail=((EditText) findViewById(R.id.buscar_mail)).getText().toString();
	    		buscar(mail);

			}
		}
		
	}
	
	public void agregoJuego(JSONArray amigos){
		Intent launchGame = null;
		if(!(soyJugador1&&(statusGlobal==1||statusGlobal==2)||!soyJugador1&&(statusGlobal==0||statusGlobal==3||statusGlobal==4))){
			
			if(soyJugador1&&statusGlobal==3||(!soyJugador1)&&statusGlobal==1){
				launchGame = new Intent(this, ControladorPortero.class);
			} else {
				launchGame = new Intent(this, ControladorTirador.class);
			}
			startActivity(launchGame);
		}else{
			Toast.makeText(getBaseContext(), "esperando al otro jugador.",Toast.LENGTH_LONG).show();

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		//Pausa la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		//findViewById(R.layout.nuevo_juego).pause();
		//Libera el candado que protege la pantalla
		//wakeLock.release();
	}

	/**
	 * Metodo onResume sobrescrito de la clase Activity
	 * LLamado cuando la Actividad vuelve a primer plano
	 */
	@Override
	protected void onResume() {
		super.onResume();
		listaJugadores = new LinkedList<Jugadores>();
		init();
		//Reanuda la vista de la Actividad
		//TiradorView view = (TiradorView)getLayoutInflater().inflate(R.layout.tira_view, null);
		//viewTirador.resume();
		//Retoma el candado que protege a la pantalla
		//wakeLock.acquire();
	}
}
