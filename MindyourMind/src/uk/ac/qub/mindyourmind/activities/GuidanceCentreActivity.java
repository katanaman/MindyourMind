package uk.ac.qub.mindyourmind.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import uk.ac.qub.mindyourmind.R;
import uk.ac.qub.mindyourmind.fragments.GuidanceCentreFragment;

/**
 * guidance center activity, allowing you to easily add more guidance center fragments to the swipe view
 * @author Adrian
 */
public class GuidanceCentreActivity extends FragmentActivity {

	//declaring viewpager and fragment state page Adapter
	private ViewPager viewpager;
	private static FragmentStatePageAdapter fspa = null;

	private int screenWidth;
	private int screenHeight;

	/**
	 * method that will allow fragments to be added in a dynamic fashion when loaded from a db 
	 * @return
	 */
	public static Fragment[] getGuidanceCenterFragments(){


		Fragment[] GuidanceCenterFragments = { 

				GuidanceCentreFragment.newInstance("Get Chilled", R.raw.get_chilled, R.string.guidance_center_get_chilled),
				GuidanceCentreFragment.newInstance("Get Involved", R.raw.get_involved, R.string.guidance_center_get_involved),
				GuidanceCentreFragment.newInstance("Get Wise", R.raw.get_wise, R.string.guidance_center_get_wise),
				GuidanceCentreFragment.newInstance("Get Help", R.raw.get_help, R.string.guidance_center_get_help),
				GuidanceCentreFragment.newInstance("Get Talking", R.raw.get_talking, R.string.guidance_center_get_talking),
				GuidanceCentreFragment.newInstance("Get Active", R.raw.get_active, R.string.guidance_center_get_active),
				GuidanceCentreFragment.newInstance("Accept Yourself", R.raw.accept_yourself, R.string.guidance_center_accept_yourself),
				GuidanceCentreFragment.newInstance("Eat Healthy ", R.raw.eat_healthy, R.string.guidance_center_eat_healthily)
		};
		return GuidanceCenterFragments;
	}




	/**
	 * onCreate method setting up the vewpager and fragment state page adapter
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_view_mount);


		//setting up swipe views
		viewpager = (ViewPager) findViewById(R.id.pager);

		fspa = new FragmentStatePageAdapter(this.getSupportFragmentManager(), getGuidanceCenterFragments());

		viewpager.setAdapter(fspa);

		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * inflating the options menu for this activity
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sign_up, menu);
		return true;
	}

	/**
	 * method handling options menu click events
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent (this, PreferencesActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * getter for Screen width
	 * @return int screenwidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	/**
	 * setter for Screen width
	 * @param screenWidth
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	/**
	 * getter for Screen height
	 * @return int screenheight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
	/**
	 * setter for screen height
	 * @param screenHeight
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}


	////// inner class /////////////////////

	public class FragmentStatePageAdapter extends FragmentStatePagerAdapter {

		Fragment[] fragments;

		public FragmentStatePageAdapter(FragmentManager fm) {
			super(fm);
			this.fragments=null;
		}

		public FragmentStatePageAdapter(FragmentManager fm, Fragment[] fragments) {
			super(fm);
			this.fragments=fragments;
		}

		@Override
		public Fragment getItem(int arg0) {

			return  fragments[arg0];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.length;
		}

	}
}
