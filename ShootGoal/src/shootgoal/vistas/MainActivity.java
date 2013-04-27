package shootgoal.vistas;



import shootgoal.build.R;
import shootgoal.controladores.ControladorMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

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