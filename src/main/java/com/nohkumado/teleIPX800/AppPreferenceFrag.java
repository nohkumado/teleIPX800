package com.nohkumado.teleIPX800;
import android.preference.*;
import android.os.*;

public class AppPreferenceFrag extends PreferenceFragment
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
