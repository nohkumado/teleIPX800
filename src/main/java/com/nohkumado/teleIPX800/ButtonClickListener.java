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


import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import com.nohkumado.ipx800control.*;

/**
handles clicks on a gridview of buttons
*/
public class ButtonClickListener implements OnClickListener
{
	MainActivity context;

	public static final String TAG = "Bclick";
/**
CTOR
*/
	public ButtonClickListener(MainActivity context)
	{
		this.context = context;
	}
	/**
	onClick
	
	extracts the relai that was clicked, and asks the ipx 
	to open then close the corresponding relai
	*/
	@Override
	public void onClick(View view)
	{
		Ipx800Control ipx = context.getIpx();
		if (view instanceof Button)
		{
			Button button = (Button) view;
			Log.d(TAG, "hit the button " + button.getHint()+ "id = "+button.getId());
			int port2trigger = Integer.parseInt(""+button.getHint());
			Log.d(TAG, "would trigger ipx out "+port2trigger);
			try
			{
				ipx.set(port2trigger,true);
				ipx.set(port2trigger,false);
			}
			catch(Exception e)
			{
				Toast.makeText(context, "error:"+e, Toast.LENGTH_LONG).show();
			}
		}
	}
}
