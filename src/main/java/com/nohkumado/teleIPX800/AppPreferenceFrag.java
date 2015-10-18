package com.nohkumado.teleIPX800;
/*
 Copyright (c) 2015 Bruno Boettcher. All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of Foobar.

 Foobar is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Foobar is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with @filename.  If not, see <http://www.gnu.org/licenses/>.
 */


import android.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;

public class AppPreferenceFrag extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
	public final static String TAG = "PrefFrag";
	/**
	onCreate
	
	adds the preferences from the ressourcefile
	*/
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
/**
onCreateView

handles the special case of the serverport entry which is an int....
*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		for(String key : prefs.getAll().keySet())
		{
			Preference pref = findPreference(key);
			if(pref == null) continue;
			if(key.equals("serverport"))
				pref.setSummary(""+prefs.getInt(key,0));
			else pref.setSummary(prefs.getString(key,key));
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	onSharedPreferenceChanged
	not needed in my code, but still in case i have one listpreference,
	its takes the selected entry to create the summary
	(planned when/if i add background images to the buttons...
	*/
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key)
	{
		Preference pref = findPreference(key);
        Log.d(TAG,"preference change for "+key+" "+pref.getTitle());
		//pref.getTitle();
		if (pref instanceof ListPreference) 
		{
			ListPreference listPref = (ListPreference) pref;
			pref.setSummary(listPref.getEntry());
		}
	}
}
