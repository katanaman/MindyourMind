package uk.ac.qub.mindyourmind.fragments;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;

public class GuidanceCentreFragment extends Fragment{

	private String title;
	private int imageResourceid;
	private int textResourceid;
	
	
	LinearLayout mLinearLayout;
	
	ImageView imageView;
	TextView textView;
	Bitmap image;
	
	public static final String EXTRA_TITLE = "guidanceCenterTitle";
	public static final String EXTRA_IMAGE_ID = "guidanceCenterImageId";
	public static final String EXTRA_MESSAGE = "guidanceCenterMessage";
	
	public GuidanceCentreFragment(){
		//Necessary empty constructor
	}
	
public static GuidanceCentreFragment newInstance(String title, int imageId, int message)
	
	{
		GuidanceCentreFragment f = new GuidanceCentreFragment();
	    Bundle bdl = new Bundle(3);
	    bdl.putString(EXTRA_TITLE, title);
	    bdl.putInt(EXTRA_IMAGE_ID, imageId);
	    bdl.putInt(EXTRA_MESSAGE, message);
	    f.setArguments(bdl);
	    return f;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//inflate the layout file for the guidance center
		 mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_guidance_centre, container, false);
					
				title = getArguments().getString(GuidanceCentreFragment.EXTRA_TITLE);
				textResourceid = getArguments().getInt(GuidanceCentreFragment.EXTRA_MESSAGE);
				imageResourceid = getArguments().getInt(GuidanceCentreFragment.EXTRA_IMAGE_ID);
				
		// loadBitmap(imageResourceid, imageView);
		 
		 return mLinearLayout;
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		

		// Instantiate the two views associated with this layout
				imageView = (ImageView) getView().findViewById(R.id.calmingImages);
				textView = (TextView) getView().findViewById(R.id.message);
				
		//loadBitmap(imageResourceid, imageView);
			imageView.setImageResource(imageResourceid);
		 try {
			 textView.setText(getActivity().getResources().getText(textResourceid));
		 } catch (Exception ex){
			 Toast.makeText(getActivity().getApplicationContext(), "no text found by that name", Toast.LENGTH_SHORT).show();
		 }
		 
	}
}	