package com.example.shootgoal.controladores;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.shootgoal.R;
import com.example.shootgoal.modelos.Jugadores;
import com.example.shootgoal.vistas.TiradorView;

public class ControladorNuevoJuego  extends Activity implements OnItemClickListener,OnClickListener  {
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
		listaJugadores.add(jugador);
		currentView = R.layout.nuevo_juego;
		final ListView lv = (ListView) findViewById(R.id.mylistview);
		lv.setAdapter(new ItemAdapter(this, R.layout.list_item ,listaJugadores));

		lv.setOnItemClickListener(this);
		//setContentView(R.layout.nuevo_juego);
	}
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		/*Intent launchGame;	
		launchGame = null;
		launchGame = new Intent(this, ControladorPortero.class);
		startActivity(launchGame);*/
		//Intent launchGame = new Intent(this,ControladorTirador.class);
			

		Intent launchGame = null;

		if(esPortero){
			launchGame = new Intent(this, ControladorPortero.class);
		} else {
			launchGame = new Intent(this, ControladorTirador.class);
		}
		esPortero=!esPortero;
		startActivity(launchGame);
	}

	
}
