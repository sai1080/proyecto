package com.example.nauval;

import java.util.List;
import java.util.Locale;

import com.example.nauval.dao.ClubNauticoDAO;
import com.example.nauval.dao.ClubNauticoDAOHttpClient;
import com.example.nauval.dao.ClubNauticoDaoTest;
import com.example.nauval.modelo.ClubNautico;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DetallePuertoActivity extends  Activity
  {
	
	
	private Typeface newfont;
	// String url = "http://www.google.es";
	private TextView direccionTextView;
	private TextView telefonoTextView;
	private TextView paginaTextView;
	private TextView correoTextView;
	private ListView listaOpcionesListView;
	private TextView coordenadasTextView;
	private String uri;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// establecemos el layout correspondiente a la actividad
		setContentView(R.layout.activity_detalle_puerto);
		// enlazamos los componentes declarados en el layout con el atributo
		// correspondiente
		direccionTextView = (TextView) findViewById(R.id.direccion_text_view);
		telefonoTextView = (TextView) findViewById(R.id.telefonotextview);
		correoTextView = (TextView) findViewById(R.id.emailtextview);
		paginaTextView = (TextView) findViewById(R.id.webtextview);
		coordenadasTextView = (TextView) findViewById(R.id.coordenadastextview);
		listaOpcionesListView = (ListView) findViewById(R.id.informacion);
		// obtenemos el identificador del puerto que hemos recibido de la
		// actividad previa(ListadoPuertosActivity)
		Integer puertoId = getIntent().getIntExtra("puerto_id", 0);
		// ejecutamos la tarea asíncrona RecuperaInformacionPuerto para que se
		// recupere la información y se muestre en los textview
		new RecuperaInformacionPuerto().execute(puertoId);
		newfont = Typeface.createFromAsset(getAssets(),
				"fonts/BrushHandNew.ttf");
		direccionTextView.setTypeface(newfont);
		telefonoTextView.setTypeface(newfont);
		correoTextView.setTypeface(newfont);
		paginaTextView.setTypeface(newfont);
		coordenadasTextView.setTypeface(newfont);

		// añadimos un clikListener en la etiqueta del teléfono para que se abra
		// el teclado
		// del teléfono cuando pinchemos.
		telefonoTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + ((TextView) arg0).getText()));
				startActivity(intent);
				finish();
			}
		});

		correoTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL,
						new String[] { ((TextView) arg0).getText().toString() });
				startActivity(intent);
			}
		});
		
		coordenadasTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
				startActivity(intent);
				 
			}			
		});

		listaOpcionesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				switch (arg2) {
				case 0:
					System.out.println("opcion:" + arg2);
					break;
				case 1:
					System.out.println("opcion:" + arg2);
					break;
				case 2:
					System.out.println("opcion:" + arg2);
					break;
				default:
					System.out.println("opcion:" + arg2);
					break;
				}
			}
		});

		paginaTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://"
						+ ((TextView) arg0).getText()));
				startActivity(intent);
			}
		});

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/*  
	 * hola
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
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

	// Esta clase se utiliza para realizar la llamada del método
	// recuperaClubNautico de ClubNauticoDAO
	// estamos obligados a hacerlo asi para no interrumpir el renderizado de la
	// interfaz de usuario
	private class RecuperaInformacionPuerto extends
			AsyncTask<Integer, Void, ClubNautico> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			progressDialog = ProgressDialog.show(DetallePuertoActivity.this,
					"Progreso", "Cargando información del puerto", true);
		}
		// En este método se realiza la llamada que requiere acceso a internet
		@Override
		protected ClubNautico doInBackground(Integer... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ClubNauticoDAO clubNauticoDAO = new ClubNauticoDAOHttpClient();
			ClubNautico clubNautico = clubNauticoDAO
					.recuperaClubNautico(params[0]);
			return clubNautico;
		}
		// Este método se ejecuta una vez concluido el método doInBackground y
		// recibe como parámetro el valor obtenido, con este
		// valor actualizamos la intefaz de usuario con los datos
		// correspondientes.
		protected void onPostExecute(final ClubNautico clubNautico) {
			direccionTextView.setText(clubNautico.getDireccion());
			telefonoTextView.setText(clubNautico.getTelefono());
			correoTextView.setText(clubNautico.getEmail());
			paginaTextView.setText(clubNautico.getWeb());			
			coordenadasTextView.setText(clubNautico.getLatitud() + "-"
					+ clubNautico.getLongitud());
			uri=String.format(Locale.ENGLISH,"geo:%f,%f?z=17&t=k&q=%f,%f(%s)",clubNautico.getLatitud(),clubNautico.getLongitud(),clubNautico.getLatitud(),clubNautico.getLongitud(),clubNautico.getNombre());
			progressDialog.dismiss();
		}
	}
}
