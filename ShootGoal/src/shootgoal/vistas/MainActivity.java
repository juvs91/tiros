package shootgoal.vistas;



import shootgoal.build.R;
import shootgoal.controladores.ControladorLogin;
import shootgoal.controladores.ControladorMenu;
import shootgoal.controladores.ControladorNuevoJuego;
import shootgoal.controladores.ControladorSignUp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity{

	int vista = 0;//vista inicial 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//aqui obtengo las preferencias del usuario 
		SharedPreferences prefs=getSharedPreferences("shootGoal",Context.MODE_PRIVATE);
		String correo = prefs.getString("email", "no existe");
		
		if(correo.equals("no existe")){
			Intent launchGame = new Intent(this,ControladorLogin.class);
			startActivity(launchGame);
		}else{
			Intent launchGame = new Intent(this,ControladorMenu.class);
			startActivity(launchGame);
		}
				

	}


		


}
