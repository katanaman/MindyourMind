package uk.ac.qub.mindyourmind.fragments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.R.color;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.database.UserUniversityTable;
import uk.ac.qub.mindyourmind.interfaces.OnPersonalDetailsFinished;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

public class PersonalDetailsFragment extends Fragment implements OnDateSetListener{
	
	public static final String DEFAULT_FRAGMNET_TAG = "personalDetailsFragment";
	
	private final int MINIMUM_AGE_OF_STUDENT = 18;
	
	EditText ETName, ETPassword, ETConfirmPassword, ETPasscode, ETConfirmPasscode, ETDateOfBirth, ETGender;
	Calendar dateOfBirth;
	
	ArrayList<String> genders;
	SpinnerDialogFragment sdf;
	
	long userId;
	String university, degreePathway, universityEmail; 
	int yearOfStudy;
	SharedPreferences prefs;
	
	static final String USER_ID = "user_id";
	static final String USER_NAME = "user_name";
	static final String USER_PASSWORD = "user_password";
	static final String USER_PASSCODE = "user_password";
	static final String USER_CONFIRM_PASSWORD = "user_confirm_password";
	static final String USER_CONFIRM_PASSCODE = "user_confirm_password";
	static final String USER_DATE_OF_BIRTH = "user_date_of_birth";
	
	public static PersonalDetailsFragment UpdateDetails(long userId) {
		PersonalDetailsFragment fragment = new PersonalDetailsFragment();
		Bundle args = new Bundle();
		args.putLong(UserTable.COLUMN_USER_ID, userId);
		fragment.setArguments(args);
		return fragment;
	}
	
