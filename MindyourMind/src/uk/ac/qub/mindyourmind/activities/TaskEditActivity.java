package uk.ac.qub.mindyourmind.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.fragments.FragmentSliders;
import uk.ac.qub.mindyourmind.fragments.TaskEditFragment;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class TaskEditActivity extends ActionBarActivity implements OnEditFinished {
	
	public static final String EXTRA_TASKID ="taskId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_edit);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar)); 
		
		long id = getIntent().getLongExtra(TaskEditActivity.EXTRA_TASKID, 0L);
		
		Fragment fragment = TaskEditFragment.newInstance(id);
		
		String fragmentTag = TaskEditFragment.DEFAULT_FRAGMNET_TAG;
		
		if(savedInstanceState == null){
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
		}
	}

	@Override
	public void finishEditingTask() {
		// when the user dismisses the editor finish the task
		finish();
	}

	@Override
	public void finishEditingRating() {
		Fragment fragment = FragmentSliders.newInstance();
		String fragmentTag = FragmentSliders.DEFAULT_FRAGMNET_TAG;
		getFragmentManager().beginTransaction().replace(R.id.container, fragment, fragmentTag).commit();
		
	}
}
