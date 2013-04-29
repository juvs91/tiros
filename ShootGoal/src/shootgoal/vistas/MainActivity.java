package shootgoal.vistas;
/**
 * @author Carlos Compean 
 * @author Eleazar Guzman 
 * @author David Tovar
 */


import shootgoal.build.R;
import shootgoal.controladores.ControladorMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
/**
 * Controlador principal del juego para que se cargue un loader para el splash
 *
 */
public class MainActivity extends Activity{

	int vista = 0;//vista inicial 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * Aqui se carga el splash del juego y para empezar el juego
		 * 
		 */
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Intent launchGame = new Intent(this,ControladorMenu.class);
		startActivity(launchGame);

	}


		


}
