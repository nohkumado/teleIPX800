package com.nohkumado.teleIPX800;

import android.app.*;
import android.app.ActionBar.*;

public class TabListener implements ActionBar.TabListener
{
	private Fragment fragment;
	// The contructor.
	public TabListener(Fragment fragment) {
		this.fragment = fragment;
	}
	// When a tab is tapped, the FragmentTransaction replaces
	// the content of our main layout with the specified fragment;
	// that's why we declared an id for the main layout.
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) 
	{
		if(ft != null && fragment != null)
		ft.replace(R.id.maincontainer, fragment);
	}
	// When a tab is unselected, we have to hide it from the user's view. 
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
	// Nothing special here. Fragments already did the job.
	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}
}

