/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is the main activity 
 *
 *
 */




package com.crazybee.myipcam;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	private Button go;
	private Button settings;
	//private Button help;
	//private Button more;
	private Button save;
	private EditText ip;
	private EditText port;
	private EditText username;
	private EditText password;
	private String ipgot;
	private String usernamegot;
	private String passwordgot;
	private String portgot;
	private String ipset;
	private String portset;
	private String usernameset;
	private String passwordset;
	private String savestatus;
	private Mediator m;
	private Checker ch;
	public static final int SNAP_VELOCITY = 200;
	private Toast toast;
	private PreferencesService service;

	/**
	 * 屏幕宽度值。
	 */
	private int screenWidth;

	/**
	 * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
	 */
	private int leftEdge;

	/**
	 * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
	 */
	private int rightEdge = 0;

	/**
	 * menu完全显示时，留给content的宽度值。
	 */
	private int menuPadding = 360;

	/**
	 * 主内容的布局。
	 */
	private View content;

	/**
	 * menu的布局。
	 */
	private View menu;

	/**
	 * menu布局的参数，通过此参数来更改leftMargin的值。
	 */
	private LinearLayout.LayoutParams menuParams;

	/**
	 * 记录手指按下时的横坐标。
	 */
	private float xDown;

	/**
	 * 记录手指移动时的横坐标。
	 */
	private float xMove;

	/**
	 * 记录手机抬起时的横坐标。
	 */
	private float xUp;

	/**
	 * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
	 */
	private boolean isMenuVisible;

	/**
	 * 用于计算手指滑动的速度。
	 */
	private VelocityTracker mVelocityTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi == false && internet == false) {
			toast = Toast.makeText(getApplicationContext(),
					"You don't have internet connection!", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(getApplicationContext());
			imageCodeProject.setImageResource(R.drawable.noavatar_middle);
			toastView.addView(imageCodeProject, 0);
			toast.show();

		}

		service = new PreferencesService(this);
		

		go = (Button) findViewById(R.id.GO);
		save = (Button) findViewById(R.id.save);
		settings = (Button) findViewById(R.id.settings);
		//help = (Button) findViewById(R.id.Help);
		//more = (Button) findViewById(R.id.moreproducts);
		ip = (EditText) findViewById(R.id.iped);
		port = (EditText) findViewById(R.id.ported);
		username = (EditText) findViewById(R.id.usered);
		password = (EditText) findViewById(R.id.passed);
		m = new Mediator(ipgot, portgot, usernamegot, passwordgot);
		ch = new Checker();
		initValues();
		findAction();
		content.setOnTouchListener(this);

	}

	@SuppressWarnings("deprecation")
	private void initValues() {

		WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = window.getDefaultDisplay().getWidth();
		content = findViewById(R.id.content);
		menu = findViewById(R.id.menu);
		menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
		// 将menu的宽度设置为屏幕宽度减去menuPadding
		menuParams.width = screenWidth - menuPadding;
		// 左边缘的值赋值为menu宽度的负数
		leftEdge = -menuParams.width;
		// menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
		menuParams.leftMargin = leftEdge;
		// 将content的宽度设置为屏幕宽度
		content.getLayoutParams().width = screenWidth;

		// System.out.println("The IP got is "+ipgot);

		// m.setIp(ipgot);
		// m.setPassword(passwordgot);
		// m.setUsername(usernamegot);
		// m.setPort(portgot);
		// System.out.println("ipget is"+m.getIp());
		// System.out.println(m.getIp().equals(null));
	
		Map<String, String> params = service.getPerferences();
		
			
				
			
		
		username.setText(params.get("name"));
		password.setText(params.get("password"));
		ip.setText(params.get("ip"));
		port.setText(params.get("port"));
		save.setEnabled(true);
		service.saved("false");
		toast = Toast.makeText(getApplicationContext(),
				"Settings are loaded", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		

	}

	private void findAction() {
		// TODO Auto-generated method stub

		// else if (!ch.checkip(ip.getText().toString())){
		//
		//
		//
		// }

		System.out.println(ch.checkip(ip.getText().toString()));
		go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Perform action on click
				if (ch.checkip(ip.getText().toString())
						&& ch.checkport(port.getText().toString())) {
					portgot = port.getText().toString();
					usernamegot = username.getText().toString();
					passwordgot = password.getText().toString();
					ipgot = ip.getText().toString();
					Intent intent = new Intent();
					// 设置传递方向
					intent.setClass(MainActivity.this, Start.class);
					intent.putExtra("username", usernamegot);
					System.out.println("usersent is " + usernamegot);
					intent.putExtra("password", passwordgot);
					intent.putExtra("ip", ipgot);
					intent.putExtra("port", portgot);
					m.setIp(ipgot);
					m.setPassword(passwordgot);
					m.setPort(portgot);
					m.setUsername(usernamegot);

					startActivity(intent);

				} else if (username.getText().toString().equals("")
						|| password.getText().toString().equals("")
						|| ip.getText().toString().equals("")
						|| port.getText().toString().equals("")) {
					toast = Toast.makeText(getApplicationContext(),
							"One of the fields is empty", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				} else if (!ch.checkip(ip.getText().toString())) {
					System.out.println(ch.checkip(ip.getText().toString()));
					System.out.println(ip.getText().toString());
					toast = Toast.makeText(getApplicationContext(),
							"IP is not correct", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				else if (!ch.checkport(port.getText().toString())) {

					toast = Toast.makeText(getApplicationContext(),
							"Port is not correct", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				// currentContext.startActivity(activityChangeIntent);

			}
		});

		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Perform action on click
				if (ch.checkip(ip.getText().toString())
						&& ch.checkport(port.getText().toString())) {
					portgot = port.getText().toString();
					usernamegot = username.getText().toString();
					passwordgot = password.getText().toString();
					ipgot = ip.getText().toString();
					Intent intent = new Intent();
					// 设置传递方向
					intent.setClass(MainActivity.this, Settings.class);
					intent.putExtra("username", usernamegot);
					System.out.println("usersent is " + usernamegot);
					intent.putExtra("password", passwordgot);
					intent.putExtra("ip", ipgot);
					intent.putExtra("port", portgot);
					m.setIp(ipgot);
					m.setPassword(passwordgot);
					m.setPort(portgot);
					m.setUsername(usernamegot);

					startActivity(intent);

				} else if (username.getText().toString().equals("")
						|| password.getText().toString().equals("")
						|| ip.getText().toString().equals("")
						|| port.getText().toString().equals("")) {
					toast = Toast.makeText(getApplicationContext(),
							"One of the fields is empty", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				} else if (!ch.checkip(ip.getText().toString())) {
					System.out.println(ch.checkip(ip.getText().toString()));
					System.out.println(ip.getText().toString());
					toast = Toast.makeText(getApplicationContext(),
							"IP is not correct", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				else if (!ch.checkport(port.getText().toString())) {

					toast = Toast.makeText(getApplicationContext(),
							"Port is not correct", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

			}
		});

//		help.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "help clicked", 300).show();
//				// Perform action on click
//				// Intent intent = new Intent();
//				// //设置传递方向
//				// intent.setClass(MainActivity.this,Start.class);
//				// intent.putExtra("username",usernamegot);
//				// intent.putExtra("password",passwordgot);
//				// intent.putExtra("ip", ipgot);
//				// intent.putExtra("port", portgot);
//				//
//				//
//				// startActivity(intent);
//
//				// currentContext.startActivity(activityChangeIntent);
//
//			}
//		});
//
//		more.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// Perform action on click
//				Toast.makeText(MainActivity.this, "more clicked", 300).show();
//
//				// Intent intent = new Intent();
//				// //设置传递方向
//				// intent.setClass(MainActivity.this,MainActivity1.class);
//				// intent.putExtra("username",usernamegot);
//				// intent.putExtra("password",passwordgot);
//				// intent.putExtra("ip", ipgot);
//				// intent.putExtra("port", portgot);
//				//
//				//
//				// startActivity(intent);
//
//				// currentContext.startActivity(activityChangeIntent);
//
//			}
//		});

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐标
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown);
			if (isMenuVisible) {
				menuParams.leftMargin = distanceX;
			} else {
				menuParams.leftMargin = leftEdge + distanceX;
			}
			if (menuParams.leftMargin < leftEdge) {
				menuParams.leftMargin = leftEdge;
			} else if (menuParams.leftMargin > rightEdge) {
				menuParams.leftMargin = rightEdge;
			}
			menu.setLayoutParams(menuParams);
			break;
		case MotionEvent.ACTION_UP:
			 //手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
			xUp = event.getRawX();
			if (wantToShowMenu()) {
				if (shouldScrollToMenu()) {
					scrollToMenu();
				} else {
					scrollToContent();
				}
			} else if (wantToShowContent()) {
				if (shouldScrollToContent()) {
					scrollToContent();
				} else {
					scrollToMenu();
				}
			}
			recycleVelocityTracker();
			break;
		}
		return true;
        //return false;
	}

	private boolean wantToShowContent() {
		return xUp - xDown < 0 && isMenuVisible;
		 //return false;
	}

	/**
	 * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
	 * 
	 * @return 当前手势想显示menu返回true，否则返回false。
	 */
	private boolean wantToShowMenu() {
		return xUp - xDown > 0 && !isMenuVisible;
		 //return false;
	}

	/**
	 * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
	 * 就认为应该滚动将menu展示出来。
	 * 
	 * @return 如果应该滚动将menu展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToMenu() {
		 //return false;
		return xUp - xDown > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
	 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
	 * 
	 * @return 如果应该滚动将content展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToContent() {
		 //return false;
		return xDown - xUp + menuPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 将屏幕滚动到menu界面，滚动速度设定为30.
	 */
	private void scrollToMenu() {
		new ScrollTask().execute(30);
	}

	/**
	 * 将屏幕滚动到content界面，滚动速度设定为-30.
	 */
	private void scrollToContent() {
		new ScrollTask().execute(-30);
	}

	/**
	 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 *            content界面的滑动事件
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在content界面滑动的速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = menuParams.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge) {
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < leftEdge) {
					leftMargin = leftEdge;
					break;
				}
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			if (speed[0] > 0) {
				isMenuVisible = true;
			} else {
				isMenuVisible = false;
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			menuParams.leftMargin = leftMargin[0];
			menu.setLayoutParams(menuParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			menuParams.leftMargin = leftMargin;
			menu.setLayoutParams(menuParams);
		}
	}

	/**
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void save(View v) {
		
		usernameset = username.getText().toString();
		passwordset = password.getText().toString();
		ipset = ip.getText().toString();
		portset = port.getText().toString();
		service.save(usernameset, passwordset, ipset, portset);
        service.saveled("false");
        service.savemotion("false");
        service.saved("true");
        toast = Toast.makeText(getApplicationContext(),
				"Settings are saved", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Map<String, String> params = service.getPerferences();
		if(!(username.getText().equals(params.get("name"))) || !(password.getText().equals(params.get("password"))) || !(ip.getText().equals(params.get("ip")))
				|| !(port.getText().equals(params.get("port")))){
			username.setText(params.get("name"));
		    password.setText(params.get("password"));
			ip.setText(params.get("ip"));		
			port.setText(params.get("port"));
		}
	}
	   @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			/*
			 * add()方法的四个参数，依次是： 
			 * 1、组别，如果不分组的话就写Menu.NONE,
			 * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单 
			 * 3、顺序，那个菜单现在在前面由这个参数的大小决定
			 * 4、文本，菜单的显示文本
			 */
			menu.add(Menu.NONE, Menu.FIRST + 1, 5, "About").setIcon(
					android.R.drawable.ic_menu_more);
			
			
			return true;

		}
	    
	    

		public boolean onOptionsItemSelected (MenuItem item) {
			switch (item.getItemId()) {
			case Menu.FIRST + 1:
				Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
			   Intent intent = new Intent(this, About.class);
			   startActivity(intent);
				break;
	}
			return false;
	  }

}