	public static PersonalDetailsFragment newInstance(long userId, String university, String degreePathway, String universityEmail, int yearOfStudy) {
		
		PersonalDetailsFragment fragment = new PersonalDetailsFragment();
		Bundle args = new Bundle();
		args.putLong(UserTable.COLUMN_USER_ID, userId);
		args.putString(UserTable.COLUMN_USER_EMAIL, universityEmail);
		args.putString(UserUniversityTable.COLUMN_USER_UNIVERSITY_NAME, university);
		args.putString(UserUniversityTable.COLUMN_USER_UNIVERSITY_FACULTY, degreePathway);
		args.putInt(UserUniversityTable.COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY, yearOfStudy);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		genders = new ArrayList<String>();
		genders.add("Male");
		genders.add("Female");
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		Bundle arguments = getArguments();
		if(arguments !=null){
			userId = arguments.getLong(UserTable.COLUMN_USER_ID, 0L);
			universityEmail= arguments.getString(UserTable.COLUMN_USER_EMAIL, "");
			university = arguments.getString(UserUniversityTable.COLUMN_USER_UNIVERSITY_NAME, "");
			degreePathway = arguments.getString(UserUniversityTable.COLUMN_USER_UNIVERSITY_FACULTY, "");
			yearOfStudy= arguments.getInt(UserUniversityTable.COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY, 0);
		}
		
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
				 sdf = new SpinnerDialogFragment(getActivity(), genders, new SpinnerDialogFragment.DialogListener() {
					
					@Override
					public void ready(int n) {
						ETGender.setText(genders.get(n));
					}
					
					@Override
					public void cancelled() {
						sdf.dismiss();
					}
				});
				sdf.show();
			}
		});
		
		if(university.length()==0){
			ETName.setText("Change password");
			ETName.setEnabled(false);
			ETName.setTextColor(color.black);
			ETGender.setVisibility(View.GONE);
			ETDateOfBirth.setVisibility(View.GONE);
		}
		
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
		
		if(ETDateOfBirth.getText().toString().isEmpty()){
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
			String dateInString = "07/06/2013";

			try {
				Date date = formatter.parse(dateInString);
				System.out.println(date);
				System.out.println(formatter.format(date));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return isCorrect;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putLong(USER_ID, userId);
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
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()){
		
		case R.id.menu_save: //if save button was pressed
			
			if(checkDetails()){
				save();
				((OnPersonalDetailsFinished)getActivity()).goToMainMenu();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if(year <= currentYear-MINIMUM_AGE_OF_STUDENT){
		dateOfBirth.set(Calendar.YEAR, year);
		dateOfBirth.set(Calendar.MONTH, monthOfYear);
		dateOfBirth.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		updateDateEditText();
		} else {
			String message = "You must me at least "+MINIMUM_AGE_OF_STUDENT+" to apply";
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void updateDateEditText() {
        // Set the date button text
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDateOfBirth = dateFormat.format(dateOfBirth.getTime());
        ETDateOfBirth.setText(formattedDateOfBirth);
    }
	
	/**
     * A helper method to show Date picker
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
    
    private void save(){    	

		// put all required values into a contentValues object
		String name = ETName.getText().toString();
		String password = ETPassword.getText().toString();
		String passcode = ETPasscode.getText().toString();
		String gender = ETGender.getText().toString();
		long dob = dateOfBirth.getTimeInMillis();

    	if(university.length()!=0){
        	setCurrentUser();
        		
    		ContentValues UserValues = new ContentValues();
    		UserValues.put(UserTable.COLUMN_USER_ID, userId);
    		UserValues.put(UserTable.COLUMN_USER_NAME, name);
    		UserValues.put(UserTable.COLUMN_USER_PASSWORD, password);
    		UserValues.put(UserTable.COLUMN_USER_CODE, passcode);
    		UserValues.put(UserTable.COLUMN_USER_GENDER, gender);
    		UserValues.put(UserTable.COLUMN_USER_EMAIL, universityEmail);
    		UserValues.put(UserTable.COLUMN_USER_DATE_OF_BIRTH, dob);

    		ContentValues UniversityValues = new ContentValues();
    		UniversityValues.put(UserUniversityTable.COLUMN_USER_UNIVERSITY_NAME, university);
    		UniversityValues.put(UserUniversityTable.COLUMN_USER_UNIVERSITY_FACULTY, degreePathway);
    		UniversityValues.put(UserUniversityTable.COLUMN_USER_UNIVERSITY_YEAR_OF_STUDY, yearOfStudy);
    		UniversityValues.put(UserUniversityTable.COLUMN_USER_UNIVERSITY_USER_ID, userId);

    		Uri UserUri = getActivity().getContentResolver().insert(MindYourMindProvider.USER_URI, UserValues);
    		Uri UniUri = getActivity().getContentResolver().insert(MindYourMindProvider.EDUCATION_URI, UniversityValues);

    		Log.d(DEFAULT_FRAGMNET_TAG, "user URI : "+ UserUri.toString());
    		Log.d(DEFAULT_FRAGMNET_TAG, "university URI : "+ UniUri.toString());
    	
    	} else {
    		
    		ContentValues UserValues = new ContentValues();
    		UserValues.put(UserTable.COLUMN_USER_NAME, name);
    		UserValues.put(UserTable.COLUMN_USER_PASSWORD, password);
    		UserValues.put(UserTable.COLUMN_USER_CODE, passcode);
    		UserValues.put(UserTable.COLUMN_USER_GENDER, gender);
    		UserValues.put(UserTable.COLUMN_USER_EMAIL, universityEmail);
    		UserValues.put(UserTable.COLUMN_USER_DATE_OF_BIRTH, dob);
    	
    	Uri uri = ContentUris.withAppendedId(MindYourMindProvider.USER_URI, userId);
    	int count = getActivity().getContentResolver().update(uri, UserValues, null, null);
		Log.d(DEFAULT_FRAGMNET_TAG, "number of rows affected : "+count);
    	}
    	Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
	}
    
    private void setCurrentUser(){
		Editor editor = prefs.edit();
		editor.putLong(getResources().getString(R.string.pref_current_user), userId);
		editor.commit();
	}
}
