package shootgoal.modelos;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shootgoal.controladores.Conexion;
import shootgoal.controladores.ControladorNuevoJuego;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Jugadores {
	private String nombre;
	private int puntaje;
	private int id;
	private String mail;
	private LinkedList<Jugadores> jugadores;
	private ControladorNuevoJuego controlador;
	public Jugadores(){

	}
	
	// constructor
	public Jugadores(int puntaje, String nombre,int id){
		this.puntaje = puntaje;
		this.nombre = nombre; 
		this.id=id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public LinkedList<Jugadores> jugadores(JSONArray json){
		Conexion.amigos(id, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				controlador.errorAmigos();
			}
			@Override
			public void onSuccess(JSONArray amigos) {
				Log.v("json", amigos+"");
				jugadores=new LinkedList<Jugadores>();
				if(amigos!=null){
					for(int i=0;i<amigos.length();i++){
					    try {
					      Jugadores jugador = new Jugadores();
						  JSONObject item = amigos.getJSONObject(i);
						  nombre=item.getString("nombre");
						  puntaje=Integer.parseInt(item.getString("puntaje"));
						  id=Integer.parseInt(item.getString("id"));
						  jugador.setId(id);
						  jugador.setNombre(nombre);
						  jugador.setPuntaje(puntaje);
						  jugadores.add(jugador);
						  Log.v("json", nombre+" "+puntaje+" "+id);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Log.v("json", jugadores+"");
				}else{
					Jugadores jugadores=new Jugadores(100,"gordo",1);
				}
			}
		});
		
		return jugadores;
		
	}

	
}
