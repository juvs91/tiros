package shootgoal.controladores;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shootgoal.build.R;
import shootgoal.modelos.Jugadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ControladorLogin extends Activity{
	
	String nombre;
	String password;
	String puntaje;
	String mail;
	int id;
	JSONArray json;
	JSONObject item;
	Jugadores jugador;
	boolean usuarioCorrecto=false;
	ControladorLogin controlador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.login);
		 controlador = this;
	       ((Button) findViewById(R.id.submit)).setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		nombre= ((EditText) findViewById(R.id.userNameText)).getText().toString();
	        		password= ((EditText) findViewById(R.id.passwordText)).getText().toString();
	        		controlador.login(nombre, password);
	        	}
	        });
	}
	
	
	public void login(String nombre,String password){
		Conexion.login(nombre,password, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(getApplicationContext(), "Network error, please try again later.",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onSuccess(JSONArray usuario) {
				jsonHandler(usuario);
			}
		});
	}
	
	public void jsonHandler(JSONArray usuario){
		Log.v("json", json+"");
		jugador=new Jugadores();
		json=usuario;
		if(json!=null){
			for(int i=0;i<json.length();i++){
			    try {
				  item = json.getJSONObject(i);
				  nombre=item.getString("nombre");
				  puntaje = item.getString("puntaje");
				  id=Integer.parseInt(item.getString("id"));
				  mail=item.getString("mail");
				  jugador.setId(id);
				  jugador.setNombre(nombre);
				  jugador.setPuntaje(Integer.parseInt(puntaje));
				  Log.v("json", nombre+" "+puntaje+" "+id);
				  usuarioCorrecto=true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else{
			Toast.makeText(getApplicationContext(), "NetworkError.",Toast.LENGTH_LONG).show();
			
		}
		
		
		if(usuarioCorrecto){
			
			//guardo en los shared preferences los valores del login 
			SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("mail", mail);
			editor.putInt("id", id);
			editor.commit();
			
			Intent launchGame = new Intent(this,ControladorMenu.class);
			startActivity(launchGame);
		}else{
			Toast.makeText(getApplicationContext(), "usuario incorrecto.",Toast.LENGTH_LONG).show();
			Intent launchGame = new Intent(this,ControladorSignUp.class);
			startActivity(launchGame);
		}
		
	}
	
	
	
	
}
