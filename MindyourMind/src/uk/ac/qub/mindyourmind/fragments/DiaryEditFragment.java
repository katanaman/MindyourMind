package uk.ac.qub.mindyourmind.fragments;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.activities.DiaryEditActivity;
import uk.ac.qub.mindyourmind.adapters.DiaryEntryListAdapter;
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;
import android.app.LoaderManager;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;


public class DiaryEditFragment extends Fragment implements OnDateSetListener, OnTimeSetListener, LoaderManager.LoaderCallbacks<Cursor> {

	public static final String DEFAULT_FRAGMNET_TAG = "diaryEditFragment";
	
	private static final int MENU_SAVE = 1;
	
	static final String ENTRY_ID = "entry_id";
	static final String ENTRY_DATE_AND_TIME = "entryDateAndTime";
	
	//views
	View rootView;
	EditText titleText;
	EditText notesText;
	TextView dateButton;
	TextView timeButton;
	ImageView imageView;
	SharedPreferences prefs;
	
	long entryId;
	Calendar entryDateAndTime;
	
	/**
	 * factory method to return an instance of the fragment with each id.
	 * @param id
	 * @return fragment
	 */
	public static DiaryEditFragment newInstance(long id) {
		DiaryEditFragment fragment = new DiaryEditFragment();
		Bundle args = new Bundle();
		args.putLong(DiaryEditActivity.EXTRA_DIARYID, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		Bundle arguments = getArguments();
		if(arguments !=null){
			entryId = arguments.getLong(DiaryEditActivity.EXTRA_DIARYID, 0L);
		}
		
		if (savedInstanceState != null){
			entryId = savedInstanceState.getLong(ENTRY_ID);
			entryDateAndTime = (Calendar) savedInstanceState.getSerializable(ENTRY_DATE_AND_TIME);
		}
		
		if (entryDateAndTime == null){
			entryDateAndTime=Calendar.getInstance();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putLong(ENTRY_ID, entryId);
		outState.putSerializable(ENTRY_DATE_AND_TIME, entryDateAndTime);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.fragment_task_edit, container, false);
		
		rootView = v.getRootView();
		titleText = (EditText) v.findViewById(R.id.title);
		notesText = (EditText) v.findViewById(R.id.notes);
		imageView = (ImageView) v.findViewById(R.id.image);
		dateButton = (TextView) v.findViewById(R.id.task_date);
		timeButton = (TextView) v.findViewById(R.id.task_time);
		
		dateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePicker();
				
			}
		});
		
		timeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTimePicker();
				
			}
		});
	
		if (entryId ==0){
			String defaultTitleKey = getString(R.string.pref_task_title_key);
			String defaultTimeKey = getString(R.string.pref_default_time_from_now_key);
			
			String defaultTitle = prefs.getString(defaultTitleKey, null);
			String defaultTime = prefs.getString(defaultTimeKey, null);
			
			if (defaultTitle !=null){
				titleText.setText(defaultTitle);
			}
			if (defaultTime != null && defaultTime.length() > 0){
				entryDateAndTime.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
			}
			
			updateDateAndTimeButtons();
			
		} else {
			//use background loader to get data from db
			getLoaderManager().initLoader(0, null, this);
		}
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		menu.add(0, MENU_SAVE, 0, R.string.confirm).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()){
		
		case MENU_SAVE: //if save button was pressed
			//if entry was a new entry, go to sliders
			if(entryId==0){
				save();
				((OnEditFinished) getActivity()).finishEditingRating();
			} else { //else finish the activity
				save();
				((OnEditFinished) getActivity()).finishEditingEntry();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
     * A helper method to show our Date picker
     */
    private void showDatePicker() {
        // Create a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Create the DatePickerDialogFragment and initialize it with
        // the appropriate values.
        DatePickerDialogFragment newFragment = DatePickerDialogFragment.newInstance( entryDateAndTime );

        // Show the dialog, and name it "datePicker".  By naming it,
        // Android can automatically manage its state for us if it
        // needs to be killed and recreated.
        newFragment.show(ft, DatePickerDialogFragment.DATE_PICKER_FRAGMENT);
    }
    
    /**
     * A helper method to show our Time picker
     */
    private void showTimePicker() {
        // Create a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Create the TimePickerDialogFragment and initialize it with
        // the appropriate values.
        TimePickerDialogFragment fragment =
                TimePickerDialogFragment.newInstance(entryDateAndTime);

        // Show the dialog, and name it "timePicker".  By naming it,
        // Android can automatically manage its state for us if it
        // needs to be killed and recreated.
        fragment.show(ft, TimePickerDialogFragment.TIME_PICKER_FRAGMENT);
    }
    
    /**
     * Call this method whenever the task's date/time has changed and
     * we need to update our date and time buttons.
     */
    private void updateDateAndTimeButtons() {
        // Set the time button text
        DateFormat timeFormat =
                DateFormat.getTimeInstance(DateFormat.SHORT);
        String timeForButton = timeFormat.format(
        		entryDateAndTime.getTime());
        timeButton.setText(timeForButton);

        // Set the date button text
        DateFormat dateFormat = DateFormat.getDateInstance();
        String dateForButton = dateFormat.format(
        		entryDateAndTime.getTime());
        dateButton.setText(dateForButton);
    }
    
    /**
     * This is the method that our Date Picker Dialog will call when
     * the user picks a date in the dialog.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
    	entryDateAndTime.set(Calendar.YEAR, year);
    	entryDateAndTime.set(Calendar.MONTH, monthOfYear);
    	entryDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateAndTimeButtons();
    }

    /**
     * This is the method that our Time Picker Dialog will call when
     * the user picks a time in the dialog.
     */
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
    	entryDateAndTime.set(Calendar.HOUR_OF_DAY, hour);
    	entryDateAndTime.set(Calendar.MINUTE, minute);
        updateDateAndTimeButtons();
    }
    
    private void save(){
    	// put all required values into a contentValues object
    	String title = titleText.getText().toString();
    	ContentValues values = new ContentValues();
    	values.put(DiaryEntryTable.COLUMN_DIARY_ENTRY_TITLE, title);
    	values.put(DiaryEntryTable.COLUMN_DIARY_ENTRY_CONTENT, notesText.getText().toString());
    	values.put(DiaryEntryTable.COLUMN_DIARY_ENTRY_TIMESTAMP, entryDateAndTime.getTimeInMillis());
    	values.put(DiaryEntryTable.COLUMN_DIARY_ENTRY_USER_ID, prefs.getLong(getResources().getString(R.string.pref_current_user), 0));
    	
    	//taskId==0 when we create a new task, otherwise it has an id already
    	if(entryId == 0){
    		Uri itemUri = getActivity().getContentResolver().insert(MindYourMindProvider.DIARY_URI, values);
    		entryId = ContentUris.parseId(itemUri);
    	} else {
    		//update the existing task
    		Uri uri = ContentUris.withAppendedId(MindYourMindProvider.DIARY_URI, entryId);
    		int count = getActivity().getContentResolver().update(uri, values, null, null);
    		
    		//throw error if more than one task is thrown
    		if(count != 1){
    			throw new IllegalStateException("Unable to update "+ entryId);
    		}
    	}
    	
    	Toast.makeText(getActivity(), getString(R.string.entry_saved_message), Toast.LENGTH_SHORT).show();
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri taskUri = ContentUris.withAppendedId(MindYourMindProvider.DIARY_URI, entryId);
		return new CursorLoader(getActivity(),taskUri,null,null,null,null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.getCount() == 0){
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					((OnEditFinished) getActivity()).finishEditingEntry();
				}
			});
			return;
		}
		
		titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DiaryEntryTable.COLUMN_DIARY_ENTRY_TITLE)));
		notesText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DiaryEntryTable.COLUMN_DIARY_ENTRY_CONTENT)));
		Long dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(DiaryEntryTable.COLUMN_DIARY_ENTRY_TIMESTAMP));
		
		Date date = new Date (dateInMillis);
		entryDateAndTime.setTime(date);
		
		// setting image thumbnail
	 	Picasso.with(getActivity()).load(DiaryEntryListAdapter.getImageUrlForTask(entryId)).into(imageView, new Callback() {
			
			@Override
			public void onSuccess() {
				Activity activity = getActivity();
				
				if (activity == null){
					return;
				}
				
				//set activity colours based on that of the image, if available
				Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
				Palette palette = Palette.generate(bitmap, 32);
				int bgColor = palette.getLightMutedColor(0);
				
				if(bgColor!=0){
					rootView.setBackgroundColor(bgColor);
				}
			}
			
			@Override
			public void onError() {
				// do nothing... just stay default
			}
		}); 
		
		updateDateAndTimeButtons();
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to reset
		
	}
}
