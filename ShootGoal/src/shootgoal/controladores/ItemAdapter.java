package shootgoal.controladores;
import java.util.List;

import shootgoal.modelos.Jugadores;
import shootgoalapp.build.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author loling 
 * Controlador para las imagenes y asi
 */
public class ItemAdapter extends BaseAdapter {
	private static List <Jugadores> itemsList;
	Context context; 
    int layoutResourceId;

	public ItemAdapter(Context context, int layoutResourceId, List<Jugadores> results) {
		itemsList = results;
		this.context = context;
		LayoutInflater inflator = LayoutInflater.from(context);
		this.layoutResourceId = layoutResourceId;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsList.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return itemsList.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	/**
	 * ViewGroup vista padre de donde es invocado
	 * View view vista desde que se llama al controlador
	 * int pos de que item se selecciona
	 * return view
	 */
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		//View row = view;
		ViewHolder holder = null;;
		
		if(view == null) {
			 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            view = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.nombre = (TextView) view.findViewById(R.id.textView1);
			holder.puntaje = (TextView) view.findViewById(R.id.textView2);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.nombre.setText(itemsList.get(pos).getNombre());
		holder.puntaje.setText(String.valueOf(itemsList.get(pos).getPuntaje()));
		
		return view;
	}
	
	static class ViewHolder {
		TextView nombre;
		TextView puntaje;
	}

}
