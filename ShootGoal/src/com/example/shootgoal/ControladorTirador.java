package com.example.shootgoal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;

public class ControladorTirador extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	
	
	/*private float x;
	private float y;
	
	public ControladorTirador(){
		this(0,0);
	}
	public ControladorTirador(float x,float y){
		this.x=x;
		this.y=y;
	}
	public boolean onTouchEvent(MotionEvent e){
		x=e.getX();
		y=e.getY();
		
		
		return true;
		
	}*/
}
