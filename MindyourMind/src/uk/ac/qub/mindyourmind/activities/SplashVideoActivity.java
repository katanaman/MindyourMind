package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import uk.ac.qub.mindyourmind.R;

public class SplashVideoActivity extends Activity {

	public static final String TAG = "videoPlayerFragment";
	
  	boolean playIntro;
    VideoView videoView;
    TextView skipButton;
    MediaController mediaController;
    Intent loginIntent;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.d(TAG, "onCreate called");
    	
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		playIntro = prefs.getBoolean(getResources().getString(R.string.pref_play_intro_key), true);
    	
		if(!playIntro){
			goToLogin();
		}
		
    	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	setContentView(R.layout.activity_splash_video);
    	
    	try{
		videoView = (VideoView) findViewById(R.id.videoView);
		skipButton = (TextView) findViewById(R.id.textViewMask);
		skipButton.bringToFront();
        String path = "android.resource://" + getPackageName() + "/" + R.raw.mym_4_intro;
        videoView.setVideoURI(Uri.parse(path));
    	} catch (Exception e){
    		Log.d(TAG, "video view errored");
    		goToLogin();
    	}
    	
      mediaController = new MediaController(this);
      mediaController.setVisibility(View.GONE);
      videoView.setMediaController(mediaController);
    	
    	skipButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "skip button pressed");
				showSkipDialog();
				
			}
		});
    	videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				goToLogin();
			}
		});
    	videoView.start();	
    }
    
    public void showSkipDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to skip");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	goToLogin();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        
    }
    
    public void goToLogin(){
    	//check to avoid opening multipul instances of the loginPage
    	loginIntent = new Intent(this, LoginSignUpActivity.class);
    	startActivity(loginIntent);
    	finish();
    }
}
