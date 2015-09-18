package uk.ac.qub.mindyourmind.fragments;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import uk.ac.qub.mindyourmind.database.DiaryEntryTable;
import uk.ac.qub.mindyourmind.database.RatingsTable;
import uk.ac.qub.mindyourmind.interfaces.OnEditFinished;
import uk.ac.qub.mindyourmind.interfaces.OnRatingsFinsihed;
import uk.ac.qub.mindyourmind.models.RatingsModel;
import uk.ac.qub.mindyourmind.models.RatingsModel.RatingTypes;
import uk.ac.qub.mindyourmind.providers.MindYourMindProvider;

public class SlidersFragment extends Fragment implements OnSeekBarChangeListener {
	
	public static String DEFAULT_FRAGMNET_TAG = "fragmentSliders";
	
	private static final int MENU_SAVE = 1;
	RelativeLayout mRelativeLayout;
	ImageView face1, face2;
	SeekBar seek1, seek2;
	TextView message, message2;
	SharedPreferences prefs;
	RatingsModel rHappiess, rSatisfaction;
	int imageNumber;
	Long userId;
	
	public static SlidersFragment newInstance(){
		return new SlidersFragment();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_sliders, container, false);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		userId =  prefs.getLong(getResources().getString(R.string.pref_current_user), 0L);
		
		rHappiess = new RatingsModel(RatingTypes.HAPPINESS);
		rSatisfaction = new RatingsModel(RatingTypes.SATISFACTION);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		face1 = (ImageView) mRelativeLayout.findViewById(R.id.imageView1);
		face2 = (ImageView) mRelativeLayout.findViewById(R.id.imageView2);
		seek1 = (SeekBar) mRelativeLayout.findViewById(R.id.seekBar1);
		seek2 = (SeekBar) mRelativeLayout.findViewById(R.id.seekBar2);
		seek1.setOnSeekBarChangeListener(this);
		seek2.setOnSeekBarChangeListener(this);
		return mRelativeLayout;
	}
	
	private void chooseImg(RatingTypes type, int rating){
		switch(type){
			case HAPPINESS :
				switch(rating){
					case 1: face1.setImageResource(R.drawable.vunhappy);
						break;
					case 2: face1.setImageResource(R.drawable.sad);
						break;
					case 3: face1.setImageResource(R.drawable.neutral);
						break;
					case 4: face1.setImageResource(R.drawable.happyface);
						break;
					case 5: face1.setImageResource(R.drawable.vhappy);
						break;
					default:
						Log.e(DEFAULT_FRAGMNET_TAG , "rating not recognised: "+ rating);
					}
				break;
			case SATISFACTION :
				switch(rating){
					case 1: face2.setImageResource(R.drawable.angry);
						break;
					case 2: face2.setImageResource(R.drawable.upset);
						break;
					case 3: face2.setImageResource(R.drawable.confused);
						break;
					case 4: face2.setImageResource(R.drawable.meh);
						break;
					case 5: face2.setImageResource(R.drawable.happy);
						break;
					default:
						Log.e(DEFAULT_FRAGMNET_TAG , "rating not recognised: "+ rating);
					}
				break;
				}
		}

	private void save(){
		long timeStamp = System.currentTimeMillis();
    	ContentValues valuesHappiness = new ContentValues();
    	valuesHappiness.put(RatingsTable.COLUMN_RATINGS_TYPE, rHappiess.getRatingType().toString());
    	valuesHappiness.put(RatingsTable.COLUMN_RATINGS_VALUE, rHappiess.getRatingValue());
    	valuesHappiness.put(RatingsTable.COLUMN_RATINGS_TIMESTAMP, timeStamp);
    	valuesHappiness.put(RatingsTable.COLUMN_RATINGS_USER_ID, userId);
    	
    	ContentValues valuesSatisfaction = new ContentValues();
    	valuesSatisfaction.put(RatingsTable.COLUMN_RATINGS_TYPE, rHappiess.getRatingType().toString());
    	valuesSatisfaction.put(RatingsTable.COLUMN_RATINGS_VALUE, rHappiess.getRatingValue());
    	valuesSatisfaction.put(RatingsTable.COLUMN_RATINGS_TIMESTAMP, timeStamp);
    	valuesSatisfaction.put(RatingsTable.COLUMN_RATINGS_USER_ID, userId);
    	
    	getActivity().getContentResolver().insert(MindYourMindProvider.RATINGS_URI, valuesHappiness);
    	getActivity().getContentResolver().insert(MindYourMindProvider.RATINGS_URI, valuesSatisfaction);
    	
    	Toast.makeText(getActivity(), getResources().getString(R.string.entry_saved_message), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		menu.add(0, MENU_SAVE, 0, R.string.confirm).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()){
		
		case MENU_SAVE: //if save button was pressed
			save();
			((OnRatingsFinsihed) getActivity()).finishedRatings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (progress<=20){
			imageNumber = 1;
		} else if (progress<=40 && progress>20){
			imageNumber = 2;
		} else if (progress<=60 && progress>40){
			imageNumber = 3;
		} else if (progress<=80 && progress>60){
			imageNumber = 4;
		} else if (progress<100 && progress>80){
			imageNumber = 5;
		} else {
			imageNumber = 3;
		}
		
		switch(seekBar.getId()){
		 case R.id.seekBar1:
			 chooseImg(RatingTypes.HAPPINESS, imageNumber);
			 rHappiess.setRatingValue(progress);
			 break;
		 case R.id.seekBar2:
			 chooseImg(RatingTypes.SATISFACTION, imageNumber);
			 rSatisfaction.setRatingValue(progress);
			 break;
		 default:
			 Log.e(DEFAULT_FRAGMNET_TAG, "unrecognised Id : "+seekBar.getId());
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// not used
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// not used
	}
}
