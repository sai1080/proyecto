package com.example.nauval;

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

import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Mapas extends android.support.v4.app.FragmentActivity {
	
	private GoogleMap mapa = null;
	private int vista = 0;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapas);	
		
		 mapa =((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();	 
		 CameraUpdate camUpd2 = 
					CameraUpdateFactory.newLatLngZoom(new LatLng(39.28, 0.22), 7F);
					mapa.animateCamera(camUpd2);					
					
		mapa.setOnMarkerClickListener(new OnMarkerClickListener(){
			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(
				Mapas.this, 
				"Marcador pulsado:\n" + 
				marker.getTitle(),
				Toast.LENGTH_SHORT).show();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
		LatLng puerto = marker.getPosition();
		CameraPosition camPos = new CameraPosition.Builder()
			    .target(puerto)   //Centramos el mapa en puerto náutico de Valencia
			    .zoom(15)         //Establecemos el zoom en 19
			    .bearing(30)      //Establecemos la orientación con el noreste arriba
			    .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
			    .build();
		CameraUpdate camUpd3 = 
				CameraUpdateFactory.newCameraPosition(camPos);
		mapa.animateCamera(camUpd3);
				return true;
			}				
		});				 
	}
	
	private void mostrarMarcador(){
		LatLng pos1 = new LatLng(39.428341, -0.331564);
		LatLng pos2 = new LatLng(40.413299, 0.433645);

		mapa.addMarker(new MarkerOptions()
				.title("Naútico Valencia")
				.snippet("interes turístico")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
				.position(pos1));
	
		mapa.addMarker(new MarkerOptions()
				.title("Naútico Benicarló")
				.snippet("interes turístico")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
				.position(pos2));		

		LatLng pos3 = new LatLng(38.846667, 0.114833);
		
		Marker denia = mapa.addMarker(new MarkerOptions()
		        .position(pos3)
		        .title("Naútico Denia")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
		        .snippet("Ruta a Ibiza"));
		
		LatLng pos4 = new LatLng(39.15698, -0.114563);
		
		mapa.addMarker(new MarkerOptions()
				.title("Naútico Cullera")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
		        .snippet("Zona de ocio")
		        .position(pos4));			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_mapa, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{	
		switch(item.getItemId())
		{
			case R.id.menu_vista:
				alternarVista();
				break;
			case R.id.menu_mover:
				//Centramos el mapa en España
				CameraUpdate camUpd1 = 
					CameraUpdateFactory.newLatLng(new LatLng(40.41, -3.69));
				mapa.moveCamera(camUpd1);
				break;
			case R.id.menu_animar:
				//Centramos el mapa en España y con nivel de zoom 5
				CameraUpdate camUpd2 = 
					CameraUpdateFactory.newLatLngZoom(new LatLng(40.41, -3.69), 5F);
				mapa.animateCamera(camUpd2);
				break;
			case R.id.menu_3d:
				LatLng madrid = new LatLng(40.417325, -3.683081);
				CameraPosition camPos = new CameraPosition.Builder()
					    .target(madrid)   //Centramos el mapa en Madrid
					    .zoom(19)         //Establecemos el zoom en 19
					    .bearing(45)      //Establecemos la orientación con el noreste arriba
					    .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
					    .build();
				
				CameraUpdate camUpd3 = 
						CameraUpdateFactory.newCameraPosition(camPos);
				
				mapa.animateCamera(camUpd3);
				break;
			case R.id.menu_posicion:
				CameraPosition camPos2 = mapa.getCameraPosition();
				LatLng pos = camPos2.target;
				Toast.makeText(Mapas.this, 
						"Lat: " + pos.latitude + " - Lng: " + pos.longitude, 
						Toast.LENGTH_LONG).show();
				break;
				
			case R.id.menu_marcadores:
				//mostrarMarcador(39.428341, -0.331564);
				mostrarMarcador();
				break;				
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void alternarVista()
	{
		vista = (vista + 1) % 4;		
		switch(vista)
		{
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
}
