package com.example.shootgoal.controladores;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.example.shootgoal.R;
import com.example.shootgoal.modelos.Jugador;
import com.example.shootgoal.modelos.Jugadores;
import com.example.shootgoal.vistas.TiradorView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ControladorNuevoJuego  extends Activity implements OnItemClickListener,OnClickListener  {
	WakeLock wakeLock;
	TiradorView view;
	int currentView;
	List<Jugadores> listaJugadores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.init();
		
		
	}
	public void init(){
		listaJugadores=new LinkedList<Jugadores>();
		Jugadores jugador=new Jugadores(100,"gordo",1);
		listaJugadores.add(jugador);
		setContentView(R.layout.nuevo_juego);
		currentView = R.layout.nuevo_juego;
		final ListView lv = (ListView) findViewById(R.id.mylistview);
		lv.setAdapter(new ItemAdapter(this, listaJugadores));

		lv.setOnItemClickListener(this);
	}
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		Intent launchGame;	
		launchGame = null;
		launchGame = new Intent(this, ControladorTirador.class);
		startActivity(launchGame);

	}

	
}
