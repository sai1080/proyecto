package com.example.nauval;


import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.nauval.dao.ClubNauticoDAO;
import com.example.nauval.dao.ClubNauticoDAOHttpClient;
import com.example.nauval.dao.ClubNauticoDaoTest;
import com.example.nauval.modelo.ClubNautico;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Puertos extends Activity{
	
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puertos);
		List<ClubNautico> clubesNauticos;
		try {
			clubesNauticos = new RecuperarPuertosTask().execute("").get();
			final ArrayAdapter<ClubNautico> adapter = new ArrayAdapter<ClubNautico>(this,
					android.R.layout.simple_list_item_1, clubesNauticos);
			
			listView = (ListView)findViewById(R.id.Lista);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					//System.out.println("select:"+arg2);
				    ClubNautico clubNautico=(ClubNautico) listView.getItemAtPosition(arg2);
				    //System.out.println("club:"+clubNautico.getNombre());
				    Intent intent= new Intent(getApplicationContext(),DetallePuertoActivity.class);
				    intent.putExtra("puerto_id", clubNautico.getId());
				    startActivity(intent);
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	// Para poder invocar servicios que acceden a Internet desde el hilo principal de la aplicación
	// definimos una clase que herede de AsyncTask donde debemo insertar el código a ejecutar.
    private class RecuperarPuertosTask extends AsyncTask<String, Void, List<ClubNautico>>{

		@Override
		protected List<ClubNautico> doInBackground(String... arg0) {
			ClubNauticoDAO clubNauticoDAO=new ClubNauticoDAOHttpClient();
			List<ClubNautico> clubesNauticos = clubNauticoDAO.recuperarClubesNauticos();
			return clubesNauticos;
		}
    }
}
