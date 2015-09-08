package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import uk.ac.qub.mindyourmind.R;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.Arrays;
 
/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class GraphViewFragment extends Fragment
{
    private XYPlot plot;
    
    public static String DEFAULT_FRAGMENT_TAG = "graphViewFragment";
 
    
    public static GraphViewFragment newInstance(){
    	GraphViewFragment fragment = new GraphViewFragment();
    	return fragment;
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
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Happiness");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Satisfaction");
 
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
        
        return view;
 
    }
}
