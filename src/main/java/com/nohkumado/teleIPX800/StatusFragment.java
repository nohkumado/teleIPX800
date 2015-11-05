package com.nohkumado.teleIPX800;

import android.app.*;
import android.os.*;
import android.view.*;

public class StatusFragment extends Fragment
{
	protected MainActivity context;
	public final static String TAG ="StFr";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View myView = inflater.inflate(R.layout.statusview, container,false);
		if(myView == null) return myView;
		context = (MainActivity) getContext();

		//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		IpxStatus status = context.getStatus();

		//GridView shortcuts = (GridView) myView.findViewById(R.id.shortcutgrid);
		return myView;
	}
  
	
}
