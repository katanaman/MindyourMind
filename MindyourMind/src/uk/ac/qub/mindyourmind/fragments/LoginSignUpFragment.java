package uk.ac.qub.mindyourmind.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.activities.ResetPasswordActivity;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;


public class LoginSignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

	boolean debug = false;
	
	public static final String DEFAULT_FRAGMNET_TAG = "loginSignUpFragment";
	
	Button login;
	TextView signUp, forgotPassword;
	private View view;
	EditText email, password;
	HashMap<String, String> emailToPassword;
	SharedPreferences prefs;
	ProgressBar loadingSpinner;
	Cursor userDetails;
	int attempts;
	
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
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		emailToPassword = new HashMap<String, String>();
		getLoaderManager().initLoader(0, null, this);
		
		if(savedInstanceState!=null){
		emailToPassword = (HashMap<String, String>) savedInstanceState.getSerializable("local_data");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view =  inflater.inflate(R.layout.fragment_login_or_sign_up, container, false);
		login = (Button) view.findViewById(R.id.button_login);
		signUp = (TextView) view.findViewById(R.id.text_view_sign_up);
		email = (EditText) view.findViewById(R.id.editTextName);
		password = (EditText) view.findViewById(R.id.editTextPassword);
		loadingSpinner = (ProgressBar) view.findViewById(R.id.progressBar);
		forgotPassword = (TextView) view.findViewById(R.id.text_view_forgot_password);
		
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
		
		
		forgotPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String enteredEmail = email.getText().toString();
				if(emailToPassword.size()<1){
					showMessage("No regestered users on this device");
				} else {
					if(emailToPassword.containsKey(enteredEmail)){
						showPasswordReset();
					} else {
						showMessage("Email address not regestered on this device");
					}
				}
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
	
	private void showPasswordReset(){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Password reset?")
		.setMessage("Send reset message to :\n" +email.getText().toString())
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
					resetPassword();
				
			}
		});

		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	public void resetPassword(){
		String emailAddress = email.getText().toString();
		ResetPasswordActivity reset = new ResetPasswordActivity(getActivity(), emailAddress);
		String newPassword = reset.sendPasswordReset();
		emailToPassword.put(emailAddress, newPassword);
		AsyncTask<String, Void, Void> async = new saveInBackground(newPassword, getIDFromEmail(emailAddress));
		async.execute("");
	}
	
	public void saveNewPassword(String newPassword, long userID){
		ContentValues UserValues = new ContentValues();
		UserValues.put(UserTable.COLUMN_USER_PASSWORD, newPassword);
		Uri uri = ContentUris.withAppendedId(MindYourMindProvider.USER_URI, userID);
		int count = getActivity().getContentResolver().update(uri, UserValues, null, null);
		//throw error if more than one password is changed
		if(count != 1){
			throw new IllegalStateException("Unable to update password");
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("local_data", emailToPassword);
	}
	
	
	private void setCurrentUser(){
		Editor editor = prefs.edit();
		editor.putLong(getResources().getString(R.string.pref_current_user), getIDFromEmail(email.getText().toString()));
		editor.commit();
	}
	
	private long getIDFromEmail(String email) throws IllegalArgumentException{
		userDetails.moveToFirst();
		while (!userDetails.isAfterLast()) {
			if(email.equals(userDetails.getString(userDetails.getColumnIndex(UserTable.COLUMN_USER_EMAIL)))){
				return userDetails.getLong(userDetails.getColumnIndex(UserTable.COLUMN_USER_ID));
			} 
		}
		throw new IllegalArgumentException("email address, has no ID");
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(),MindYourMindProvider.USER_URI,null,null,null,null);
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

	class saveInBackground extends AsyncTask<String, Void, Void> {
		String newPassword; 
		long userID;
		public saveInBackground(String newPassword, long userID) {
			this.newPassword=newPassword;
			this.userID=userID;
		}
		@Override
		protected Void doInBackground(String... params) { 
			ContentValues UserValues = new ContentValues();
			UserValues.put(UserTable.COLUMN_USER_PASSWORD, newPassword);
			Uri uri = ContentUris.withAppendedId(MindYourMindProvider.USER_URI, userID);
			int count = getActivity().getContentResolver().update(uri, UserValues, null, null);
			//throw error if more than one password is changed
			if(count != 1){
				Log.e(DEFAULT_FRAGMNET_TAG,"Unable to update password");
			}
			return null;
		}
	} 
}

