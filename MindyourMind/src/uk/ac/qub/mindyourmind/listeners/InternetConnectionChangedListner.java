package uk.ac.qub.mindyourmind.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Listener for Internet connectivity setting a static boolean to value
 * true for connection available 
 * false if not
 * @author Adrian
 */
public class InternetConnectionChangedListner extends BroadcastReceiver	{

	public static boolean isConnected = false; //static boolean to check for connection

	/**
	 * onRecieve method that is called when connectivity changes
	 */
	
	@Override
	public void onReceive( Context context, Intent intent ) {
		checkConnection(context);
	}

	@SuppressWarnings("deprecation")
	public static void checkConnection(Context context){
		//initially set isConnected to false
		isConnected = false;

		//get the connectivity manager
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		//if there is an active network set isConnected to true, and let user know through Toast message
		if ( activeNetInfo != null ){
			isConnected = true;
			Toast.makeText( context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
		}
		if( mobNetInfo != null ){
			isConnected = true;
			Toast.makeText( context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
		}
	}
}
