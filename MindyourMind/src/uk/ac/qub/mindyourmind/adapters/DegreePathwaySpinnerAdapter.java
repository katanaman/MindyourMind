package uk.ac.qub.mindyourmind.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class DegreePathwaySpinnerAdapter extends BaseAdapter implements SpinnerAdapter{

	Context context;
	
	static String[] data = {"Select a degree pathway", "Medicine", "Denistry", "Engineering"};
	
	public DegreePathwaySpinnerAdapter(Context context){
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setTextColor(Color.WHITE);
        text.setText(data[position]);
        return text;
    }
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView text = new TextView(context);
		text.setTextColor(Color.WHITE);
		text.setText(data[position]);
		return text;
	}
}

