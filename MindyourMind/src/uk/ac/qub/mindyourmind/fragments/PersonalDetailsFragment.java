package uk.ac.qub.mindyourmind.fragments;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnPersonalDetailsFinished;

public class PersonalDetailsFragment extends Fragment implements OnDateSetListener {
	
	public static final String DEFAULT_FRAGMNET_TAG = "personalDetailsFragment";
	
	EditText ETName, ETPassword, ETConfirmPassword, ETPasscode, ETConfirmPasscode, ETDateOfBirth, ETGender;
	Calendar dateOfBirth;
	
	
	static final String USER_NAME = "user_name";
	static final String USER_PASSWORD = "user_password";
	static final String USER_PASSCODE = "user_password";
	static final String USER_CONFIRM_PASSWORD = "user_confirm_password";
	static final String USER_CONFIRM_PASSCODE = "user_confirm_password";
	static final String USER_DATE_OF_BIRTH = "user_date_of_birth";
	
	public static PersonalDetailsFragment newInstance() {
		return new PersonalDetailsFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			dateOfBirth = (Calendar) savedInstanceState.getSerializable(USER_DATE_OF_BIRTH);
		}
		if (dateOfBirth == null){
			dateOfBirth=Calendar.getInstance();
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_personal_details, container, false);
		
		ETName = (EditText) v.findViewById(R.id.editTextName);
		ETPassword = (EditText) v.findViewById(R.id.editTextPassword);
		ETConfirmPassword = (EditText) v.findViewById(R.id.editTextConfirmPassword);
		ETPasscode = (EditText) v.findViewById(R.id.editTextPasscode);
		ETConfirmPasscode = (EditText) v.findViewById(R.id.editTextConfirmPasscode);
		ETGender = (EditText) v.findViewById(R.id.editTextGender);
		ETDateOfBirth = (EditText) v.findViewById(R.id.editTextDOB);
		
		ETDateOfBirth.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});
		
		
		ETGender.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		if(savedInstanceState != null){
			ETName.setText(savedInstanceState.getString(USER_NAME));
			ETPassword.setText(savedInstanceState.getString(USER_PASSWORD));
			ETConfirmPassword.setText(savedInstanceState.getString(USER_CONFIRM_PASSWORD));
			ETPasscode.setText(savedInstanceState.getString(USER_PASSCODE));
			ETConfirmPasscode.setText(savedInstanceState.getString(USER_CONFIRM_PASSCODE));
		}
		
		return v;
	}
	
	public boolean checkDetails(){
		boolean isCorrect = false;
		
		if (ETPassword.getText().toString().equals(ETConfirmPassword.getText().toString())){
			isCorrect = true;
		} else {
			Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if ((ETPasscode.getText().toString()).equals(ETConfirmPasscode.getText().toString())){
			isCorrect = true;
		} else {
			Toast.makeText(getActivity(), "Passcodes do not match", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return isCorrect;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(USER_NAME, ETName.getText().toString());
		outState.putString(USER_PASSWORD, ETPassword.getText().toString());
		outState.putString(USER_CONFIRM_PASSWORD, ETConfirmPassword.getText().toString());
		outState.putString(USER_PASSCODE, ETPasscode.getText().toString());
		outState.putString(USER_CONFIRM_PASSCODE, ETConfirmPasscode.getText().toString());
		outState.putSerializable(USER_DATE_OF_BIRTH, dateOfBirth);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_login_sign_up, menu);
		//menu.add(0, MENU_SAVE, 0, R.string.confirm).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()){
		
		case R.id.menu_save: //if save button was pressed
			
			if(checkDetails()){
				//save();
				Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
				((OnPersonalDetailsFinished)getActivity()).goToMainMenu();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		dateOfBirth.set(Calendar.YEAR, year);
		dateOfBirth.set(Calendar.MONTH, monthOfYear);
		dateOfBirth.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		updateDateEditText();
	}
	
	private void updateDateEditText() {
        // Set the date button text
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDateOfBirth = dateFormat.format(dateOfBirth.getTime());
        ETDateOfBirth.setText(formattedDateOfBirth);
    }
	
	/**
     * A helper method to show our Date picker
     */
    private void showDatePicker() {
        // Create a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Create the DatePickerDialogFragment and initialize it with
        // the appropriate values.
        SignUpDatePickerDialogFragment newFragment = SignUpDatePickerDialogFragment.newInstance(dateOfBirth);

        // Show the dialog, and name it "datePicker".  By naming it,
        // Android can automatically manage its state for us if it
        // needs to be killed and recreated.
        newFragment.show(ft, SignUpDatePickerDialogFragment.DATE_PICKER_FRAGMENT);
    }
}
