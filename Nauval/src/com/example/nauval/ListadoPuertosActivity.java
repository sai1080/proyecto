package com.example.nauval;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.nauval.dao.ClubNauticoDAO;
import com.example.nauval.dao.ClubNauticoDAOHttpClient;
import com.example.nauval.dao.ClubNauticoDaoTest;
import com.example.nauval.modelo.ClubNautico;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Simón
 *ESta actividad se utiliza en el caso de uso que lista el conjunto de puertos que
 *existen en el sistema.
 */
public class ListadoPuertosActivity extends Activity {

	ListView listView;

	/* 
	 * Inicializamos los componentes de la interfaz de usuario que previamente hemos incorporado
	 * al archivo layout puertos.xml, a través del DAO recuperamos la lista de puertos y se los
	 * añadimos al componente ListView que será el encargado de visualizarlos. A esta lista le
	 * añadimos un OnItemClickListener para cuando el usuario seleccione uno de ellos se abra 
	 * la actividad DetallesPuertoActivity.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puertos);
		listView = (ListView) findViewById(R.id.Lista);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// System.out.println("select:"+arg2);
				ClubNautico clubNautico = (ClubNautico) listView
						.getItemAtPosition(arg2);
				// System.out.println("club:"+clubNautico.getNombre());
				Intent intent = new Intent(getApplicationContext(),
						DetallePuertoActivity.class);
				intent.putExtra("puerto_id", clubNautico.getId());
				startActivity(intent);
			}
		});
		new RecuperarPuertosTask().execute("");
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		default:
			break;
		}
		return true;
	}

	// Para poder invocar servicios que acceden a Internet desde el hilo
	// principal de la aplicación
	// definimos una clase que herede de AsyncTask donde debemo insertar el
	// código a ejecutar.
	private class RecuperarPuertosTask extends
			AsyncTask<String, Void, List<ClubNautico>> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			progressDialog = ProgressDialog.show(ListadoPuertosActivity.this,
					"En proceso", "Cargando puertos", true);
		}

		@Override
		protected List<ClubNautico> doInBackground(String... arg0) {
			ClubNauticoDAO clubNauticoDAO = new ClubNauticoDAOHttpClient();
			List<ClubNautico> clubesNauticos = clubNauticoDAO
					.recuperarClubesNauticos();
			return clubesNauticos;
		}

		@Override
		protected void onPostExecute(List<ClubNautico> clubesNauticos) {
			super.onPostExecute(clubesNauticos);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final ArrayAdapter<ClubNautico> adapter = new ArrayAdapter<ClubNautico>(
					ListadoPuertosActivity.this,
					android.R.layout.simple_list_item_1, clubesNauticos);
			listView.setAdapter(adapter);
			progressDialog.dismiss();
		}
	}
}
