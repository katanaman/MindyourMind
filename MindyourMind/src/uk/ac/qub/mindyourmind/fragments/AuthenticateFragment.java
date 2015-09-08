package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.adapters.DegreePathwaySpinnerAdapter;
import uk.ac.qub.mindyourmind.adapters.UniversitiesSpinnerAdapter;
import uk.ac.qub.mindyourmind.interfaces.OnAuthenticated;

public class AuthenticateFragment extends Fragment implements OnItemSelectedListener{
	
	public static final String DEFAULT_FRAGMNET_TAG = "authenticateFragment";
	

	private static final int MENU_SAVE = 1;
			
	Spinner SUniversity, SDegreePathway;
	EditText ETYearOfStudy, ETUniversityEmail;
	String university, degreePathway, universityEmail;
	int yearOfStudy;
	
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
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(UNIVERSITY_NAME, SUniversity.getSelectedItemPosition());
		outState.putInt(DEGREE_PATHWAY, SDegreePathway.getSelectedItemPosition());
		outState.putString(UNIVERSITY_EMAIL, ETUniversityEmail.getText().toString());
		outState.putString(YEAR_OF_STUDY, ETYearOfStudy.getText().toString());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_authenticate_accout, container, false);
		
		SUniversity = (Spinner) v.findViewById(R.id.spinnerUniversity);
		SDegreePathway = (Spinner) v.findViewById(R.id.spinnerDegreePathway);
		ETYearOfStudy = (EditText) v.findViewById(R.id.editTextYearOfStudy);
		ETUniversityEmail = (EditText) v.findViewById(R.id.editUniversityEmail);
		
		SUniversity.setAdapter(new UniversitiesSpinnerAdapter(getActivity()));
		SDegreePathway.setAdapter(new DegreePathwaySpinnerAdapter(getActivity()));
		
		if(savedInstanceState != null){
			SUniversity.setSelection(savedInstanceState.getInt(UNIVERSITY_NAME));
			SUniversity.setSelection(savedInstanceState.getInt(DEGREE_PATHWAY));
			ETUniversityEmail.setText(savedInstanceState.getString(UNIVERSITY_EMAIL));
			ETYearOfStudy.setText(savedInstanceState.getString(YEAR_OF_STUDY));
		}
		
		return v;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch(view.getId()){
		case R.id.spinnerUniversity:
			university = (String) SUniversity.getItemAtPosition(position);
			break;
		case R.id.spinnerDegreePathway:
			degreePathway = (String) SDegreePathway.getItemAtPosition(position);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// do nothing
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
			//save();
			//checkRegistrationSuccessful();
			Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
			((OnAuthenticated) getActivity()).success();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void checkRegistrationSuccessful(){
		
	}
	
}
