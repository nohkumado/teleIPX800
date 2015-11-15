package com.nohkumado.teleIPX800;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.nohkumado.teleIPX800.*;
import java.util.*;
import android.util.*;

public class LedStatusAdapter extends ArrayAdapter<String>
{
	protected Context context;
	protected LedStatusList data;
	public final static String TAG ="LedStAd";

	public LedStatusAdapter(Context c, int textViewResourceId,
													LedStatusList d)
	{
		super(c, textViewResourceId, d.getLabelList());
		context = c;
		data = d;
	}

	@Override
  public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.labelbutton, parent, false);
    TextView labelView = (TextView) rowView.findViewById(R.id.inputLabel);
    ImageView inputView = (ImageView) rowView.findViewById(R.id.inputIc);
		ImageView relaiView = (ImageView) rowView.findViewById(R.id.ouputIc);
		
		String butLabel = getItem(position);

    labelView.setText(butLabel);
		//Log.d(TAG,"creating view for pos "+position+" is "+butLabel+" state :"+data.getOutState(position));
    
    // change the icon for inputs and outputs
		if (data != null)
		{
			if (data.getInState(position))
				inputView.setImageResource(R.drawable.ic_light_on);
			else
				inputView.setImageResource(R.drawable.ic_light_off);
			if (data.getOutState(position))
				relaiView.setImageResource(R.drawable.ic_relai_zu);
			else
				relaiView.setImageResource(R.drawable.ic_relai_offen);
		}
    return rowView;
  }
} 
