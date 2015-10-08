package com.nohkumado.teleIPX800;
import android.os.*;
import android.preference.*;

public class AppPreferences extends PreferenceActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new AppPreferenceFrag()).commit();
	}
	
}
