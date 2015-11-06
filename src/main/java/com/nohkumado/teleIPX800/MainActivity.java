package com.nohkumado.teleIPX800;
/*
 Copyright (c) 2015 NohKumado. All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of TeleIpx.

 This is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with @filename.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;
import com.nohkumado.ipx800control.*;
import android.view.View.*;


public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener
{
  public final static String TAG ="MA";
  protected Ipx800Control ipx = new Ipx800Control();
  protected IpxStatus status ;
  protected ButtonClickListener clickHandler = new ButtonClickListener(this);
  protected ButtonAdapter buttonArray;
	protected Button serverBut;
	protected boolean bigScreen = false;
	protected ClickoDrome clickView;
	protected StatusFragment statView;

 	private double textSize;

  /**
	 CTOR
	 creates and initializes the status obkect
	 */
	public MainActivity()
  {
		super();
		status = new IpxStatus(ipx) ;	
  }

  /**
   onCreate
   creates if needed a new ButtonAdapter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
		super.onCreate(savedInstanceState);
		//Log.d(TAG, "calling refresh");
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sp.registerOnSharedPreferenceChangeListener(this);
		
		ipx.setHost(sp.getString("servername", "NA"));

		status.refresh(this);
		//Log.d(TAG, "ipx    status = " + status.isConnected());
		/*if(isTablet()) {
		 setTheme(android.R.style.Theme_Light);
		 }
		 else {
		 setTheme(android.R.style.Theme_DeviceDefault);
		 } 
		 */
		setContentView(R.layout.main);
		if (findViewById(R.id.statusLayout) != null)
		{
			bigScreen = true;
			//Log.d(TAG, "big screen!!");
		}
		//else
		//Log.d(TAG, "small screen...");
		Configuration config = getResources().getConfiguration();
		if (config.screenLayout == Configuration.SCREENLAYOUT_SIZE_SMALL) Log.d(TAG, "small layout...");
		else if (config.screenLayout == Configuration.SCREENLAYOUT_SIZE_NORMAL) Log.d(TAG, "normal layout...");
		else if (config.screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE) Log.d(TAG, "large layout...");
		else if (config.screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE) Log.d(TAG, "xlarge layout...");

		//Log.d(TAG, "stored servername = " + sp.getString("servername", "non"));	  
		//Log.d(TAG, "ipx    servername = " + ipx.getHost());


		if (buttonArray == null) buttonArray = new ButtonAdapter(this);
		//Log.d(TAG, "filling grid");
		buttonArray.fillData(clickHandler);

		LinearLayout clickContainer = (LinearLayout) findViewById(R.id.clickodrome);
		if (clickContainer != null)
		{
			if (savedInstanceState != null) return;
			// Add the fragment to the 'fragment_container' FrameLayout
			//Log.d(TAG,"grid? "+findViewById(R.id.shortcutgrid));
			Log.d(TAG, "creating clickodrome...");
			clickView = new ClickoDrome();
			Log.d(TAG, "fetching clickcontainer...");
			getFragmentManager().beginTransaction()
				.add(R.id.clickodrome, clickView).commit();
		}
		LinearLayout statusContainer  = (LinearLayout) findViewById(R.id.statusLayout);
		if (statusContainer != null)
		{
			if (savedInstanceState != null) return;
			statView = new StatusFragment();		

			getFragmentManager().beginTransaction()
				.add(R.id.statusLayout, statView).commit();
		}
		Log.d(TAG, "done create...");
  }

  /**
   onCreateOptionsMenu
   creates the menu atm only the settings
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
  }
  /**
   onOptionsItemSelected
   star the Preference activity when the settings entry is hit
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
		switch (item.getItemId())
		{
			case R.id.menu_item_settings:
				//Toast.makeText(this, "ADD!", Toast.LENGTH_SHORT).show();
				callSettings();
				return true;
			case R.id.menu_item_about:
				FragmentManager fm = getFragmentManager();
				AboutDialogFragment about = new AboutDialogFragment();
				about.show(fm, getResources().getString(R.string.about));
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
  }

	public void callSettings()
	{
		Intent i = new Intent(this, AppPreferences.class);
		startActivity(i);
	}
  /**
   onSharedPreferenceChanged
   force the redraw of the grid on change of settings
   */
  @Override
  public void onSharedPreferenceChanged(SharedPreferences p1, String p2)
  {
		Log.d(TAG,"in main shared preference change handling");
		status.refresh(this);
		serverBut = (Button) findViewById(R.id.servernameValue);

		if (serverBut != null) 
		{
			//Log.d(TAG, "Setting colors ... ipx connected?? " + status.isConnected());
			if (status.isConnected()) serverBut.setTextColor(Color.GREEN);
			else serverBut.setTextColor(Color.RED);
		}

		GridView shortcuts = (GridView) findViewById(R.id.shortcutgrid);
		if (shortcuts != null)shortcuts.invalidate();
		//status.refresh(this);
		buttonArray.fillData(clickHandler);
  }
	/**
	 isTablet

	 tries to find out if its a tablet or a phone....

	 */
	public boolean isTablet() 
	{
    try
		{
			// Compute screen size
			DisplayMetrics dm = getResources().getDisplayMetrics();
			float screenWidth  = dm.widthPixels / dm.xdpi;
			float screenHeight = dm.heightPixels / dm.ydpi;
			double size = Math.sqrt(Math.pow(screenWidth, 2) +
															Math.pow(screenHeight, 2));
			textSize = dm.widthPixels * 0.5 / 100;
			//Set TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size), where the unit parameter is  and the size parameter is percent of the total width of your screen. Normally, for me, .5% of total width for font is suitable. You can experiment by trying other total width percentages, such as 0.4% or 0.3%.
			//This way your

			// Tablet devices should have a screen size greater than 6 inches
			return size >= 6;
    }
		catch (Throwable t)
		{
			Log.e(TAG, "Failed to compute screen size", t);
			return false;
    }

	} 
	/**
	 gridData

	 @returns the adapter with the griddata...
	 */
	public ButtonAdapter gridData()
	{
		return buttonArray;
	}
	/**
   getIpx
   returns the ipx object
   */
  public Ipx800Control getIpx()
  {
		return ipx;
  }

	public View.OnClickListener getClickHandler()
	{
		return clickHandler;
	}

	public boolean isConnected()
	{
		if (status == null) return false;
		return status.isConnected();
	}
	public IpxStatus getStatus()
	{
		return status;
	}
	public void statusUpdated()
	{
		Log.d(TAG, "about to invalidate click view...");
		runOnUiThread(new Runnable() 
			{
				@Override
				public void run() 
				{
					if (clickView != null) clickView.invalidate();
					if (statView != null) statView.update();
				}
			});
	}
}
