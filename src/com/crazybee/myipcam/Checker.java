/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is class is for validating the user input
 *
 *
 */


package com.crazybee.myipcam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {
	private String ip;
	private String port;
	private String username;
	private String password;
	
	
	public Checker(String ip){
		this.ip = ip;
		
	}
	public Checker(){}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean checkip(String IP){
	
		
		Pattern p=Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		        Matcher m = p.matcher(IP);
		          
		        return m.matches();

		
		
	}
	public Boolean checkport(String port){
		
		Pattern pattern = Pattern.compile("[0-9]*"); 
		return pattern.matcher(port).matches(); 
	}

}
