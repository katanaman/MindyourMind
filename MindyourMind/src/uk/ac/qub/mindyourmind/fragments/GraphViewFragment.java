package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.adapters.TaskListAdapter;
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.database.RatingsTable;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.models.RatingsModel.RatingTypes;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.Plot;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
 
/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class GraphViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	
	private final int NUMBER_OF_SLIDERS = 2;
	
    private XYPlot plot;
    public static String DEFAULT_FRAGMENT_TAG = "graphViewFragment";
    private SharedPreferences prefs;
    long userID;
    ArrayList<Long> timeStamp;
    ArrayList<Integer> happiness, satisfaction;
	
    public static GraphViewFragment newInstance(){
    	GraphViewFragment fragment = new GraphViewFragment();
    	return fragment;
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		userID = prefs.getLong(getResources().getString(R.string.pref_current_user), 0l);
		Log.d(DEFAULT_FRAGMENT_TAG, "userId :"+userID);
		if(userID!=0){
			getLoaderManager().initLoader(0, null, this);
		}
		
		timeStamp  = new ArrayList<Long>();
		happiness = new ArrayList<Integer>();
		satisfaction  = new ArrayList<Integer>();
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
//        // fun little snippet that prevents users from taking screenshots
//        // on ICS+ devices :-)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                                 WindowManager.LayoutParams.FLAG_SECURE);
    	View view = inflater.inflate(R.layout.fragment_graph_view, container, false);
        // initialize our XYPlot reference:
        plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);
        
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
        
//        Number[] dates = {
//        		1420761600000,
//        		1104537600
//        }; 
        
        Number[] years = {
                978307200,  // 2001
                1009843200, // 2002
                1041379200, // 2003
                1072915200, // 2004
                1104537600  // 2005
        };
 
        plot.getGraphWidget().setDomainValueFormat(new DecimalFormat("0"));
        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.TRANSPARENT);
        
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(happiness,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Happiness");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(satisfaction, 
        		SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, 
        		"Satisfaction");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(view.getContext().getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
 
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
 
        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(view.getContext().getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);
 
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        
     // hook up the plotUpdater to the data model:
 
        // thin out domain tick labels so they dont overlap each other:
        plot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
        plot.setDomainStepValue(5);
 
        plot.setRangeStepMode(XYStepMode.INCREMENT_BY_VAL);
        plot.setRangeStepValue(10);
 
        plot.setRangeValueFormat(new DecimalFormat("###.#"));
 
        // uncomment this line to freeze the range boundaries:
        plot.setRangeBoundaries(-100, 100, BoundaryMode.FIXED);
 
        // create a dash effect for domain and range grid lines:
        DashPathEffect dashFx = new DashPathEffect(
                new float[] {PixelUtils.dpToPix(3), PixelUtils.dpToPix(3)}, 0);
        plot.getGraphWidget().getDomainGridLinePaint().setPathEffect(dashFx);
        plot.getGraphWidget().getRangeGridLinePaint().setPathEffect(dashFx);
        return view;
 
    }
    
    public void updatePlot(){
    	plot.redraw();
    }
    
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// get values for current user
		String selection = DiaryEntryTable.COLUMN_DIARY_ENTRY_USER_ID + "=?";
	    String[] selectionArgs = {String.valueOf(userID)};
		return new CursorLoader(getActivity(), MindYourMindProvider.RATINGS_URI, null, selection, selectionArgs, null);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// update data points  and  redraw the plot
		HashSet<String> sliderTypes = new HashSet<String>();
		timeStamp  = new ArrayList<Long>();
		happiness = new ArrayList<Integer>();
		satisfaction  = new ArrayList<Integer>();
		Log.d(DEFAULT_FRAGMENT_TAG, "data count :"+data.getCount());
		if(data!=null){
			data.moveToFirst();
			while (!data.isAfterLast()) {
				String type = data.getString(data.getColumnIndex(RatingsTable.COLUMN_RATINGS_TYPE));
				Log.d(DEFAULT_FRAGMENT_TAG, "type :"+type);
				sliderTypes.add(type);
				switch (RatingTypes.valueOf(type)) {
					case HAPPINESS:
						happiness.add(data.getInt(data.getColumnIndex(RatingsTable.COLUMN_RATINGS_VALUE)));
						timeStamp.add(data.getLong(data.getColumnIndex(RatingsTable.COLUMN_RATINGS_TIMESTAMP)));
						break;
	
					case SATISFACTION:
						satisfaction.add(data.getInt(data.getColumnIndex(RatingsTable.COLUMN_RATINGS_VALUE)));
						break;
					default : 
						Log.e(DEFAULT_FRAGMENT_TAG, "unknown ratings type :"+type);
				}
			    data.moveToNext();
			}
		}
		updatePlot();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
	}
}
