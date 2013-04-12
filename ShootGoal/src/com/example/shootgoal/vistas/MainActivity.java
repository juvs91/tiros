package com.example.shootgoal.vistas;



import java.io.IOException;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.shootgoal.R;

import com.example.shootgoal.controladores.ControladorMenu;
import com.example.shootgoal.controladores.ControladorNuevoJuego;
import com.example.shootgoal.controladores.ControladorPortero;
import com.example.shootgoal.controladores.ControladorTirador;

public class MainActivity extends Activity{

	int vista = 0;//vista inicial 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Intent launchGame = new Intent(this,ControladorMenu.class);
		startActivity(launchGame);

	}


		


}
