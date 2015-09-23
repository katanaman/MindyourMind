package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import uk.ac.qub.mindyourmind.fragments.LoginSignUpFragment;
import uk.ac.qub.mindyourmind.fragments.QuickSignInFragment;
import uk.ac.qub.mindyourmind.interfaces.OnLogin;
import uk.ac.qub.mindyourmind.interfaces.OnLoginClicked;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.R;
/**
 * base activity that deals with current logged in user, password reset, loggin fragment, and quick login fragment 
 * @author Adrian
 */
@SuppressWarnings("deprecation")
public class LoginSignUpActivity extends ActionBarActivity implements OnLoginClicked, OnSignUpClicked, OnLogin {
	
	public static final String TAG ="loginSignUpActivity"; //debugging tag
	
	//static identifier for shared preferences
	public static final String EXTRA_USERID ="userId"; // constant for userID
	
	/**
	 * on create method using the checking if the user has logged in before
	 * if so quick login fragment is used (using the passcode)
	 * else a standard login page is displayed
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login_and_sign_up);
		
		//get shared preferences manager
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Fragment fragment;
		//get last logged in userID from shared preferences
		long userID = prefs.getLong(getResources().getString(R.string.pref_current_user), 0L);
		Log.d(TAG, "returned user id: " + userID);
		
		//check if userID there is a valid userID
		if(userID != 0){ //if there is a previous user display the quick sign-in fragment
			fragment = QuickSignInFragment.newInstance(userID);
		} else { //if not open the login signup fragment
			fragment = LoginSignUpFragment.newInstance();
		}
		getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
	}

	/**
	 * method to finish this activity and begin the mainmenu activity as a new task
	 */
	@Override
	public void openMainMenu(View v) {
		Intent intent = new Intent(this, MainMenuActivity.class); //create the intent
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//add flags to set the main menu activity as a new task 
		startActivity(intent);
		finish(); 
	}

	/**
	 * method to finish this activity and begin sign up activity
	 */
	@Override
	public void openSignUpActivity(View v) {
		//when the user wants to create an account open the sign up activity
		startActivity(new Intent(this, SignUpActivity.class));
	}

	/**
	 * method to restart this activity when user leaves the quick sign-in
	 */
	@Override
	public void goToLogin() {
		Intent intent = new Intent(this, LoginSignUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
