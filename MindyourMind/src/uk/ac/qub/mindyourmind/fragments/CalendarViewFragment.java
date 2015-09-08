package uk.ac.qub.mindyourmind.fragments;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.adapters.CalendarViewAdapter;
import uk.ac.qub.mindyourmind.providers.TaskProvider;


public class CalendarViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	public static final String DEFAULT_FRAGMNET_TAG = "calendarViewFragment";
	
	
	public GregorianCalendar month, itemmonth;// calendar instances.
	
	public CalendarViewAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot marker.
	public ArrayList<String> items; // container to store calendar items which
	                                // needs showing the event marker
	//public Cursor c;

	public static CalendarViewFragment newInstance() {
		
		CalendarViewFragment fragment = new CalendarViewFragment();
		return fragment;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();
        items = new ArrayList<String>();
        adapter = new CalendarViewAdapter(getActivity(), month);
        
        
       // getLoaderManager().initLoader(0, null, this);
    }     

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {     
        
    	View view = inflater.inflate(R.layout.fragment_calendar_view, container, false);
        Locale.setDefault( Locale.UK );
       
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        
        gridview.setAdapter(adapter);
        
        handler = new Handler();
        handler.post(calendarUpdater);
        
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
        
        RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);
        
        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {

                ((CalendarViewAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalendarViewAdapter.dayString.get(position);
                Log.d(DEFAULT_FRAGMNET_TAG, selectedGridDate);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarViewAdapter) parent.getAdapter()).setSelected(v);

                showToast(selectedGridDate);

            }
        });
        
        return view;
    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
        TextView title = (TextView) getActivity().findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    
    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
        	
        	/*if (items.size()>0){
        		adapter.setItems(items);
                adapter.notifyDataSetChanged();
        	}*/
        	
        	
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.UK);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add("2015-08-29");
                items.add("2015-09-07");
                items.add("2015-09-15");
                items.add("2015-09-20");
                items.add("2015-11-30");
                items.add("2015-11-28");
            }

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }; 
    
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), TaskProvider.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d(DEFAULT_FRAGMNET_TAG, data.toString());
		//adapter.swapCursor(data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		//adapter.swapCursor(null);
		
	}
}