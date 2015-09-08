package uk.ac.qub.mindyourmind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.interfaces.OnEditTask;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class TaskListActivity extends ActionBarActivity implements OnEditTask {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
	}
	
	/**
	 * called when a user wants to edit or insert a task.
	 */
	@Override
	public void editTask(long id){
		//when we are asked to edit a reminder, start the TaskEditActivity with the id of the task to edit
		startActivity(new Intent(this, TaskEditActivity.class).putExtra(TaskEditActivity.EXTRA_TASKID, id));
	}
}
