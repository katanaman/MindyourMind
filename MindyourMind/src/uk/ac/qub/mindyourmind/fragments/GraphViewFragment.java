package uk.ac.qub.mindyourmind.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.database.RatingsTable;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
 
/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class GraphViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	
	
	public static String DEFAULT_FRAGMENT_TAG = "graphViewFragment";
	private PointF minXY, maxXY;
	private XYPlot plot;
    
    private SharedPreferences prefs;
    long userID;
    
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
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
    	View view = inflater.inflate(R.layout.fragment_graph_view, container, false);
        // initialize our XYPlot reference:
        plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);
        
        //Configure plot layout
        plot.getGraphWidget().setTicksPerRangeLabel(1);
        plot.getGraphWidget().setTicksPerDomainLabel(2);
        plot.getGraphWidget().setRangeValueFormat(new DecimalFormat("###"));
        //custom format for showing the x-axis values as dates and not a unix timestamp 
        plot.getGraphWidget().setDomainValueFormat(new Format(){
			/**
			 * default serial number for Format class
			 */
			private static final long serialVersionUID = 1L;
			// create a simple date format that draws on the year portion of our timestamp.
			@SuppressLint("SimpleDateFormat")
			private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
			@SuppressLint("SimpleDateFormat")
			@Override
			public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
				long timestamp = ((Number) obj).longValue() * 1000;
				Date date = new Date(timestamp);
				Log.d(DEFAULT_FRAGMENT_TAG, "formatting date=" + new SimpleDateFormat("MMMM dd, yyyy").format(date));
				return dateFormat.format(date, toAppendTo, pos);
			}
			@Override
			public Object parseObject(String source, ParsePosition pos) {
				return null;
			}
		});
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 25);
        plot.setRangeBoundaries(0, BoundaryMode.FIXED, 100, BoundaryMode.FIXED);
        
        
        /* reduce the number of range labels
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        // thin out domain tick labels so they dont overlap each other:
        plot.setDomainStepMode(XYStepMode.SUBDIVIDE);
        plot.setDomainStepValue(2);

        plot.setRangeStepMode(XYStepMode.INCREMENT_BY_VAL);
        plot.setRangeStepValue(50);
 
        plot.setRangeUpperBoundary(100, BoundaryMode.FIXED);
        plot.setRangeValueFormat(new DecimalFormat("###"));
        //plot.setRangeBoundaries(0, 100, BoundaryMode.FIXED);
 
        // create a dash effect for domain and range grid lines:
        DashPathEffect dashFx = new DashPathEffect(new float[] {PixelUtils.dpToPix(3), PixelUtils.dpToPix(3)}, 0);
        plot.getGraphWidget().getDomainGridLinePaint().setPathEffect(dashFx);
        plot.getGraphWidget().getRangeGridLinePaint().setPathEffect(dashFx);
        */
        view.setOnTouchListener(new View.OnTouchListener() {
			
        	@Override
            public boolean onTouch(View arg0, MotionEvent event) {
        		Log.d(DEFAULT_FRAGMENT_TAG, "onTouchClicked");
        		updatePlot();
        		
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: // Start gesture
                    firstFinger = new PointF(event.getX(), event.getY());
                    mode = ONE_FINGER_DRAG;
                    stopThread = true;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP: 
                	mode = NONE;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                	
                	distBetweenFingers = spacing(event);
                    // the distance check is done to avoid false alarms
                    if (distBetweenFingers > 5f) {
                        mode = TWO_FINGERS_DRAG;
                    }
                    break;
                    
                case MotionEvent.ACTION_MOVE:
                    if (mode == ONE_FINGER_DRAG) {
                        PointF oldFirstFinger=firstFinger;
                        firstFinger=new PointF(event.getX(), event.getY());
                        lastScrolling=oldFirstFinger.x-firstFinger.x;
                        scroll(lastScrolling);
                        lastZooming=(firstFinger.y-oldFirstFinger.y)/plot.getHeight();
                        if (lastZooming<0)
                            lastZooming=1/(1-lastZooming);
                        else
                            lastZooming+=1;
                        zoom(lastZooming);
                        plot.setDomainBoundaries(minXY.x, maxXY.x, BoundaryMode.AUTO);
                        updatePlot();
         
                    } else if (mode == TWO_FINGERS_DRAG) {
                        float oldDist =distBetweenFingers; 
                        distBetweenFingers=spacing(event);
                        lastZooming=oldDist/distBetweenFingers;
                        zoom(lastZooming);
                        plot.setDomainBoundaries(minXY.x, maxXY.x, BoundaryMode.AUTO);
                        updatePlot();
                    }
                    break;
                }
                return true;
            }
        	
        	// Definition of the touch states
            static final int NONE = 0;
            static final int ONE_FINGER_DRAG = 1;
            static final int TWO_FINGERS_DRAG = 2;
            int mode = NONE;
         
            PointF firstFinger;
            float lastScrolling;
            float distBetweenFingers;
            float lastZooming;
            boolean stopThread = false;
         
            private void zoom(float scale) {
                float domainSpan = maxXY.x - minXY.x;
                float domainMidPoint = maxXY.x        - domainSpan / 2.0f;
                float offset = domainSpan * scale / 2.0f;
                minXY.x=domainMidPoint- offset;
                maxXY.x=domainMidPoint+offset;
            }
         
            private void scroll(float pan) {
                float domainSpan = maxXY.x    - minXY.x;
                float step = domainSpan / plot.getWidth();
                float offset = pan * step;
                minXY.x+= offset;
                maxXY.x+= offset;
            }
         
            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float)Math.sqrt(x * x + y * y);
            }
		});
        
        return view;
 
    }
    
    public void drawLine(ArrayList<Integer> values, final ArrayList<Long> domain, String name, LineAndPointFormatter format){
		
		ArrayList<Number> interleavedXY = new ArrayList<Number>();
		
		for(int i=0 ; i<values.size();i++){
			interleavedXY.add(domain.get(i)/1000);
			interleavedXY.add(values.get(i));
		}
			
    		// same as above
    		XYSeries series = new SimpleXYSeries(interleavedXY, SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, name);

//    		// same as above:
//    		LineAndPointFormatter format = new LineAndPointFormatter();
//    		format.setPointLabelFormatter(new PointLabelFormatter());
//    		format.configure(getActivity().getApplicationContext(),
//    				R.xml.line_point_formatter_with_plf1);
    		plot.addSeries(series, format);
    }
    
    private LineAndPointFormatter[] getLineColours(){
    	PointLabelFormatter plf = new PointLabelFormatter(Color.TRANSPARENT);
    	LineAndPointFormatter[] lineColours = {
    			new LineAndPointFormatter(Color.rgb(0, 200, 0), Color.rgb(200, 0, 0), Color.TRANSPARENT, plf),//green line
    			new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(200,0, 0), Color.TRANSPARENT, plf),//blue line
    			new LineAndPointFormatter(Color.rgb(200, 0, 0), Color.rgb(200,0, 0), Color.TRANSPARENT, plf)//red line
    	};
    	return lineColours;
    }
    
    public void updatePlot(){
    	//redraw and set internal variables for keeping track of the boundaries
    	plot.redraw();
        plot.calculateMinMaxVals();
        minXY=new PointF(plot.getCalculatedMinX().floatValue(),plot.getCalculatedMinY().floatValue());
        maxXY=new PointF(plot.getCalculatedMaxX().floatValue(),plot.getCalculatedMaxY().floatValue());
        Log.d(DEFAULT_FRAGMENT_TAG, "minXY :"+minXY + " maxXY :" + maxXY);
    }
     public void clearPlot(){
    	 plot.clear();
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
		
		int valueColumnIndex = data.getColumnIndex(RatingsTable.COLUMN_RATINGS_VALUE);
		int timeStampColumnIndex = data.getColumnIndex(RatingsTable.COLUMN_RATINGS_TIMESTAMP);
		
		// update data points  and  redraw the plot
		HashSet<String> sliderTypes = new HashSet<String>();
		HashMap<String, ArrayList<Integer>> values = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, ArrayList<Long>> timeStamp = new HashMap<String, ArrayList<Long>>();
		
		Log.d(DEFAULT_FRAGMENT_TAG, "data count :"+data.getCount());
		if(data!=null){
			data.moveToFirst();
			while (!data.isAfterLast()) {
				String type = data.getString(data.getColumnIndex(RatingsTable.COLUMN_RATINGS_TYPE));
				Log.d(DEFAULT_FRAGMENT_TAG, "type :"+type);
				if(sliderTypes.contains(type)){
					values.get(type).add(data.getInt(valueColumnIndex));
					timeStamp.get(type).add(data.getLong(timeStampColumnIndex));
					Log.d(DEFAULT_FRAGMENT_TAG, "type :"+ type);
					Log.d(DEFAULT_FRAGMENT_TAG, "value :"+data.getInt(valueColumnIndex));
					Log.d(DEFAULT_FRAGMENT_TAG, "timeStamp :"+data.getLong(timeStampColumnIndex));
				} else {
					sliderTypes.add(type);
					values.put(type, new ArrayList<Integer>());
					timeStamp.put(type, new ArrayList<Long>());
					values.get(type).add(data.getInt(valueColumnIndex));
					timeStamp.get(type).add(data.getLong(timeStampColumnIndex));
					
					Log.d(DEFAULT_FRAGMENT_TAG, "type :"+ type);
					Log.d(DEFAULT_FRAGMENT_TAG, "value :"+data.getInt(valueColumnIndex));
					Log.d(DEFAULT_FRAGMENT_TAG, "timeStamp :"+data.getLong(timeStampColumnIndex));
				}
				
			    data.moveToNext();
			}
		}
		Log.d(DEFAULT_FRAGMENT_TAG, "number of sliders :"+sliderTypes.size());
		clearPlot();
		LineAndPointFormatter[] lineColours = getLineColours();
		
		for(int i=0; i<sliderTypes.size();i++){
			String type = (String)sliderTypes.toArray()[i];
			drawLine(values.get(type), timeStamp.get(type), type, lineColours[i]);
		}
		updatePlot();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// do nothing
	}

}

