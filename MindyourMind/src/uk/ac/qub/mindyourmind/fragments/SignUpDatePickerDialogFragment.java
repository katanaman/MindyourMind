package uk.ac.qub.mindyourmind.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class SignUpDatePickerDialogFragment extends DialogFragment {
	
	

	public static final String DATE_PICKER_FRAGMENT = "datePicker";
	static final String YEAR = "year";
	static final String MONTH = "month";
	static final String DAY = "day";
	
	 public static SignUpDatePickerDialogFragment newInstance(Calendar date) {
	        SignUpDatePickerDialogFragment fragment =new SignUpDatePickerDialogFragment();

	        Bundle args = new Bundle();
	        args.putInt(YEAR, date.get(Calendar.YEAR));
	        args.putInt(MONTH, date.get(Calendar.MONTH));
	        args.putInt(DAY, date.get(Calendar.DAY_OF_MONTH));
	        fragment.setArguments(args);

	        return fragment;
	    }
	 
	 @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

	        // Find the TaskEditFragment that created this dialog by name.
	        // We'll use that fragment as the edit callback,
	        // so that when the user chooses a new date in our datepicker
	        // dialog, the dialog will call back into the edit fragment to
	        // set the new date.
	        OnDateSetListener callback = (OnDateSetListener)getFragmentManager().findFragmentByTag(PersonalDetailsFragment.DEFAULT_FRAGMNET_TAG);

	        // Construct a new DatePicker dialog that will be hosted by
	        // this fragment. Set its Year, Month, and Day to the values
	        // specified in the args bundle
	        Bundle args = getArguments();
	        return new DatePickerDialog(getActivity(), callback,args.getInt(YEAR),args.getInt(MONTH),args.getInt(DAY));
		
	}
}
