package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.fragments.AuthenticateFragment;
import uk.ac.qub.mindyourmind.fragments.PersonalDetailsFragment;
import uk.ac.qub.mindyourmind.interfaces.OnAuthenticated;
import uk.ac.qub.mindyourmind.interfaces.OnPersonalDetailsFinished;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class SignUpActivity extends ActionBarActivity implements OnAuthenticated, OnPersonalDetailsFinished{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		if(savedInstanceState==null){
			Fragment fragment = AuthenticateFragment.newInstance();	
			getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
		}
	}

	
	@Override
	public void success(long userId, String university, String degreePathway, String universityEmail, int yearOfStudy) {
		// go add add details fragment
		Fragment fragment = PersonalDetailsFragment.newInstance(userId, university, degreePathway, universityEmail, yearOfStudy);
		getFragmentManager().beginTransaction().replace(R.id.container, fragment, PersonalDetailsFragment.DEFAULT_FRAGMNET_TAG).commit();	
	}
	
	@Override
	public void goToMainMenu() {
		// open the main menu
		startActivity(new Intent(this, MainMenuActivityTemp.class));
	}

	
}
