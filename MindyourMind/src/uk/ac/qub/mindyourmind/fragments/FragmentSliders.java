package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;

public class FragmentSliders extends Fragment {
	
	RelativeLayout mRelativeLayout;
	ImageView face, face2;
	RatingBar rate;
	SeekBar seek;
	TextView message, message2;
	Button done;
	//ViewPager viewPager;
	
	public static String DEFAULT_FRAGMNET_TAG = "fragmentSliders";
	
	public static FragmentSliders newInstance(){
		return new FragmentSliders();
	}
	
	private int rating;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.fragment5, container, false);
		
		//initialiseVariables();
		
		face = (ImageView) mRelativeLayout.findViewById(R.id.imageView1);
		face2 = (ImageView) mRelativeLayout.findViewById(R.id.imageView2);
		rate = (RatingBar) mRelativeLayout.findViewById(R.id.ratingBar1);
		seek = (SeekBar) mRelativeLayout.findViewById(R.id.seekBar1);
		done = (Button) mRelativeLayout.findViewById(R.id.done);
		//viewPager=(ViewPager) getActivity().findViewById(R.id.pager);
		
		setRating(1);
		
		
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				((OnEditFinished) getActivity()).finishEditingTask();
			}
		});
		rate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				setRating((int)rating+5);
				chooseImg();
			}
		});
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress<=20){
					setRating(1);
				} else if (progress<=40 && progress>20){
					setRating(2);
				} else if (progress<=60 && progress>40){
					setRating(3);
				} else if (progress<=80 && progress>60){
					setRating(4);
				} else if (progress<100 && progress>80){
					setRating(5);
				} else {
					setRating(3);
				}
				chooseImg();
			}
		});
		
		return mRelativeLayout;
	}
	
	private void displayMessage(String message){
		Toast.makeText(getActivity(),
				"I'm feeling " + message, Toast.LENGTH_SHORT).show();
	}
	
	private void chooseImg(){
		
		switch(rating){
		case 1: face.setImageResource(R.drawable.vunhappy);
			break;
		case 2: face.setImageResource(R.drawable.sad);
			break;
		case 3: face.setImageResource(R.drawable.neutral);
			break;
		case 4: face.setImageResource(R.drawable.happyface);
			break;
		case 5: face.setImageResource(R.drawable.vhappy);
			break;
		case 6: face2.setImageResource(R.drawable.angry);
			break;
		case 7: face2.setImageResource(R.drawable.upset);
			break;
		case 8: face2.setImageResource(R.drawable.confused);
			break;
		case 9: face2.setImageResource(R.drawable.meh);
			break;
		case 10: face2.setImageResource(R.drawable.happy);
			break;
		default:
		}
	}

}
