package uk.ac.qub.mindyourmind.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.providers.TaskProvider;

public class CalendarViewAdapter extends BaseAdapter {
	private Context mContext;

	public static final String TAG = "calendarViewAdapter";
	
	Cursor cursor;
	int titleColumnIndex;
	int notesColumnIndex;
	int idColumnIndex;
	int dateTimeColumnIndex;
	
	 
	 private java.util.Calendar month;
	 
	 public GregorianCalendar pmonth; // calendar instance for previous month
	 /**
	  * calendar instance for previous month for getting complete view
	  */
	 public GregorianCalendar pmonthmaxset;
	 private GregorianCalendar selectedDate;
	 int firstDay;
	 int maxWeeknumber;
	 int maxP;
	 int calMaxP;
	 int lastWeekDay;
	 int leftDays;
	 int mnthlength;
	 String itemvalue, curentDateString;
	 DateFormat df;

	 private ArrayList<String> items;
	 public static List<String> dayString;
	 private View previousView;

	 public CalendarViewAdapter(Context c, GregorianCalendar monthCalendar) {
	  CalendarViewAdapter.dayString = new ArrayList<String>();
	  month = monthCalendar;
	  selectedDate = (GregorianCalendar) monthCalendar.clone();
	  mContext = c;
	  month.set(GregorianCalendar.DAY_OF_MONTH, 1);
	  this.items = new ArrayList<String>();
	  df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
	  curentDateString = df.format(selectedDate.getTime());
	  refreshDays();
	 }
/*
	 public void swapCursor(Cursor c) {
		cursor = c;
		if (cursor!=null){
			cursor.moveToFirst();
			titleColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_TITLE);
			notesColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_NOTES);
			idColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_TASKID);
			dateTimeColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_DATE_TIME);
		}
		
		// Print dates of the current week
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.UK);
        Long dateInMillis;
        Date date;
		for (int i=0; i<c.getCount(); i++){
			dateInMillis = c.getLong(c.getColumnIndexOrThrow(TaskProvider.COLUMN_DATE_TIME));
			date=new Date(dateInMillis);
			items.add("0" + df.format(date));
			Log.d(TAG, items.get(i));
		}
		
		notifyDataSetChanged();
	}
	 */
	 public void setItems(ArrayList<String> items) {
		 
		 for (int i=0;i<items.size();i++ ){
			 if(items!=null){
			 items.set(i, 0+ items.get(i));
	   }
	  }
	  this.items = items;
	 }
	 
	 
	 /*
	public int getItemCount() {
		//cursor!=null? cursor.getCount() : 0;
		return  
	}*/

	 public int getCount() {
	  return dayString.size();
	 }

	 public Object getItem(int position) {
	  return dayString.get(position);
	 }

	 public long getItemId(int position) {
	  return 0;
	 }

	 // create a new view for each item referenced by the Adapter
	 public View getView(int position, View convertView, ViewGroup parent) {
	  View v = convertView;
	  TextView dayView;
	  if (convertView == null) { // if it's not recycled, initialize some
	         // attributes
	   LayoutInflater vi = (LayoutInflater) mContext
	     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   v = vi.inflate(R.layout.calendar_item, null);

	  }
	  dayView = (TextView) v.findViewById(R.id.date);
	  // separates daystring into parts.
	  String[] separatedTime = dayString.get(position).split("-");
	  // taking last part of date. ie; 2 from 2012-12-02
	  String gridvalue = separatedTime[2].replaceFirst("^0*", "");
	  // checking whether the day is in current month or not.
	  if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
	   // setting offdays to white color.
	   dayView.setTextColor(Color.WHITE);
	   dayView.setClickable(false);
	   dayView.setFocusable(false);
	  } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
	   dayView.setTextColor(Color.WHITE);
	   dayView.setClickable(false);
	   dayView.setFocusable(false);
	  } else {
	   // setting curent month's days in blue color.
	   dayView.setTextColor(Color.BLUE);
	  }

	  if (dayString.get(position).equals(curentDateString)) {
	   setSelected(v);
	   previousView = v;
	  } else {
		  // change to highlight color
	   v.setBackgroundResource(R.drawable.list_item_background);
	  }
	  dayView.setText(gridvalue);

	  // create date string for comparison
	  String date = dayString.get(position);

	  if (date.length() == 1) {
	   date = "0" + date;
	  }
	  String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
	  if (monthStr.length() == 1) {
	   monthStr = "0" + monthStr;
	  }

	  // show icon if date is not empty and it exists in the items array
	  ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
	  if (date.length() > 0 && items != null && items.contains(date)) {
	   iw.setVisibility(View.VISIBLE);
	  } else {
	   iw.setVisibility(View.INVISIBLE);
	  }
	  return v;
	 }

	 public View setSelected(View view) {
	  if (previousView != null) {
	   previousView.setBackgroundResource(R.drawable.list_item_background);
	  }
	  previousView = view;
	  view.setBackgroundResource(R.drawable.calendar_cel_selectl);
	  return view;
	 }

	 public void refreshDays() {
	  // clear items
	  items.clear();
	  dayString.clear();
	  pmonth = (GregorianCalendar) month.clone();
	  // month start day. ie; sun, mon, etc
	  firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
	  // finding number of weeks in current month.
	  maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
	  // allocating maximum row number for the gridview.
	  mnthlength = maxWeeknumber * 7;
	  maxP = getMaxP(); // previous month maximum day 31,30....
	  calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
	  /**
	   * Calendar instance for getting a complete gridview including the three
	   * month's (previous,current,next) dates.
	   */
	  pmonthmaxset = (GregorianCalendar) pmonth.clone();
	  /**
	   * setting the start date as previous month's required date.
	   */
	  pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

	  /**
	   * filling calendar gridview.
	   */
	  for (int n = 0; n < mnthlength; n++) {

	   itemvalue = df.format(pmonthmaxset.getTime());
	   pmonthmaxset.add(GregorianCalendar.DATE, 1);
	   dayString.add(itemvalue);

	  }
	 }

	 private int getMaxP() {
	  int maxP;
	  if (month.get(GregorianCalendar.MONTH) == month
	    .getActualMinimum(GregorianCalendar.MONTH)) {
	   pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
	     month.getActualMaximum(GregorianCalendar.MONTH), 1);
	  } else {
	   pmonth.set(GregorianCalendar.MONTH,
	     month.get(GregorianCalendar.MONTH) - 1);
	  }
	  maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

	  return maxP;
	 }

	}
