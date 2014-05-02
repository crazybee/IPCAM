/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is the settings activity, more settings can be added. 
 *
 *
 */


package com.crazybee.myipcam;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crazybee.myipcam.MySlipSwitch.OnSwitchListener;
import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;


public class Settings extends Activity {
	//private CallGet cg;
	//private Button switch_Btn;
	private MySlipSwitch slipswitch_MSL;
	private Button button1;
	private MySlipSwitch slidebutton1;
	private Button button2;
	private MySlipSwitch slidebutton2;
	private Button button3;
	private MySlipSwitch slidebutton3;
	private Button button4;
	private MySlipSwitch slidebutton4;
	private Button button5;
	private MySlipSwitch slidebutton5;
	private Button qrscan;
	private Button qrgen;
	private Button clear;
	private TextView resultTextView;
//	private Button LED_Btn;
	private MySlipSwitch LED_MSL;
    public static final String NAME = "NAME";
    private String password;
	private String ip;
	private String port;
	private String username;
	private String motion;
	private String led;
	private String teststring = "this is the test string from liu";
	private Boolean motionstatus ;
	private Boolean ledstatus ;
	private Toast toast;
	private PreferencesService service;
	private ImageView qrImgImageView;
	
	public static final String PASSWORD = "PASSWORD";
//	private TextView mp4text;  
//    private Spinner mp4spinner;
//    private static final String[] types={"MPEG4","MJPEG"};
//    private ArrayAdapter<String> adapter; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		 Intent intent = getIntent(); 
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
   
	        StrictMode.setThreadPolicy(policy);
	        username = intent.getStringExtra("username");   
	        password = intent.getStringExtra("password");
	        port = intent.getStringExtra("port");
	        ip = intent.getStringExtra("ip");
	       
	        service = new PreferencesService(this); 
			Map<String, String> params = service.getPerferences();  
			
	       //led = params.get("led");
	       motion = params.get("motion");
	       
//	       if (led.equals("true")){
//	    	   ledstatus = true;
//	    	   service.saveled("true");
//	       }
//	       else {
//	    	   ledstatus = false;
//	    	   service.saveled("false");
//	       }
	       if (motion.equals("true")){
	    	   motionstatus = true;
	    	   service.savemotion("true");
	       }
	       else {
	    	   motionstatus = false;
	    	   service.savemotion("false");
	       }
	      
		
		
		//cg = new CallGet(username,password,ip,port,"md.asp");
		findview();
		
	    initvalues();
		
		findaction();
		
	       
	}

	private void initvalues() {
//		InputStream is = null;
//		
//		is = CallGet().httpGet();
//		BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));
//		StringBuffer response = new StringBuffer();
//
//        String line = null;
//        try {
//            line = buffReader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        
//        }
//        while (line != null) {
//            response.append(line);
//            response.append('\n');
//            try {
//                line = buffReader.readLine();
//            } catch (IOException e) {
//                System.out.println(" IOException: " + e.getMessage());
//                e.printStackTrace();
//               
//            }
//        }
//        try {
//            buffReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//           
//        }
//        System.out.println("Response: " + response.toString());
//	
//		// TODO Auto-generated method stub
//		 ledstatus = LED_MSL.getSwitchState();
	        motionstatus = slipswitch_MSL.getSwitchState();
//	        if (ledstatus)  service.saveled("true");
//	        else service.saveled("false");
	        if (motionstatus)  service.savemotion("true");
	        else service.savemotion("false");
		
		
		
	}

	

	private void findaction() {
		// TODO Auto-generated method stub
		 slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {
			
				
				@Override
				public void onSwitched(boolean isSwitchOn) {
					// TODO Auto-generated method stub
					if(isSwitchOn ) {
						
						service.savemotion("true");
						CallAction call = new CallAction(username,password,ip,port,"enet_source=md.asp&enet_avs_md_enable=Yes&enet_avs_md_delay=5&enet_avs_md_email=No&enet_avs_md_ftp=No");
						String responds = call.callPost();
						System.out.println(responds);
						toast = Toast.makeText(getApplicationContext(),
								"Motion detect is on", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						service.savemotion("false");
						CallAction call = new CallAction(username,password,ip,port,"enet_source=md.asp&enet_avs_md_enable=No");
						String responds = call.callPost();
						System.out.println(responds);
						Toast.makeText(Settings.this, "Motion Detection is Off+responds", 300).show();
						
					}
				}
			});
	        
	        
	        
//	        switch_Btn.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					slipswitch_MSL.updateSwitchState(!slipswitch_MSL.getSwitchState());
//					
//					if(slipswitch_MSL.getSwitchState()) {
//						Toast.makeText(Settings.this, "On", 300).show();
//					} else {
//						Toast.makeText(Settings.this, "Off", 300).show();
//					}
//				}
//			});
//	        
//	   	 LED_MSL.setOnSwitchListener(new OnSwitchListener() {
//				
//				@Override
//				public void onSwitched(boolean isSwitchOn) {
//					// TODO Auto-generated method stub
//					if(isSwitchOn) {
//						service.saveled("true");
//						CallGet cg = new CallGet(username,password,"");
//						
//						Toast.makeText(Settings.this, "On", 300).show();
//					} else {
//						service.saveled("false");
//						Toast.makeText(Settings.this, "Off", 300).show();
//					}
//				}
//			});
	        
qrgen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String contentString = "cr"+"&"+username+"&"+password+"&"+ip+"&"+port;
					if (!contentString.equals("")) {
						//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
						qrImgImageView.setImageBitmap(qrCodeBitmap);
					}else {
						Toast.makeText(Settings.this, "Setting info should not be empty", Toast.LENGTH_SHORT).show();
					}
					
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

qrscan.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		//打开扫描界面扫描条形码或二维码
		Intent openCameraIntent = new Intent(Settings.this,CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}
});

