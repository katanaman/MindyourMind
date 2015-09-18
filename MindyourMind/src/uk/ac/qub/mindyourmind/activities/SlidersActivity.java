package uk.ac.qub.mindyourmind.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.SlidersFragment;
import uk.ac.qub.mindyourmind.interfaces.OnRatingsFinsihed;

public class SlidersActivity extends ActionBarActivity implements OnRatingsFinsihed {

	public static final String EXTRA_TASKID ="taskId";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliders);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		
		Fragment fragment = SlidersFragment.newInstance();
		
		String fragmentTag = SlidersFragment.DEFAULT_FRAGMNET_TAG;
	
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}
	
	@Override
	public void finishedRatings() {
		// when the user dismisses the editor finish the task
		finish();
	}
}
