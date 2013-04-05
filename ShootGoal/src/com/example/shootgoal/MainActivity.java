package com.example.shootgoal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
public class MainActivity extends Activity implements OnClickListener {
	int vista=0;//vista inicial 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pr);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		ImageButton boton= ((ImageButton) findViewById(R.id.butPlay));
		boton.setOnClickListener(this);
		

	}


	@Override
	public void onClick(View arg0) {
		Intent launchGame=new Intent(this,ControladorTirador.class);
		startActivity(launchGame);	

	}

}
