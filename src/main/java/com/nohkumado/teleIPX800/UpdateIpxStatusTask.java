package com.nohkumado.teleIPX800;

import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import com.nohkumado.ipx800control.*;
import java.io.*;
import java.nio.charset.*;

/**
  update asynchronously and parse asynchronously , takes a list of 1 ipx to 
	ask for status, and then parses the xml output, sets also the connection flag
*/
public class UpdateIpxStatusTask extends AsyncTask<Ipx800Control, Integer, String>
{
	public static final String TAG = "update-thread";
	ProgressDialog hourglass;
	IpxStatus ipxStatus;

	public UpdateIpxStatusTask(ProgressDialog d, IpxStatus c) 
	{
		hourglass = d;
		ipxStatus =  c;
	}

	@Override
	protected String doInBackground(Ipx800Control[] p1)
	{
		String result = "";
		Log.d(TAG, "updating in bck ground");
		Ipx800Control ipx = p1[0];

		if (ipx != null)
		{
			result = ipx.status();
			//Log.d(TAG,"ipx returned "+result);
			if (result.length() > 0)
			{
				ipxStatus.connected(true);
				InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
				try
				{
					ipxStatus.parse(stream);
				}
				catch (Exception e)
				{ Log.e(TAG, "something went wrong parsing " + result+"\nException "+e);}
			}
		}
		return result;
	}
	@Override
	protected void onPostExecute(String result)
	{
		hourglass.dismiss();

		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute()
	{
		hourglass.show();
		super.onPreExecute();
	}
}
