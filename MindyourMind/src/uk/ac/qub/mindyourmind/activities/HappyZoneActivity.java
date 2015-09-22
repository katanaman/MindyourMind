package uk.ac.qub.mindyourmind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * happy zone activity that will be further implemented into an imported photo gallery in future develoment cycles  
 * @author Adrian
 */
public class HappyZoneActivity extends ActionBarActivity{

	/**
	 * setting content view and firing an intent to the devices media gallery
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		 //startActivityForResult(i, RESULT_LOAD_IMAGE);
		startActivity(i);
	}
}
