package com.example.nauval;

import java.util.List;

import com.example.nauval.dao.ClubNauticoDAO;
import com.example.nauval.dao.ClubNauticoDAOHttpClient;
import com.example.nauval.dao.ClubNauticoDaoTest;
import com.example.nauval.modelo.ClubNautico;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DetallePuertoActivity extends Activity {
	private Typeface newfont;
	String url = "http://www.google.es";
	private TextView direccionTextView;
	private TextView telefonoTextView;
	private TextView paginaTextView;
	private TextView correoTextView;
	private ListView listaOpcionesListView;
	private TextView coordenadasTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//establecemos el layout correspondiente a la actividad
		setContentView(R.layout.activity_detalle_puerto);
		//enlazamos los componentes declarados en el layout con el atributo correspondiente
		direccionTextView=(TextView) findViewById(R.id.direccion_text_view);
		 telefonoTextView = (TextView) findViewById(R.id.telefonotextview);
		 correoTextView = (TextView) findViewById(R.id.emailtextview);
		 paginaTextView = (TextView) findViewById(R.id.webtextview);
		 coordenadasTextView = (TextView) findViewById(R.id.coordenadastextview);
		listaOpcionesListView = (ListView) findViewById(R.id.informacion);
		//obtenemos el identificador del puerto que hemos recibido de la actividad previa(ListadoPuertosActivity)
		Integer puertoId=getIntent().getIntExtra("puerto_id", 0);
        //ejecutamos la tarea asincrona RecuperaInformacionPuerto para que se recupere la informacion y se muestre en los textview	
		new RecuperaInformacionPuerto().execute(puertoId);
		newfont = Typeface.createFromAsset(getAssets(), "fonts/BrushHandNew.ttf");
		direccionTextView.setTypeface(newfont);
		telefonoTextView.setTypeface(newfont);
		correoTextView.setTypeface(newfont);
		paginaTextView.setTypeface(newfont);
		coordenadasTextView.setTypeface(newfont);		
		
		//añadimos un clikListener en la etiqueta del telefono para que se abra el marcador
		//del telefono cuando pinchemos.
		telefonoTextView.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+((TextView)arg0).getText()));
				startActivity(intent);
				finish();
			}
		});
		
		correoTextView.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{((TextView)arg0).getText().toString()});
				startActivity(intent);			
			}
		});
		
		listaOpcionesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			      switch (arg2) {
				case 0:
					System.out.println("opcion:"+arg2);
					break;
				case 1:
					System.out.println("opcion:"+arg2);
					break;
				case 2:
					System.out.println("opcion:"+arg2);
					break;
				default:
					System.out.println("opcion:"+arg2);
					break;
				}			
			}
		});
				
		paginaTextView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://"+((TextView)arg0).getText()));
				startActivity(intent);				
			}						
		});		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_puerto, menu);
		return true;
	}
	
	//esta clase se utiliza para realizar la llamada del metodo recuperaClubNautico del ClubNauticoDAO
	//estamos obligados a hacerlo asi para no interrumpir el renderizado de a interfaz de usuario
	private class RecuperaInformacionPuerto extends AsyncTask<Integer, Void, ClubNautico>{
		
		//En este metodo se realiza la llamada que requiere acceso a internet
		@Override
		protected ClubNautico doInBackground(Integer... params) {
			ClubNauticoDAO clubNauticoDAO=new ClubNauticoDAOHttpClient();
			ClubNautico clubNautico= clubNauticoDAO.recuperaClubNautico(params[0]);
			return clubNautico;
		}
		//Este metodo se ejecuta una vez concluido el metodo doInBackground y 
		//recibe como parametro el valor obtenido, con este
		//valor actualizamos la intefaz de usuario con los datos correspondientes.
		protected void onPostExecute(final ClubNautico clubNautico){
			direccionTextView.setText(clubNautico.getDireccion());
			telefonoTextView.setText(clubNautico.getTelefono());
			correoTextView.setText(clubNautico.getEmail());
			paginaTextView.setText(clubNautico.getWeb());
			coordenadasTextView.setText(clubNautico.getLatitud() + "-" + clubNautico.getLongitud());
		}		
	}
}
