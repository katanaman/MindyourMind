package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.GraphViewFragment;

@SuppressWarnings("deprecation")
public class GraphViewActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		Fragment fragment = GraphViewFragment.newInstance();
		
		String fragmentTag = GraphViewFragment.DEFAULT_FRAGMENT_TAG;
		
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}

}
