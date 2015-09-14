package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.MediaController.MediaPlayerControl;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnVideoFinished;

public class VideoPlayerFragment extends Fragment implements
OnCompletionListener, OnPreparedListener {

		public static final String TAG = "videoPlayerFragment";
	  	boolean hasPlayed;
	    VideoView videoView;
	    MediaController mediaController;
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
			View v =  inflater.inflate(R.layout.fragment_video_player, container, false);
			videoView = (VideoView) v.findViewById(R.id.videoView);
	        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.mym_4_intro;
	        videoView.setVideoURI(Uri.parse(path));
	        
	        mediaController = new MediaController(getActivity());
	    	mediaController.setAnchorView(videoView);
	    	videoView.setMediaController(mediaController);
	    		
	    	videoView.start();
	    	
	    	videoView.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					
					((OnVideoFinished) getActivity()).videoFinshed();
				}
			});
	    	
       /*
 			timer = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(186000);
					} catch (InterruptedException e) {
						((OnIntroFinished)getActivity()).openLoginSignUp();
					}
					((OnIntroFinished)getActivity()).openLoginSignUp();
				}
			});
		
	    		
	        timer.start();
	        
	        videoView.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					timer.interrupt();
					return true;
				}
			});
			*/
	    	
	        return v;
	    }

		@Override
		public void onPrepared(MediaPlayer mp) {
		
		}

		@Override
		public void onCompletion(MediaPlayer mp) {
			Log.d(TAG, "onCompleteCalled");
			((OnVideoFinished) getActivity()).videoFinshed();
		}
}