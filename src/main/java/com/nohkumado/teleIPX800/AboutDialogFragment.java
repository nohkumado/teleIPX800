package com.nohkumado.teleIPX800;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;

public class AboutDialogFragment extends DialogFragment
{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) 
		{

			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			//TextView msg = new TextView(getActivity());
			//msg.setText(Html.fromHtml(getResources().getString(R.string.aboutcnt)));
			builder.setTitle(R.string.about);
			//builder.setView(msg);
			builder.setCancelable(false);
			
			builder.setMessage(Html.fromHtml(getResources().getString(R.string.aboutcnt)))
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
			// Create the AlertDialog object and return it
			return builder.create();
		}
}
