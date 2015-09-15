package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;

/*
import java.net.URL;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.Toast;
import android.widget.VideoView;
import uk.ac.qub.mindyourmind.R;
*/

public class mediaPlayerActivity extends Activity {
	
}

/*
public class mediaPlayerActivity extends Activity implements
OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaPlayerControl { 

	private String path;
	private MediaPlayer mMediaPlayer;
	private MediaController mcontroller;
	private VideoView videoView;
	
	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		
		videoView = (VideoView) findViewById(R.id.videoView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		mcontroller.show();
		return false;
	}
	
	private void playVideo() {
		doCleanUp();
		
		try {
			path = getIntent().getStringExtra("url");
			if (path == "") {
				Toast.makeText(this, "URL Not found",
						Toast.LENGTH_LONG).show();
			}
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepare();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setScreenOnWhilePlaying(true);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mcontroller = new MediaController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
		mcontroller.setMediaPlayer(this);
		mcontroller.setAnchorView(findViewById(R.id.mediaplayer_surfaceview_container));
		handler.post(new Runnable() {

			public void run() {
				mcontroller.setEnabled(true);
				mcontroller.show();
			}
		});
	}

	//mediacontroller implemented methods

	public void start() {
		mMediaPlayer.start();
	}

	public void pause() {
		mMediaPlayer.pause();
	}

	public int getDuration() {
		return mMediaPlayer.getDuration();
	}

	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	public void seekTo(int i) {
		mMediaPlayer.seekTo(i);
	}

	public boolean isPlaying() {
		return mMediaPlayer.isPlaying(); 
	}

	public int getBufferPercentage() {
		return 0;
	}

	public boolean canPause() {
		return true;
	}

	public boolean canSeekBackward() {
		return true;
	}

	public boolean canSeekForward() {
		return true;
	}
}
*/