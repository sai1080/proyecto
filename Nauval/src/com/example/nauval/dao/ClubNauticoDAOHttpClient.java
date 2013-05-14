package com.example.nauval.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.nauval.modelo.ClubNautico;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClubNauticoDAOHttpClient implements ClubNauticoDAO{
	private static final String puertosURL="http://10.0.0.121:8080/nauvalservidor/PuertosServlet?id=";

	@Override
	public List<ClubNautico> recuperarClubesNauticos() {
		HttpClient httpClient = new  DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(new HttpGet(puertosURL + "all"));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				Gson gson=new Gson();
				List<ClubNautico> puertos=gson.fromJson(responseString, new TypeToken<List<ClubNautico>>(){}.getType());
			    return puertos;
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

	@Override
	public ClubNautico recuperaClubNautico(Integer id) {
		HttpClient httpClient = new  DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(new HttpGet(puertosURL + id));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
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
