package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnIntroFinished;

public class VideoPlayerfragment extends Fragment{

	  boolean hasPlayed;
	  	Thread timer;
	    VideoView videoView;
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
			View v =  inflater.inflate(R.layout.fragment_video_player, container, false);


			videoView = (VideoView) v.findViewById(R.id.videoView);
/*
	        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.mym_intro;
	        videoView.setVideoURI(Uri.parse(path));
	        if(savedInstanceState==null) {
	        	videoView.start();
	        };
	        */
	        
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
	        
	        return v;
		}
}