clear.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		//打开扫描界面扫描条形码或二维码
		service.save(null,null , null, null);
		toast = Toast.makeText(getApplicationContext(),
				"Settings are cleared!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
});
	        
//	        LED_Btn.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					LED_MSL.updateSwitchState(!LED_MSL.getSwitchState());
//					
//					if(LED_MSL.getSwitchState()) {
//						Toast.makeText(Settings.this, "On", 300).show();
//					} else {
//						Toast.makeText(Settings.this, "Off", 300).show();
//					}
//				}
//			});
	        
//	        mp4spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
//	        {  
//	  
//	            @Override  
//	            public void onItemSelected(AdapterView<?> arg0, View arg1,  
//	                    int arg2, long arg3) {  
//	                // TODO Auto-generated method stub  
//	                mp4text.setText("Video format:"+types[arg2]);  
//	                //设置显示当前选择的项  
//	                arg0.setVisibility(View.VISIBLE);  
//	            }  
//	  
//	            @Override  
//	            public void onNothingSelected(AdapterView<?> arg0) {  
//	                // TODO Auto-generated method stub  
//	                  
//	            }  
//	              
//	        });  
		
	}

	private void findview() {
		// TODO Auto-generated method stub
		 slipswitch_MSL = (MySlipSwitch)findViewById(R.id.main_myslipswitch);
	     slipswitch_MSL.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
	     
	     
	     slipswitch_MSL.setSwitchState(motionstatus);
	    // switch_Btn = (Button)findViewById(R.id.main_button_switch);
	     
//	     slidebutton1 = (MySlipSwitch)findViewById(R.id.slidebutton1);
//	     slidebutton1.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     slidebutton1.setSwitchState(true);
//	     button1 = (Button)findViewById(R.id.button1);
//	     
//	     slidebutton2 = (MySlipSwitch)findViewById(R.id.slidebutton2);
//	     slidebutton2.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     slidebutton2.setSwitchState(true);
//	     button2 = (Button)findViewById(R.id.button2);
//	     
//	     slidebutton3 = (MySlipSwitch)findViewById(R.id.slidebutton3);
//	     slidebutton3.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     slidebutton3.setSwitchState(true);
//	     button1 = (Button)findViewById(R.id.button3);
//	     
//	     slidebutton4 = (MySlipSwitch)findViewById(R.id.slidebutton4);
//	     slidebutton4.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     slidebutton4.setSwitchState(true);
//	     button4 = (Button)findViewById(R.id.button4);
//	     
//	     slidebutton4 = (MySlipSwitch)findViewById(R.id.slidebutton5);
//	     slidebutton4.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     slidebutton4.setSwitchState(true);
//	     button4 = (Button)findViewById(R.id.button5);
	     
//	     LED_MSL = (MySlipSwitch)findViewById(R.id.LED_myslipswitch);
//	     LED_MSL.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
//	     LED_MSL.setSwitchState(ledstatus);
//	     LED_Btn = (Button)findViewById(R.id.LED_button_switch);
//	     
//	     mp4text=(TextView)findViewById(R.id.Video);  
//	     mp4spinner=(Spinner)findViewById(R.id.videospinner);  
//	     adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types); 
//	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		 mp4spinner.setAdapter(adapter); 
		 
	     qrgen = (Button) findViewById(R.id.qrgen);
	     qrImgImageView = (ImageView)findViewById(R.id.iv_qr_image);
	     qrscan  = (Button) findViewById(R.id.qrscan);
//	     resultTextView = (TextView)findViewById(R.id.scan_result);
	     clear = (Button) findViewById(R.id.clear);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Results of the scan
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
//			resultTextView.setText(scanResult);
			String usernameset = StringDecoder.getName(scanResult,1);
			String passwordset = StringDecoder.getName(scanResult,2);
			String ipset = StringDecoder.getName(scanResult,3);
			String portset = StringDecoder.getName(scanResult,4);
			
			service.save(usernameset, passwordset, ipset, portset);
			Intent intent = new Intent();
			intent.setClass(Settings.this, MainActivity.class);
			startActivity(intent);
			
		}
	}

}
