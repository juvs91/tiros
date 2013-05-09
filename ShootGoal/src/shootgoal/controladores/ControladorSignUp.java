package shootgoal.controladores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import shootgoal.modelos.Jugadores;
import shootgoalapp.build.R;
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
import android.widget.Toast;

public class ControladorSignUp extends Activity{
	ControladorSignUp controlador;
	String nombre;
	String password;
	String puntaje;
	String mail;
	int id;
	JSONArray json;
	JSONObject item;
	Jugadores jugador;
	boolean usuarioCorrecto=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.sign_up);
		 controlador = this;
	       ((Button) findViewById(R.id.submit_sign_up)).setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		nombre= ((EditText) findViewById(R.id.usuario_texto_sign_up)).getText().toString();
	        		password= ((EditText) findViewById(R.id.pasword_texto_sign_up)).getText().toString();
	        		mail= ((EditText) findViewById(R.id.mail_sign_up_text)).getText().toString();
	        		controlador.signUp(nombre, password,mail);
	        	}
	        });
	}
	public void signUp(String nombre,String password,String mail){
		Conexion.signUp(nombre,password,mail, new JsonHttpResponseHandler() {
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
				  mail=item.getString("mail");
				  id=Integer.parseInt(item.getString("id"));
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
			//falta poner que redirija a la pantalla de exito 
			
		}else{
			Toast.makeText(getApplicationContext(), "Create an account.",Toast.LENGTH_LONG).show();
			//falta poner que rediriga al controlador de signUp
			
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
			Toast.makeText(getApplicationContext(), "usuario incorrecto .",Toast.LENGTH_LONG).show();
			Intent launchGame = new Intent(this,ControladorSignUp.class);
			startActivity(launchGame);
		}
		
	}
	

}
