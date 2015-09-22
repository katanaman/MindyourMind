package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.PersonalDetailsFragment;
import uk.ac.qub.mindyourmind.interfaces.OnPersonalDetailsFinished;

@SuppressWarnings("deprecation")
public class PersonalDetailsActivity extends ActionBarActivity implements OnPersonalDetailsFinished {

	long userID;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		userID = prefs.getLong(getString(R.string.pref_current_user), 0L);
		
		if(savedInstanceState==null){
			Fragment fragment = PersonalDetailsFragment.UpdateDetails(userID);	
			getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
		}
	}

	@Override
	public void goToMainMenu() {
		// finishing this activity will take us back to the main menu
		finish();
	}
}
