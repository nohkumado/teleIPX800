package com.nohkumado.teleIPX800;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.GridLayout.*;
import com.nohkumado.teleIPX800.*;
import com.nohkumado.ipx800control.*;
/*
 error report:
 java.lang.NoSuchMethodError: No virtual method 
 getContext()Landroid/content/Context; 
 in class Lcom/nohkumado/teleIPX800/ClickoDrome; 
 or its super classes (declaration of 'com.nohkumado.teleIPX800.ClickoDrome' 
 appears in /data/app/com.nohkumado.teleIPX800-2/base.apk)
 at com.nohkumado.teleIPX800.ClickoDrome.onCreate(ClickoDrome.java:25)
 at android.app.Fragment.performCreate(Fragment.java:2075)
 at android.app.FragmentManagerImpl.moveToState(FragmentManager.java:868)
 at android.app.FragmentManagerImpl.moveToState(FragmentManager.java:1082)
 at android.app.BackStackRecord.run(BackStackRecord.java:833)
 at android.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:1467)
 at android.app.Activity.performStart(Activity.java:6328)
 at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2665)
 at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2767)
 at android.app.ActivityThread.access$900(ActivityThread.java:177)
 at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1449)
 at android.os.Handler.dispatchMessage(Handler.java:102)
 at android.os.Looper.loop(Looper.java:145)
 at android.app.ActivityThread.main(ActivityThread.java:5951)
 at java.lang.reflect.Method.invoke(Native Method)
 at java.lang.reflect.Method.invoke(Method.java:372)
 at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1400)
 at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1195)
 #
 */

public class ClickoDrome extends Fragment
{
	protected MainActivity context;
	protected Button serverBut;
	protected View myView;
	public final static String TAG ="ClDr";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = (MainActivity) getContext();
		//Log.d(TAG, "done Clickodrome create...");

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//Log.d(TAG, "entering Clickodrome createView... container: " + container);
		//Log.d(TAG, "previous state: " + savedInstanceState);
		myView = inflater.inflate(R.layout.clickodrome, container, false);
		if (myView == null) return myView;
		context = (MainActivity) getActivity();

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		Ipx800Control ipx = context.getIpx();

		GridView shortcuts = (GridView) myView.findViewById(R.id.shortcutgrid);
		if (shortcuts == null)
		{
			//Log.d(TAG, "no grid found...");
			LinearLayout mainWindow = (LinearLayout)myView.findViewById(R.id.maincontainer);
			//Log.d(TAG,"mainwin "+mainWindow);

			shortcuts = new GridView(context);
			shortcuts.setId(mainWindow.generateViewId());
			shortcuts.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			shortcuts.setBackgroundColor(Color.WHITE);
			shortcuts.setNumColumns(3);
			shortcuts.setColumnWidth(GridView.AUTO_FIT);
			shortcuts.setVerticalSpacing(5);
			shortcuts.setHorizontalSpacing(5);
			shortcuts.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			mainWindow.addView(shortcuts);
			//else Log.e(TAG,"couldn't find mainWindow???");
		}
		shortcuts.setAdapter(context.gridData());


		serverBut = (Button) myView.findViewById(R.id.servernameValue);
		if (serverBut != null) 
		{
			serverBut.setText(sp.getString("servername", ipx.getHost()));
			//WTF?? between the CTOR and onCreate this should exist...
			//if (status == null) status = new IpxStatus(ipx);
			//Log.d(TAG, "Setting colors ... ipx connected?? " + status.isConnected());
			if (context.isConnected()) serverBut.setTextColor(Color.GREEN);
			else serverBut.setTextColor(Color.RED);
			serverBut.setOnClickListener(context.getClickHandler());
			//Log.d(TAG, "test size = " + (int)textSize);
			//serverBut.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)textSize);
		}
		Button field = (Button) myView.findViewById(R.id.portValue);
		if (field != null) field.setText("" + sp.getInt("serverport", ipx.getPort()));

		//Log.d(TAG, "done Clickodrome createView...");

		return myView;
	}

	public void invalidate()
	{
		if (myView != null) myView.invalidate();	
	}
}
