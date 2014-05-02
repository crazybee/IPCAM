/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is the getter and setter class. It's already deprecated since ver 1.2
 *
 *
 */

package com.crazybee.myipcam;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;

public class Mediator {
	public static String ip;
	public static String port;
	public static String username;
	public static String password;
	
	public Mediator(String ip, String port, String username, String password){
		
		Mediator.ip = ip;
		Mediator.port = port;
		Mediator.username = username;
		Mediator.password = password;
		
		
		
	}
	public Mediator(){
		
	}

	public static String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		Mediator.ip = ip;
	}

	public static String getPort() {
		return port;
	}

	public void setPort(String port) {
		Mediator.port = port;
	}

	public static String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		Mediator.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		Mediator.password = password;
	}
	
	
	public boolean saveInfoToFile(Context context){  
        try {  
            File parentfile = context.getFilesDir();//  /data/data/com.itxushuai.login/files  
            File des = new File(parentfile,"info.txt");//  /data/data/com.itxushuai.login/files/info.txt  
            FileOutputStream fos = new FileOutputStream(des);  
            String info = username+"&"+password; 
            
            fos.write(info.getBytes());  
            fos.close();  
            return true ;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  

}
}
