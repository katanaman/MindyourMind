package uk.ac.qub.mindyourmind.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import uk.ac.qub.mindyourmind.interfaces.OnEditTask;
import uk.ac.qub.mindyourmind.interfaces.OnPlayVideo;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class MeditationZoneActivity extends ActionBarActivity implements OnPlayVideo {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meditation_zone);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
	}

	@Override
	public void playVideo(String id){
		try{
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
			startActivity(intent);                 
		}catch (ActivityNotFoundException ex){
			Intent intent=new Intent(Intent.ACTION_VIEW, 
					Uri.parse("http://www.youtube.com/watch?v="+id));
			startActivity(intent);
		}
	}
}

