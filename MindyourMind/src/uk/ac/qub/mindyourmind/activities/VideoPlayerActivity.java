package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.interfaces.OnVideoFinished;
import uk.ac.qub.mindyourmind.R;

public class VideoPlayerActivity extends Activity implements OnVideoFinished {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_video_player);
		
		//Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		
		/* String path = "android.resource://" + getPackageName() + "/" + R.raw.mym_intro;
	        
		    Uri data = Uri.parse(path);
		    intent.setDataAndType(data, "video/wmv");
		    startActivity(intent);
	*/
	}

	@Override
	public void videoFinshed() {
		
		startActivity(new Intent(this, LoginSignUpActivity.class));
	}
	
}
