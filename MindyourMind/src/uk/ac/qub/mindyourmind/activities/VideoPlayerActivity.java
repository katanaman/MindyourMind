package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import uk.ac.qub.mindyourmind.interfaces.OnVideoFinished;
import uk.ac.qub.mindyourmind.R;

public class VideoPlayerActivity extends Activity implements OnVideoFinished {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_video_player);
		
	}

	@Override
	public void videoFinshed() {
		
		startActivity(new Intent(this, LoginSignUpActivity.class));
	}
	
}
