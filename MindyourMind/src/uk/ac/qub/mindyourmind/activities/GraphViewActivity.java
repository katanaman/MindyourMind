package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.GraphViewFragment;

/**
 * activity used for mounting graph view fragment
 * @author Adrian
 */
@SuppressWarnings("deprecation")
public class GraphViewActivity extends ActionBarActivity {

	/**
	 * on Create setting content view and action bar
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		Fragment fragment = GraphViewFragment.newInstance();
		
		String fragmentTag = GraphViewFragment.DEFAULT_FRAGMENT_TAG;
		
		//checking on saved instance state to prevent adding the fragment more than once
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}

}
