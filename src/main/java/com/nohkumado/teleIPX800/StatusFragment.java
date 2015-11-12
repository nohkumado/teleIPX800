package com.nohkumado.teleIPX800;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.util.*;

public class StatusFragment extends Fragment
{
	protected MainActivity context;
	public final static String TAG ="StFr";
	protected  View myView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		myView = inflater.inflate(R.layout.statusview, container,false);
		if(myView == null) return myView;
		context = (MainActivity) getContext();
		
		
		IpxStatus status = context.getStatus();
		Button timeBut = (Button) myView.findViewById(R.id.timeView);
		if(timeBut != null) timeBut.setText(status.getDate());
		
		LinearLayout statusView = (LinearLayout) myView.findViewById(R.id.inputStatusView);
		if(statusView != null)
		{
			if(getFragmentManager().findFragmentByTag("inOutFrag") == null)
				getFragmentManager().beginTransaction()
					.add(R.id.inputStatusView, 
					    new LedStatusListFragment(context.getIOstate()),"inOutFrag").commit();
			
			
		}
		//update();
		//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

		//GridView shortcuts = (GridView) myView.findViewById(R.id.shortcutgrid);
		return myView;
	}
	public void update()
	{
		if(myView == null) return;
		if(context == null) return;
		
//		IpxStatus status = context.getStatus();
//		Button timeBut = (Button) myView.findViewById(R.id.timeView);
//		if(timeBut != null) timeBut.setText(status.getDate());
		Log.d(TAG,"about to invalidate status view...");
		myView.invalidate();
	}

  
	
}
