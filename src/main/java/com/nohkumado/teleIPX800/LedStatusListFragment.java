package com.nohkumado.teleIPX800;
import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import android.view.*;

public class LedStatusListFragment extends ListFragment
{
	public final static String TAG = "LedStFrag";
	protected LedStatusAdapter listAdapter;
	protected LedStatusList data;


	public LedStatusListFragment()
	{
	}
	public LedStatusListFragment(LedStatusList d)
	{
		data = d;
	}

	public LedStatusAdapter  getAdapter()
	{
		return listAdapter;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null)
		{
			//exists allready? lets hope all is ok....
			Log.d(TAG, "listfrag exists allready");
			return;
		}
		if (data == null) Log.e(TAG, "oy oy no data??");
		if (listAdapter == null) listAdapter = new LedStatusAdapter(getContext(), R.layout.labelbutton, data);
		setListAdapter(listAdapter);
Log.d(TAG,"set adapter with size : "+listAdapter.getCount());		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Log.d(TAG, "clicked " + l + " v " + v + " pos " + position + " id " + id);
	}


}
