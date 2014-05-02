/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is activity for http connection. 
 *
 *
 */

package com.crazybee.myipcam;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
public class Start extends Activity {
	private MjpegView mv;
	private String password;
	private String ip;
	private String port;
	private String username;
	 private Toast toast;
	PowerManager powerManager = null; 
	WakeLock wakeLock = null; 
	@Override
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent(); 
        username = intent.getStringExtra("username");   
        password = intent.getStringExtra("password");
        port = intent.getStringExtra("port");
        ip = intent.getStringExtra("ip");
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        
       
        //sample public cam
       
        String URL = "http://"+ip+":"+port+"/mjpg/video.mjpg"; 
        System.out.println("URL IS "+URL);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mv = new MjpegView(this);
        setContentView(mv);  
        this.powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        
        try {
			try {
				
				mv.setSource(MjpegInputStream.read(URL));
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				 Toast.makeText(Start.this, "url doesn't work", 500).show();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				 Toast.makeText(Start.this, "url doesn't work", 500).show();
				e.printStackTrace();
			} catch (IOException e) {
				 Toast.makeText(Start.this, "url doesn't work", 500).show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			 Toast.makeText(Start.this, "url doesn't work", 500).show();
			e.printStackTrace();
		}//���������Դ����MjpegInputStreamȥ��ȡ
        mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        mv.showFps(true);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mv.stopPlayback();
		this.wakeLock.release(); 

	}
	@Override 
	protected void onResume() { 
	super.onResume(); 
	this.wakeLock.acquire(); 
	} 
	@Override
	protected void onDestroy() {
	        super.onDestroy();
	      mv.stopPlayback();
	      mv.destroyDrawingCache();
	}
	}
