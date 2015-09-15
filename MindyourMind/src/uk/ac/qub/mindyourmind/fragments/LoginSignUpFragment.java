package uk.ac.qub.mindyourmind.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;
import uk.ac.qub.mindyourmind.providers.TaskProvider;

public class LoginSignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

	boolean debug = false;
	
	public static final String DEFAULT_FRAGMNET_TAG = "loginSignUpFragment";
	
	Button login;
	TextView signUp;
	private View view;
	EditText email, password;
	Map<String, String> emailToPassword;
	SharedPreferences prefs;
	ProgressBar loadingSpinner;
	Cursor userDetails;
	private static LoginSignUpFragment instance;
	
	public static LoginSignUpFragment newInstance() {
		
		if(instance==null){
			instance = new LoginSignUpFragment();
		}
		return instance;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(false);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		emailToPassword = new HashMap<String, String>();
		getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view =  inflater.inflate(R.layout.fragment_login_or_sign_up, container, false);
		login = (Button) view.findViewById(R.id.button_login);
		signUp = (TextView) view.findViewById(R.id.text_view_sign_up);
		email = (EditText) view.findViewById(R.id.editTextName);
		password = (EditText) view.findViewById(R.id.editTextPassword);
		loadingSpinner = (ProgressBar) view.findViewById(R.id.progressBar);
		
		loadingSpinner.setVisibility(View.GONE);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (debug){
					((OnLoginClicked) getActivity()).openMainMenuFragment(view);
				}
				if(checkDetails()){
					setCurrentUser();
					((OnLoginClicked) getActivity()).openMainMenuFragment(view);
				}
				loadingSpinner.setVisibility(View.GONE);
			}
		});
		
		signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OnSignUpClicked) getActivity()).openSignUpActivity(view);
			}
		});
		return view;
	}
	
	public boolean checkDetails(){
		boolean isCorrect = false;
		loadingSpinner.setVisibility(View.VISIBLE);
		
		String enteredEmail = email.getText().toString();
		
		if(emailToPassword.size()<1){
			showMessage("No regestered users on this device");
		} else {
			if(emailToPassword.containsKey(enteredEmail)){
				if (emailToPassword.get(enteredEmail).equals(password.getText().toString())){
					isCorrect= true;
				} else {
					showMessage("Incorrect Password");
				}
			} else {
				showMessage("Email address not recognised on this device");
			}
		}
		return isCorrect;
	}
	
	private void showMessage(String message){
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(),MindYourMindProvider.USER_URI,null,null,null,null);
	}

	private void setCurrentUser(){
		Editor editor = prefs.edit();
		editor.putLong(getResources().getString(R.string.pref_current_user), getIDFromEmail(email.getText().toString()));
		editor.commit();
	}
	
	private int getIDFromEmail(String email) throws IllegalArgumentException{
		userDetails.moveToFirst();
		while (!userDetails.isAfterLast()) {
			if(email.equals(userDetails.getString(userDetails.getColumnIndex(UserTable.COLUMN_USER_EMAIL)))){
				return userDetails.getInt(userDetails.getColumnIndex(UserTable.COLUMN_USER_ID));
			} 
		}
		throw new IllegalArgumentException("email address, has no ID");
	}
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(DEFAULT_FRAGMNET_TAG, "Loader finshed");
		userDetails = cursor;
		
		if(cursor!=null){
			emailToPassword.clear();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {	
				emailToPassword.put(cursor.getString(cursor.getColumnIndexOrThrow(UserTable.COLUMN_USER_EMAIL)), cursor.getString(cursor.getColumnIndexOrThrow(UserTable.COLUMN_USER_PASSWORD)));
			    cursor.moveToNext();
			}
		}
	} 

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to reset
		
	}
}
