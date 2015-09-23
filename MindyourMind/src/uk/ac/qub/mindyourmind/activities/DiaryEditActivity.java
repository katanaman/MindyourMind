package uk.ac.qub.mindyourmind.activities;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.fragments.DiaryEditFragment;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
import uk.ac.qub.mindyourmind.R;
/**
 * An activity to mount the DiaryEditfragment, implementing the onEditFinished interface 
 * @author Adrian
 */
@SuppressWarnings("deprecation")
public class DiaryEditActivity extends ActionBarActivity implements OnEditFinished {
	
	public static final String EXTRA_DIARYID ="diaryId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_edit);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar)); //setting a toolbar as the actionbar
		
		//if creating a new entry id will be 0, else it will be the id of the entry to be edited
		long id = getIntent().getLongExtra(DiaryEditActivity.EXTRA_DIARYID, 0L);
		
		Fragment fragment = DiaryEditFragment.newInstance(id);//creating a new diaryedit fragment
		
		String fragmentTag = DiaryEditFragment.DEFAULT_FRAGMNET_TAG;//setting a fragment tag
		
		if(savedInstanceState == null){ //checking to prevent adding the fragment twice if onCreate is called multiple times
			getFragmentManager().beginTransaction().add(R.id.container, fragment, fragmentTag).commit();//fragment manager adding the fragment to the layout
		}
	}

	/**
	 * method to end this activity and fall back to the previous one on the stack, called when 
	 * the user has finished editing an entry
	 */
	@Override
	public void finishEditingEntry() {
		// when the user dismisses the editor finish the entry
		finish();
	}

	/**
	 * method opening the sliders activity, where users can rate their entries after adding a
	 * new entry
	 */
	@Override
	public void finishEditingRating() {
		startActivity(new Intent(this, SlidersActivity.class));
		finish();
	}
}
