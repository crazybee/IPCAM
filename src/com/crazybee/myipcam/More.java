/**
 * IPCam controller for Sitecom WL-405
 * @author Z.liu
 * @modified by Z.liu
 * @date 1-nov-2013
 *This is the activity for future inplementation. Invisible in ver 1.4. 
 *
 *
 */

package com.crazybee.myipcam;

import com.crazybee.myipcam.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class More extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more, menu);
		return true;
	}

}
