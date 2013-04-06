package com.example.shootgoal.controladores;
import java.util.List;

import com.example.shootgoal.R;
import com.example.shootgoal.modelos.Jugadores;

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
	private LayoutInflater inflator;


	public ItemAdapter(Context context, List<Jugadores> results) {
		itemsList = results;
		inflator = LayoutInflater.from(context);
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
		ViewHolder holder;
		
		if(view == null) {
			view = inflator.inflate(R.layout.jugadores, null);
			holder = new ViewHolder();
			holder.nombre = (TextView) view.findViewById(R.id.jugador);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.nombre.setText(itemsList.get(pos).getNombre());
		
		
		return view;
	}
	
	static class ViewHolder {
		TextView nombre;
		TextView puntaje;
	}

}
