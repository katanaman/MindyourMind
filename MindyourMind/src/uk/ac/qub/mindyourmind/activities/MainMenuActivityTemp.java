package uk.ac.qub.mindyourmind.activities;

import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import uk.ac.qub.mindyourmind.adapters.GridViewImageAdapter;
import uk.ac.qub.mindyourmind.interfaces.OnGotoLogin;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import uk.ac.qub.mindyourmind.R;

@SuppressWarnings("deprecation")
public class MainMenuActivityTemp extends ActionBarActivity {

	private static final String DEFAULT_TAG = "mainMenuActivityTemp";
	private GridView grdImages;
	// private ImageButton createEntry;
	
	private MenuComponent[] menu;
	private OnClickListener onClick;

	private void addMenuItems(){
		MenuComponent[] menuItems = {
				new MenuComponent("diary", "diary_icon","TaskListActivity"),
				new MenuComponent("calendar", "calendar_icon","CalendarViewActivity"),
				new MenuComponent("happyZone", "vhappy","HappyZoneActivity"),
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
		setContentView(R.layout.activity_main_menu_temp);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		grdImages= (GridView) findViewById(R.id.grdImages);
		//createEntry= (ImageButton) findViewById(R.id.btnSelect);
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
		if (id == R.id.action_settings) {
			startActivity(new Intent (this, PreferencesActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void goToLogin() {
		Intent intent = new Intent(this, LoginSignUpActivity.class);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
		
	}
}
