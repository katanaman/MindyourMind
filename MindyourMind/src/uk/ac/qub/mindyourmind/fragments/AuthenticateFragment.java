package uk.ac.qub.mindyourmind.fragments;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.database.UserUniversityTable;
import uk.ac.qub.mindyourmind.interfaces.OnAuthenticated;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

public class AuthenticateFragment extends Fragment {
	
	public static final String DEFAULT_FRAGMNET_TAG = "authenticateFragment";

	long userId;
	
	private static final int MENU_SAVE = 1;
			
	EditText ETYearOfStudy, ETUniversityEmail, SUniversity, SDegreePathway;
	String university, degreePathway, universityEmail;
	int yearOfStudy;
	SpinnerDialogFragment sdf;
	
	ArrayList<String> supportedUniversities;
	ArrayList<String> supportedPathways;
	
	static final String UNIVERSITY_NAME = "university_name";
	static final String DEGREE_PATHWAY = "degree_pathway";
	static final String UNIVERSITY_EMAIL = "university_email";
	static final String YEAR_OF_STUDY = "year_of_study";
	
	
	public static AuthenticateFragment newInstance() {
		return new AuthenticateFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportedUniversities = new ArrayList<String>();
		supportedPathways = new ArrayList<String>();
		populateFacultyList();
		populateUniversityList();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(UNIVERSITY_NAME, SUniversity.getText().toString());
		outState.putString(DEGREE_PATHWAY, SDegreePathway.getText().toString());
		outState.putString(UNIVERSITY_EMAIL, ETUniversityEmail.getText().toString());
		outState.putString(YEAR_OF_STUDY, ETYearOfStudy.getText().toString());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_authenticate_accout, container, false);
		
		SUniversity = (EditText) v.findViewById(R.id.spinnerUniversity);
		SDegreePathway = (EditText) v.findViewById(R.id.spinnerDegreePathway);
		ETYearOfStudy = (EditText) v.findViewById(R.id.editTextYearOfStudy);
		ETUniversityEmail = (EditText) v.findViewById(R.id.editUniversityEmail);
		
		
		if(savedInstanceState != null){
			SUniversity.setText(savedInstanceState.getString(UNIVERSITY_NAME));
			SUniversity.setText(savedInstanceState.getString(DEGREE_PATHWAY));
			ETUniversityEmail.setText(savedInstanceState.getString(UNIVERSITY_EMAIL));
			ETYearOfStudy.setText(savedInstanceState.getString(YEAR_OF_STUDY));
		}
		
		SUniversity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sdf = new SpinnerDialogFragment(getActivity(), supportedUniversities, new SpinnerDialogFragment.DialogListener() {
					
					@Override
					public void ready(int n) {
						SUniversity.setText(supportedUniversities.get(n));
					}
					
					@Override
					public void cancelled() {
						sdf.dismiss();
					}
				});
				sdf.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				sdf.show();
			}
		});
		
		SDegreePathway.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sdf = new SpinnerDialogFragment(getActivity(), supportedPathways, new SpinnerDialogFragment.DialogListener() {
					
					@Override
					public void ready(int n) {
						SDegreePathway.setText(supportedPathways.get(n));
					}
					
					@Override
					public void cancelled() {
						sdf.dismiss();
					}
				});
				sdf.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				sdf.show();
				
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, MENU_SAVE, 0, R.string.confirm).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()){
		
		case MENU_SAVE: //if save button was pressed
			if (checkValidEmail()){
				if(isFormComplete()){
					save();
					Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
					((OnAuthenticated) getActivity()).success(userId, university, degreePathway, universityEmail, yearOfStudy);
				}
			} else {
				Toast.makeText(getActivity(), "Sorry, email address not valid", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void populateUniversityList(){
		supportedUniversities.add("Queens University Belfast");
	}
	public void populateFacultyList(){
		supportedPathways.add("Medicine");
	}
	public boolean checkValidEmail(){
		return true;
	}
	
	
	private boolean isFormComplete(){
		boolean isComplete = true;
		String emailAddress = ETUniversityEmail.getText().toString();
		//int yearOfStudy = Integer.parseInt(ETYearOfStudy.getText().toString());
		String university = SUniversity.getText().toString();
		String degreePathway = SDegreePathway.getText().toString();
		
		if(emailAddress.length() == 0 || emailAddress.contains("@")==false){
			isComplete = false;
			Toast.makeText(getActivity(), "Please enter a university email address", Toast.LENGTH_SHORT).show();
//		} else if (yearOfStudy<=0 || yearOfStudy>10){
//			isComplete = false;
//			Toast.makeText(getActivity(), "Please enter a valid year of study", Toast.LENGTH_SHORT).show();
		} else if (university.equals("")){
			isComplete = false;
			Toast.makeText(getActivity(), "Please select a university", Toast.LENGTH_SHORT).show();
		} else if (degreePathway.equals("")){
			isComplete = false;
			Toast.makeText(getActivity(), "Please select a degree pathway", Toast.LENGTH_SHORT).show();
		}
		return isComplete;
	}

	private void save(){
		
		 
		userId = generateUserID(); 
		// put all required values into a contentValues object
		universityEmail = ETUniversityEmail.getText().toString();
		yearOfStudy = Integer.valueOf(ETYearOfStudy.getText().toString());
		university = SUniversity.getText().toString();
		degreePathway = SDegreePathway.getText().toString();

		
		Toast.makeText(getActivity(), getString(R.string.task_saved_message), Toast.LENGTH_SHORT).show();
		
	}
	
	private long generateUserID(){
		
		DateFormat fd = new SimpleDateFormat("yyyyMMddhhmmss");
		String UId = (fd.format(new Date())) + (Math.abs(new Random().nextInt(999)));
		
		Long uID = Long.parseLong(UId);
		Log.d(DEFAULT_FRAGMNET_TAG, "generated Id : " +UId);
		return uID;
	}
}
