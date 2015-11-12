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
	LedStatusList data;

	public LedStatusAdapter(Context c, int textViewResourceId,
													LedStatusList d)
	{
		super(c, textViewResourceId, d.getLabelList());
		context = c;
		data = d;
	}
	
	@Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.labelbutton, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.inputLabel);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.inputIc);
    
		String butLabel = getItem(position);
		
    textView.setText(butLabel);
    // change the icon for Windows and iPhone
    if (data != null && data.getOutState(position)) {
      imageView.setImageResource(R.drawable.ic_light_on);
    } else {
      imageView.setImageResource(R.drawable.ic_light_off);
    }

    return rowView;
  }
} 
