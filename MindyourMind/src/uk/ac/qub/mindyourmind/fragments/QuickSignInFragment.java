package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.activities.LoginSignUpActivity;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
import uk.ac.qub.mindyourmind.interfaces.OnLogin;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

/**
 * quick signIn fragment using the cashed last user and  
 * @author Adrian
 *
 */
public class QuickSignInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {

	public static final String DEFAULT_FRAGMNET_TAG = "quickSignInFragment";
	
	View view;
	TextView userEmail, notMe;
	Button login;
	EditText passcode;
	String userCode;
	String email;
	long userID;
	int correctPasscode;
	static QuickSignInFragment fragment;

	
	/**
	 * get instance method with bundle args containing the last logged in userId
	 * @param userId
	 * @return
	 */
	public static QuickSignInFragment newInstance(long userId) {
		if(fragment==null){
			fragment = new QuickSignInFragment();
		}
		Bundle args = new Bundle();
		args.putLong(LoginSignUpActivity.EXTRA_USERID, userId);
		fragment.setArguments(args);
		return fragment;
	}
	
	/**
	 * onCreate method setting the userId from the arguments passed in
	 * initialising the loader to get the email address of the userId passed in 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle arguments = getArguments();
		if(arguments !=null){
			userID = arguments.getLong(LoginSignUpActivity.EXTRA_USERID, 0L);
			Log.d(DEFAULT_FRAGMNET_TAG, "userId returned = " + userID);
		}
		getLoaderManager().initLoader(0, null, this);
	}
	
	/**
	 * method inflating the view and instantiating the view components  
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view =  inflater.inflate(R.layout.fragment_quick_sign_in, container, false);
		userEmail = (TextView) view.findViewById(R.id.email);
		notMe = (TextView) view.findViewById(R.id.TV_notMe);
		
		login = (Button) view.findViewById(R.id.button_login);
		passcode = (EditText) view.findViewById(R.id.ETPasscode);
		
		notMe.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//get shared preferences manager
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
				SharedPreferences.Editor editor = prefs.edit();
				editor.putLong(getResources().getString(R.string.pref_current_user), 0L);
				editor.commit();
				((OnLogin)getActivity()).goToLogin();
			}
		});
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkCode()){
					((OnLoginClicked) getActivity()).openMainMenuFragment(view);
				}
			}
		});
		
		return view;
	}
	
	@Override
	public void onPause() {
		// clear the passcode box when fragment has lost focus 
		super.onPause();
		passcode.setText("");
	}
	
	private boolean checkCode(){
		if (passcode.getText().toString().equals(""+correctPasscode)){
			return true;	
		} else {
			Toast.makeText(getActivity(), "Incorrect passcode", Toast.LENGTH_SHORT).show();
			passcode.setText("");
			return false;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		Uri Uri = ContentUris.withAppendedId(MindYourMindProvider.USER_URI, userID);
		Log.d(DEFAULT_FRAGMNET_TAG, "onCreateLoader called, id: " + Uri);
		return new CursorLoader(getActivity(),Uri,null,null,null,null);
		
//		return new CursorLoader(getActivity(), MindYourMindProvider.USER_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data.getCount() == 0){
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.e(DEFAULT_FRAGMNET_TAG, "no rows returned");
					getActivity().finish();
				}
			});
			return;
		}
		data.moveToFirst();
		
		correctPasscode = data.getInt(data.getColumnIndexOrThrow(UserTable.COLUMN_USER_CODE));
		email = data.getString(data.getColumnIndexOrThrow(UserTable.COLUMN_USER_EMAIL));
		Log.d(DEFAULT_FRAGMNET_TAG, "returned passcode = " + correctPasscode);
		Log.d(DEFAULT_FRAGMNET_TAG, "returned email = " + email);
		userEmail.setText(email);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to reset
	}
}
