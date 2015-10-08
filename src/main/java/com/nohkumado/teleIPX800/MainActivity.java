package com.nohkumado.teleIPX800;

import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewGroup.*;
import android.widget.*;

import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener
{


	public final static String TAG ="MA";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
            case R.id.menu_item_settings:
                //Toast.makeText(this, "ADD!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, AppPreferences.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs)
	{
		View v =  super.onCreateView(parent, name, context, attrs);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		if (parent == null)
		{
			//Log.e(TAG, "no parentview found....");
			return v;
		}
		//else Log.d(TAG, "proceeding with createview");
		/*if(v == null)
		 {
		 Log.e(TAG,"no view found....");
		 return v;
		 }*/
		TextView field = (TextView) parent.findViewById(R.id.servernameValue);
		if (field != null) field.setText(sp.getString("servername", "NA"));
		field = (TextView) parent.findViewById(R.id.portValue);
		if (field != null) field.setText(sp.getString("serverport", "NA"));

		GridLayout shortcuts = (GridLayout) parent.findViewById(R.id.shortcutgrid);
		if (shortcuts != null)
		{
			//Log.d(TAG, "creating the buttons");
			shortcuts.removeAllViews();
			for (int i=0; i < 16; i++)
			{
				//Log.d(TAG, "creating button" + i);
				Button aButton = new Button(context);
				aButton.setText("action" + i);
				aButton.setHint("a" + 1);
				aButton.setLayoutParams(new LayoutParams(
											ViewGroup.LayoutParams.WRAP_CONTENT,
											ViewGroup.LayoutParams.WRAP_CONTENT));

				aButton.setOnClickListener(this);
				shortcuts.addView(aButton);
			}
			parent.invalidate();
			/*
			 or(int i = 0 ; i < NUMBER_OF_IMAGE_BUTTONS; i++){
			 imageButtons[i] = new ImageButton(this);
			 imageButtons[i].setImageResource(R.drawable.bola_verde);
			 imageButtons[i].setLayoutParams(lp);
			 imageButtons[i].setOnClickListener(mGreenBallOnClickListener);
			 imageButtons[i].setBackgroundColor(Color.TRANSPARENT); 
			 imageButtons[i].setTag(i);
			 imageButtons[i].setId(i);
			 gameBoard.addView(imageButtons[i]);
			 }
			 */
		}
		//else
			//Log.d(TAG, "no grid found...");

		return v;
	}
	@Override
	public void onClick(View view)
	{
		if (view instanceof Button)
		{
			Button button = (Button) view;
			Log.d(TAG, "hit the button " + button.getHint());
		}
	}



}
