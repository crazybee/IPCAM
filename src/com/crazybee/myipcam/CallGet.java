package com.crazybee.myipcam;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CallGet {
	private static String user;
	private static String password;
	private static String action;
	private static String ip;
	private static String port;

	public CallGet(String user, String password, String ip, String port, String action){
		System.out.println("callget starts");
		CallGet.user = user;
		System.out.println(user);
		CallGet.password = password;
		System.out.println(password);
		CallGet.ip = ip;
	    CallGet.port = port;
		
		action = "http://"+ip+":"+port+"/"+action;
		System.out.println(action);
		
		
	}
	public  static InputStream httpGet (){
		
			  InputStream content = null;
			  try {
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpGet httpGet = new HttpGet(action);
			    httpGet.addHeader(BasicScheme.authenticate(
			    		 new UsernamePasswordCredentials(user, password),
			    		 "UTF-8", false));
			    HttpResponse httpResponse = httpclient.execute(httpGet);
			    HttpEntity responseEntity = httpResponse.getEntity();
			    System.out.println(responseEntity);
			    content = httpResponse.getEntity().getContent();
			  } catch (Exception e) {
			    Log.d("[GET REQUEST]", "Network exception", e);
			  }
			    return content;
			
		
		
		
	}

}
