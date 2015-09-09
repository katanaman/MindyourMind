package uk.ac.qub.mindyourmind.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;
import uk.ac.qub.mindyourmind.providers.TaskProvider;

public class LoginSignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

	boolean debug = true;
	
	public static final String DEFAULT_FRAGMNET_TAG = "loginSignUpFragment";
	
	Button login;
	TextView signUp;
	private View view;
	EditText email, password;
	Map<String, String> emailToPassword;
	
	public static LoginSignUpFragment newInstance() {
		return new LoginSignUpFragment();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(false);
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
		
		signUp.setFocusable(false);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(checkDetails()||debug){
				((OnLoginClicked) getActivity()).openMainMenuFragment(view);
				}
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
		String enteredEmail = email.getText().toString();
		
		if(emailToPassword.size()<1){
			Toast.makeText(getActivity(), "No regestered users on this device", Toast.LENGTH_SHORT).show();
		} else {
			if(emailToPassword.containsKey(enteredEmail)){
				if (emailToPassword.get(enteredEmail).equals(password.getText().toString())){
					isCorrect= true;
				} else {
					Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), "Email address not recognised on this device", Toast.LENGTH_SHORT).show();
			}
		}
		return isCorrect;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (!debug){
		return new CursorLoader(getActivity(),MindYourMindProvider.USER_URI,null,null,null,null);
		} else{
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(DEFAULT_FRAGMNET_TAG, "Loader finshed");
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
