package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import uk.ac.qub.mindyourmind.interfaces.OnSignUpClicked;
import uk.ac.qub.mindyourmind.R;

public class VideoPlayerActivity extends Activity implements OnSignUpClicked {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		
		/* String path = "android.resource://" + getPackageName() + "/" + R.raw.mym_intro;
	        
		    Uri data = Uri.parse(path);
		    intent.setDataAndType(data, "video/wmv");
		    startActivity(intent);
	*/
	}

	@Override
	public void openSignUpActivity(View v) {
		
		startActivity(new Intent(this, LoginSignUpActivity.class));
		
	}
}
