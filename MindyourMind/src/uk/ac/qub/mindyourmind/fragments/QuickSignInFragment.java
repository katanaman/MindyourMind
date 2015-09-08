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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.activities.LoginSignUpActivity;
import uk.ac.qub.mindyourmind.database.UserTable;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
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
	
	long userId;
	int correctPasscode;
	
	public static QuickSignInFragment newInstance(long userId) {
		QuickSignInFragment fragment = new QuickSignInFragment();
		Bundle args = new Bundle();
		args.putLong(LoginSignUpActivity.EXTRA_USERID, userId);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userId = savedInstanceState.getLong(LoginSignUpActivity.EXTRA_USERID);
		getLoaderManager().initLoader(0, null, this);
	}
	
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
				editor.remove(UserTable.COLUMN_USER_ID);
				editor.commit();
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
	
	private boolean checkCode(){
		if (passcode.getText().toString().equals(""+correctPasscode)){
			return true;	
		} else {
			return false;
		}
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri Uri = ContentUris.withAppendedId(MindYourMindProvider.USER_URI, userId);
		return new CursorLoader(getActivity(),Uri,null,null,null,null);
		//return new CursorLoader(getActivity(), MindYourMindProvider.USER_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data.getCount() == 0){
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					((OnEditFinished) getActivity()).finishEditingTask();
				}
			});
		}
		
		correctPasscode = (data.getInt(data.getColumnIndexOrThrow(UserTable.COLUMN_USER_CODE)));
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// nothing to reset
		
	}
}
