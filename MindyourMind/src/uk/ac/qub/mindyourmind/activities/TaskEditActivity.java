package uk.ac.qub.mindyourmind.activities;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.fragments.SlidersFragment;
import uk.ac.qub.mindyourmind.fragments.TaskEditFragment;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class TaskEditActivity extends ActionBarActivity implements OnEditFinished {
	
	public static final String EXTRA_DIARYID ="diaryId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_edit);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar)); 
		
		long id = getIntent().getLongExtra(TaskEditActivity.EXTRA_DIARYID, 0L);
		
		Fragment fragment = TaskEditFragment.newInstance(id);
		
		String fragmentTag = TaskEditFragment.DEFAULT_FRAGMNET_TAG;
		
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}

	@Override
	public void finishEditingEntry() {
		// when the user dismisses the editor finish the entry
		finish();
	}

	@Override
	public void finishEditingRating() {
		startActivity(new Intent(this, SlidersActivity.class));
		finish();
	}
}
