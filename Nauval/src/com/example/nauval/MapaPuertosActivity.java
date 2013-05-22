package com.example.nauval;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.nauval.dao.ClubNauticoDAO;
import com.example.nauval.dao.ClubNauticoDAOHttpClient;
import com.example.nauval.modelo.ClubNautico;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.Projection;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MapaPuertosActivity extends
		android.support.v4.app.FragmentActivity {

	private GoogleMap mapa = null;
	private int vista = 0;
	private List<ClubNautico> clubesNauticos;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapas);
		try {
			clubesNauticos = new RecuperarPuertosTask().execute("").get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		//Generamos una posición de la comunidad valencia al inicio de la actividad 
		CameraUpdate camUpd2 = CameraUpdateFactory.newLatLngZoom(new LatLng(
				39.28, 0.22), 7F);
		mapa.animateCamera(camUpd2);

		mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(MapaPuertosActivity.this,
						"Marcador pulsado:\n" + marker.getTitle(),
						Toast.LENGTH_SHORT).show();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				LatLng puerto = marker.getPosition();
				CameraPosition camPos = new CameraPosition.Builder()
						.target(puerto) // Centramos el mapa en  un puerto náutico
										// d
						.zoom(15) // Establecemos el zoom en 19
						.bearing(30) // Establecemos la orientación con el
										// noreste arriba
						.tilt(70) // Bajamos el punto de vista de la cámara 70
									// grados
						.build();
				CameraUpdate camUpd3 = CameraUpdateFactory
						.newCameraPosition(camPos);
				mapa.animateCamera(camUpd3);
				return true;
			}
		});
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void mostrarMarcadores() {
		for (ClubNautico clubNautico : clubesNauticos) {
			LatLng pos = new LatLng(clubNautico.getLatitud(),
					clubNautico.getLongitud());
			mapa.addMarker(new MarkerOptions()
					.title(clubNautico.getNombre())
					.snippet(clubNautico.getDireccion())
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
					.position(pos));
		}	
	}
	
	private void getPosition(){
		mapa.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				com.google.android.gms.maps.Projection proy = mapa.getProjection();
				Point coord = proy.toScreenLocation(point);
				
				Toast.makeText(MapaPuertosActivity.this, "Lat: " + point.latitude + "\n" +
												  "Long" + point.longitude + "\n" +
												  "X: " + coord.x + " - Y: " + coord.y, vista).show();			
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_mapa, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent=new Intent(this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.menu_centrarmapa:
			/*CameraUpdate camUpdCentrar = CameraUpdateFactory.newLatLngZoom(
					new LatLng(39.28, 0.22), 7F);
			mapa.animateCamera(camUpdCentrar);*/
						
			LatLng valencia1 = new LatLng(39.28, 0.22);
			CameraPosition camPos1 = new CameraPosition.Builder().target(valencia1) // Centramos el mapa en la comunidas valenciana de valencia
																				
					.zoom(7) // Establecemos el zoom en 19
					.bearing(0) // Establecemos la orientación vertical								
					.tilt(0) // el punto de vista de la cámara es cenital
					.build();

			CameraUpdate camUpd4 = CameraUpdateFactory
					.newCameraPosition(camPos1);

			mapa.animateCamera(camUpd4);
			
			
		break;
		case R.id.menu_vista:
			alternarVista();
			break;
		case R.id.menu_mover:
			/* Centramos el mapa en España
			CameraUpdate camUpd1 = CameraUpdateFactory.newLatLng(new LatLng(
					39.42815, -0.331682));
			mapa.moveCamera(camUpd1);*/
			getPosition();
			break;
		case R.id.menu_animar:
			// Centramos el mapa en España y con nivel de zoom 5
			CameraUpdate camUpd2 = CameraUpdateFactory.newLatLngZoom(
					new LatLng(40.41, -3.69), 5F);
			mapa.animateCamera(camUpd2);
			break;
		case R.id.menu_3d:
			LatLng valencia = new LatLng(39.42839, -0.33181);
			CameraPosition camPos = new CameraPosition.Builder().target(valencia) // Centramos el mapa en el puerto náutico de valencia
																				
					.zoom(17) // Establecemos el zoom en 19
					.bearing(35) // Establecemos la orientación con el noreste
									// arriba
					.tilt(30) // Bajamos el punto de vista de la cámara 70
								// grados
					.build();

			CameraUpdate camUpd3 = CameraUpdateFactory
					.newCameraPosition(camPos);

			mapa.animateCamera(camUpd3);
			break;
		/*case R.id.menu_posicion:
			CameraPosition camPos2 = mapa.getCameraPosition();
			LatLng pos = camPos2.target;
			Toast.makeText(MapaPuertosActivity.this,
					"Lat: " + pos.latitude + " - Lng: " + pos.longitude,
					Toast.LENGTH_LONG).show();
			break;*/

		case R.id.menu_marcadores:
			// mostrarMarcador(39.428341, -0.331564);
			mostrarMarcadores();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void alternarVista() {
		vista = (vista + 1) % 4;
		switch (vista) {
		case 0:
			mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case 1:
			mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case 2:
			mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case 3:
			mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
	}

	// Para poder invocar servicios que acceden a Internet desde el hilo
	// principal de la aplicación
	// definimos una clase que herede de AsyncTask donde debemo insertar el
	// código a ejecutar.
	private class RecuperarPuertosTask extends
			AsyncTask<String, Void, List<ClubNautico>> {

		@Override
		protected List<ClubNautico> doInBackground(String... arg0) {
			ClubNauticoDAO clubNauticoDAO = new ClubNauticoDAOHttpClient();
			List<ClubNautico> clubesNauticos = clubNauticoDAO
					.recuperarClubesNauticos();
			return clubesNauticos;
		}
	}
}
