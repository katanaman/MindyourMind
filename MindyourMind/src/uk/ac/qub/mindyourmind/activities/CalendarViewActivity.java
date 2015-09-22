package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.CalendarViewFragment;

/**
 * Calendar activity to mount the calendar view fragment
 * @author Adrian
 */
@SuppressWarnings("deprecation")
public class CalendarViewActivity extends ActionBarActivity {

	/**
	 * on create method setting the layout xml file
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		//get a new instance of the fragment
		Fragment fragment = CalendarViewFragment.newInstance();
		//set a fragment tag in case it needs refered to later
		String fragmentTag = CalendarViewFragment.DEFAULT_FRAGMNET_TAG;
		//check if saved instance state is null to prevent the fragment being added more than once
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}
}