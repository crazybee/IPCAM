/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is the activity for saving user settings. 
 *
 *
 */package com.crazybee.myipcam;
import java.util.HashMap;  
import java.util.Map;  
  
import android.content.Context;  
import android.content.SharedPreferences;  
import android.content.SharedPreferences.Editor;  
  
public class PreferencesService {  
    private Context context;  
  
    public PreferencesService(Context context) {  
        this.context = context;  
    }  
    
   

	public PreferencesService(MjpegInputStream mjpegInputStream) {
		// TODO Auto-generated constructor stub
		
	}



	/** 
     * 保存参数 
     * @param name  姓名 
     * @param age   年龄   
     * @param sex   性别 
     */  
    public void save(String name, String password, String ip, String port) {  
        //获得SharedPreferences对象  
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);  
        Editor editor = preferences.edit();  
        editor.putString("name", name);  
        editor.putString("password", password);  
        editor.putString("ip", ip);
        editor.putString("port", port);
        editor.commit();  
    }  
    public void savemotion(String motion){
    	
    	SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    	Editor editor = preferences.edit();  
        editor.putString("motion", motion);
        
      
        editor.commit();  
    	
    	
    }
 public void saveled(String led){
    	
    	SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    	Editor editor = preferences.edit();  
        editor.putString("led", led);
        
      
        editor.commit();  
    	
    	
    }
 
 public void saved(String savestatus){
 	
 	SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
 	Editor editor = preferences.edit();  
     editor.putString("savestatus", savestatus);
     
   
     editor.commit();  
 	
 	
 }
  
    /** 
     * 获取各项参数 
     * @return 
     */  
    public Map<String, String> getPerferences() {  
        Map<String, String> params = new HashMap<String, String>();  
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);  
        params.put("name", preferences.getString("name", "")); 
        params.put("password", preferences.getString("password", ""));
        params.put("ip", preferences.getString("ip", ""));
        params.put("port", preferences.getString("port", ""));
        params.put("motion", preferences.getString("motion", "true"));
        params.put("led", preferences.getString("led", "true"));
        params.put("savestatus", preferences.getString("savestatus", ""));
        return params;  
    }  
      
      
      
}  