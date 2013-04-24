package shootgoal.controladores;

import java.util.LinkedList;
import java.util.List;

import shootgoal.modelos.Jugadores;
import shootgoal.vistas.TiradorView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import shootgoal.build.R;

public class ControladorNuevoJuego  extends Activity implements 
	OnItemClickListener  {
	WakeLock wakeLock;
	TiradorView view;
	int currentView;
	List<Jugadores> listaJugadores;
	boolean esPortero = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
		
		
	}
	public void init(){
		setContentView(R.layout.nuevo_juego);
		listaJugadores=new LinkedList<Jugadores>();
		Jugadores jugador=new Jugadores(100,"gordo",1);
		Jugadores jugador2 = new Jugadores(9999, "tu mama", 0);
		listaJugadores.add(jugador);
		listaJugadores.add(jugador2);
		currentView = R.layout.nuevo_juego;
		final ListView lv = (ListView) findViewById(R.id.mylistview);
		lv.setAdapter(new ItemAdapter(this, R.layout.list_item ,listaJugadores));

		lv.setOnItemClickListener(this);
		//setContentView(R.layout.nuevo_juego);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		/*Intent launchGame;	
		launchGame = null;
		launchGame = new Intent(this, ControladorPortero.class);
		startActivity(launchGame);*/
		//Intent launchGame = new Intent(this,ControladorTirador.class);
		Log.v("Hoolis", "Entre on click");

		Intent launchGame = null;

		if(esPortero){
			Log.v("Hoolis", "Soy portero");
			launchGame = new Intent(this, ControladorPortero.class);
		} else {
			Log.v("Hoolis", "No soy portero");
			launchGame = new Intent(this, ControladorTirador.class);
		}
		esPortero=!esPortero;
		startActivity(launchGame);
	}

	
}
