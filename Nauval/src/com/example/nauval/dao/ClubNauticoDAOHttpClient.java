package com.example.nauval.dao;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.nauval.modelo.ClubNautico;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Esta clase implementa la funcionalidad definida en la interface ClubNauticoDAO para
 * recuperar los datos se hace uso de un servicio residente en un servidor Http
 * @author Simón
 *
 */
public class ClubNauticoDAOHttpClient implements ClubNauticoDAO{
	 private static final String puertosURL="http://nauval.jelastic.dogado.eu/nauval/PuertosServlet?id=";
	 // private static final String puertosURL="http://192.168.0.10:8080/nauvalservidor/PuertosServlet?id=";
	/* 
	 * Implementa la funcionalidad definida en el método de la interface, para recuperar
	 * la información se realiza una petición Http de tipo Get al servlet PuertosServlet estableciendo
	 * como parámetro id de la petición el valor all, una vez recibida la respuesta, los datos recibidos
	 * que estarán en formato Json se transformará en objetos de la clase ClubNautico utilizando
	 * la libreria Json de Google.
	 * 
	 */
	@Override
	public List<ClubNautico> recuperarClubesNauticos() {
		HttpClient httpClient = new  DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(new HttpGet(puertosURL + "all"));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK){
				String responseString=EntityUtils.toString(response.getEntity(), HTTP.ISO_8859_1);
				Gson gson=new Gson();
				List<ClubNautico> puertos=gson.fromJson(responseString, new TypeToken<List<ClubNautico>>(){}.getType());
			    return puertos;
			}
			else{
				response.getEntity().getContent().close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * Implementa la funcionalidad definida en el método de la interface, para recuperar
	 * la información se realiza una petición Http de tipo Get al servlet PuertosServlet estableciendo
	 * como parámetro id de la petición el valor numérico correspondiente al id del puerto, del cual
	 * queremos recuperar la información, una vez recibida la respuesta, los datos recibidos
	 * que estarán en formato Json se transformará en objetos de la clase ClubNautico utilizando
	 * la libreria Json de Google.
	 * 
	 */
	@Override
	public ClubNautico recuperaClubNautico(Integer id) {
		HttpClient httpClient = new  DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(new HttpGet(puertosURL + id));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK){
				String responseString=EntityUtils.toString(response.getEntity(), HTTP.ISO_8859_1);
				Gson gson=new Gson();
				ClubNautico puerto=gson.fromJson(responseString, ClubNautico.class);
				return puerto;
			}
			else{
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		// TODO Auto-generated method stub
		return null;
	}
}
