package uk.ac.qub.mindyourmind.fragments;

import java.util.Calendar;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;

public class TimePickerDialogFragment extends DialogFragment{
	
	public static final String TIME_PICKER_FRAGMENT = "timePicker";
	static final String HOUR = "hour";
	static final String MINUTE = "minute";
	
	
	
	public static TimePickerDialogFragment newInstance(Calendar time){
		
		TimePickerDialogFragment fragment = new TimePickerDialogFragment();
		
		Bundle args = new Bundle();
		args.putInt(HOUR, time.get(Calendar.HOUR_OF_DAY));
		args.putInt(MINUTE, time.get(Calendar.MINUTE));
		
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		OnTimeSetListener callBack = (OnTimeSetListener) getFragmentManager().findFragmentByTag(DiaryEditFragment.DEFAULT_FRAGMNET_TAG);
		
		Bundle args = getArguments();
		
		return new TimePickerDialog(getActivity(), callBack, args.getInt(HOUR), args.getInt(MINUTE), true);
		
		
	}

}
