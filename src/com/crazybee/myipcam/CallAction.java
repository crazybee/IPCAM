/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 */

package com.crazybee.myipcam;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class CallAction {
	private String user;
	private String password;
	private String action;
	private String ip;
	private String port;
	private PreferencesService service;
	public CallAction(String user,String password,String ip, String port, String action){
		
		this.user = user;
		this.password = password;
		this.action = action;	
		this.ip = ip;
		this.port = port;
	
	}
	
	public String callPost()
    {    
		System.out.println("callaction starts");
		
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);
	
		System.out.println("ip in callpost is"+ip);
		System.out.println("port in callpost is"+port);
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient(httpParameters);
        HttpPost httppost = new HttpPost("http://"+ip+":"+port+"/form/enet");

            String responseBody = "";

        HttpResponse response = null;

        try {

            String base64EncodedCredentials = "Basic " + Base64.encodeToString( 
                    (user + ":" + password).getBytes(), 
                    Base64.NO_WRAP);


            httppost.setHeader("Authorization", base64EncodedCredentials);

            httppost.setHeader(HTTP.CONTENT_TYPE,"text/html");

//            JSONObject obj = new JSONObject();
//
//            obj.put("day", String.valueOf(2)); 
//            obj.put("emailId", "userTest@gmail.com");
//            obj.put("month", String.valueOf(5));
//            obj.put("year", String.valueOf(2013));


             httppost.setEntity(new StringEntity(action, "UTF-8"));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200) {
                 Log.d("response ok", "ok response :/");
                 System.out.println("RESPONSE OK");
                 
            } else {
                Log.d("response not ok", "Something went wrong :/");
                System.out.println("RESPONSE WRONG");
                System.out.println(response.toString());
                
            }

            responseBody = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("protocol exception");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("io exception");
        }
       
        

        return responseBody;
    }


}
