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




/**
ButtonAdapter

a class to store the relai-infos extracted from the shared preferences,
only those relais with a changed label will be taken into account

Thanks Mat for the starting help
 @link http://www.stealthcopter.com/blog/2010/09/android-creating-a-custom-adapter-for-gridview-buttonadapter/
*/
import android.content.*;
import android.preference.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class ButtonAdapter extends BaseAdapter
{
	protected Context mContext;
    protected ButtonClickListener clickHandler;
	protected ArrayList<Relai> content = new ArrayList<Relai>();

	public static final String TAG = "ButAd";

	/**
	CTOR
	*/
	public ButtonAdapter(Context c)
	{
		mContext = c;
	}

    /**
	fillData
	
	extract from the preferences wich relais have changed labels,
	create a relai object and store the whole in a list
	
	@argument clickHandler the object that will listen and react to the clicks
	@see Relai
	*/
	public void fillData(ButtonClickListener clickHandler)
	{
		this.clickHandler = clickHandler;
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);

		for (int i=1; i < 33; i++)
		{
			//String actPic = "relai" + i + "_pic";
			String actName = "relai" + i + "_name";
			String actAnswer = "Relai " + i;

			if (sp.contains(actName) && !(sp.getString(actName, actAnswer).equals(actAnswer)))
			{
				Relai relai = new Relai(i, actName, sp.getString(actName, actAnswer));
				content.add(relai);
			}
		}
	}

	/**
	getCount
	
	Total number of Relai's contained within the adapter
	*/
	public int getCount()
	{
		return content.size();
	}

	/**
	getItem
	returns the relai at the given position
	*/
	public Object getItem(int position)
	{
		return content.get(position);
	}

	/**
	
	getItemId
	
	Require for structure, not really used in my code. Can
	be used to get the id of an item in the adapter for 
	manual control.
	*/
	public long getItemId(int position) 
	{
		Relai btn = content.get(position);
		if (btn != null) return btn.pos();
		else return -1;
	}

	/**
	getView
	
	extracts relai-info at the given position,
	if the button exists reuses it, otherwise creates one,
	initializes it with the relai-data, and returns it
	*/
	public View getView(int position, 
						View convertView, ViewGroup parent) 
	{
		Relai relai = content.get(position);
		//Log.d(TAG, "creating button" + position);
		Button btn;
		if (convertView == null) 
		{  
			// if it's not recycled, initialize some attributes
			btn = new Button(mContext);
			btn.setLayoutParams(new ViewGroup.LayoutParams(
									ViewGroup.LayoutParams.WRAP_CONTENT,
									ViewGroup.LayoutParams.WRAP_CONTENT));

			btn.setOnClickListener(clickHandler);

		} 
		else
		{
			btn = (Button) convertView;
		}
		//exus
		btn.setText(relai.label());
		btn .setHint("" + position);
		// filenames is an array of strings
		//btn.setTextColor(Color.WHITE);
		//btn.setBackgroundResource(R.drawable.button);
		//btn.setId(relai.pos());
		
		return btn;
	}
	/**
	 clears the contents of the adapter
	 */
	public void clear()
	{
		content.clear();
	}
}

