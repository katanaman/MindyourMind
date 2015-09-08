package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import uk.ac.qub.mindyourmind.fragments.LoginSignUpFragment;
import uk.ac.qub.mindyourmind.fragments.QuickSignInFragment;
import uk.ac.qub.mindyourmind.interfaces.OnLogin;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.database.UserTable;
@SuppressWarnings("deprecation")
public class LoginSignUpActivity extends ActionBarActivity implements OnLoginClicked, OnSignUpClicked, OnLogin {
	
	//static identifier for shared preferences
	public static final String EXTRA_USERID ="userId";
	
	/**
	 * on create method using the checking if the user has logged in before
	 * if so quick login fragment is used (using the passcode)
	 * else a standard login page is displayed
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_and_sign_up);
		
		//get shared preferences manager
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	
		
		Fragment fragment;
		
		//get last logged in userID from shared preferences
		long userID = prefs.getLong(UserTable.COLUMN_USER_ID, -1);
		
		//check if it's not null
		if(userID != -1){
			fragment = QuickSignInFragment.newInstance(userID);
		} else {
			fragment = LoginSignUpFragment.newInstance();
		}
		
		getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
	}

	@Override
	public void openMainMenuFragment(View v) {
		//startActivity(new Intent(this, MainMenuActivity.class));
		startActivity(new Intent(this, MainMenuActivityTemp.class));
	}

	@Override
	public void openSignUpActivity(View v) {
		//when the user wants to start a create an account open the sign up activity
		startActivity(new Intent(this, SignUpActivity.class));
	}

	@Override
	public void goToLogin() {
		getFragmentManager().beginTransaction().replace(R.id.container, LoginSignUpFragment.newInstance()).commit();
	}
	
	
	
	
	
}
