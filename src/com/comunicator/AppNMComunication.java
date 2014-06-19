package com.comunicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class AppNMComunication {
	static String TAG=AppNMComunication.class.getClass().getSimpleName();
	
 	public AppNMComunication(){
 	} 
	
 	public static synchronized Object InvokeMethod(ArrayList<Parameters> params,String URL,String NAME_SPACE,String METHOD_NAME)throws Exception
    { 
        SoapObject request =new SoapObject(NAME_SPACE,METHOD_NAME); 
        for(PropertyInfo pinfo:params) 
        	request.addProperty(pinfo);   
        SoapSerializationEnvelope envelope = GetEnvelope(request);
        return  MakeCall(URL,envelope,NAME_SPACE,METHOD_NAME);
    } 
 	
    public static synchronized SoapSerializationEnvelope GetEnvelope(SoapObject Soap)throws Exception
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(Soap); 
        return envelope;
    } 
    
    public static synchronized Object MakeCall(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)throws Exception
    {   
	        HttpTransportSE ht = new HttpTransportSE(URL); 
	        ht.debug = true;
	        ht.call(NAMESPACE+METHOD_NAME, Envelope);
        return  Envelope.getResponse(); 
    }
	
	public static synchronized JSONObject InvokeService(String URL) throws Exception
	{
		return new JSONObject(MakeCall_To_Service(URL).toString());
	}
	
	public static synchronized JSONArray InvokeService2(String URL) throws Exception
	{
		return MakeCall_To_Service2(URL);
	}
	public static synchronized StringBuilder MakeCall_To_Service(String url) throws Exception
	{  			
		StringBuilder builder = new StringBuilder();
		Boolean allow_request= new Utils().Build(url);
		if(allow_request==false ){ 
				throw new ComunicatorInvalidException ("Petición Invalida  para el URL " + url);
		}
			@SuppressWarnings({ "deprecation", "resource" })
			HttpClient httpclient = new DefaultHttpClient();  
			
			HttpGet request = new HttpGet(url);                
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			
			HttpResponse response = httpclient.execute(request);
			
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) { 
				throw new Exception ("HTTP Response "+Integer.toString(statusCode));
				//Log.w(TAG, "Error " + statusCode + " for URL " + url); 
				//return null;
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			for (String line = null; (line = reader.readLine()) != null ; ) {
				builder.append(line).append("\n");
			}   
		return builder;     
    }
	
	public static synchronized JSONArray  MakeCall_To_Service2(String url) throws Exception
	{  
		Boolean allow_request= new Utils().Build(url);
		if(allow_request==false ){ 
				throw new ComunicatorInvalidException ("Petición Invalida  para el URL " + url);
		}
			
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient httpclient = new DefaultHttpClient();  

		HttpGet request = new HttpGet(url);                
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		HttpResponse response = httpclient.execute(request);

		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) { 
			throw new Exception ("HTTP Response "+Integer.toString(statusCode));
			//return null;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (String line = null; (line = reader.readLine()) != null ; ) {
			builder.append(line).append("\n");
		}  
		JSONArray arrayjson = new JSONArray(new String(builder));
		return arrayjson;
    }
}
