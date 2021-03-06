package uk.ac.qub.mindyourmind.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import uk.ac.qub.mindyourmind.adapters.GridViewImageAdapter;
import android.widget.GridView;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class MainMenuActivity extends ActionBarActivity {

	private static final String DEFAULT_TAG = "mainMenuActivityTemp";
	private GridView grdImages;
	// private ImageButton createEntry;
	
	private MenuComponent[] menu;

	private void addMenuItems(){
		MenuComponent[] menuItems = {
				new MenuComponent("diary", "diary_icon","DiaryListActivity"),
				new MenuComponent("calendar", "calendar_icon","CalendarViewActivity"),
				new MenuComponent("sliderZone", "vhappy","SlidersActivity"),
				new MenuComponent("guidanceCentre", "tips","GuidanceCentreActivity"),
				new MenuComponent("meditationZone", "meditation_icon","MeditationZoneActivity"),
				new MenuComponent("statistics", "graphs","GraphViewActivity")
		};
		menu = menuItems;
	}

	  /**
	   * when refactoring this code take out all the non android java code and link it through interfaces
	   */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		grdImages= (GridView) findViewById(R.id.grdImages);
        addMenuItems();
        grdImages.setAdapter(new GridViewImageAdapter(this, menu));

        grdImages.setOnItemClickListener(new OnItemClickListener() {
        	
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	
            	String className = (getPackageName()+"."+"activities."+menu[position].link);
				try{
					Intent intent = new Intent(v.getContext() , Class.forName( className ));
					startActivity(intent);
				}catch(ClassNotFoundException e){
					Toast.makeText(v.getContext(), "No such activity found", Toast.LENGTH_SHORT).show();
				}	
            }
        });
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
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

	   Log.d(DEFAULT_TAG, "onBackPressed Called");
	   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		
		case R.id.action_settings :
			startActivity(new Intent (this, PreferencesActivity.class));
			return true;
		case R.id.personal_details :
			startActivity(new Intent (this, PersonalDetailsActivity.class));
			return true;
		default :
			return super.onOptionsItemSelected(item);
		}
	}

	
	public void goToLogin() {
		Intent intent = new Intent(this, LoginSignUpActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
    	startActivity(intent);
    	finish();		
	}
}
