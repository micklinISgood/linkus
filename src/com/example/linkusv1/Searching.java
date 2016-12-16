package com.example.linkusv1;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import gov.nasa.jpf.annotation.Checkpoint;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import gov.nasa.jpf.annotation.Checkpoint;

import com.skyhookwireless.wps.WPSAuthentication;
import com.skyhookwireless.wps.WPSContinuation;
import com.skyhookwireless.wps.WPSLocation;
import com.skyhookwireless.wps.WPSPeriodicLocationCallback;
import com.skyhookwireless.wps.WPSReturnCode;
import com.skyhookwireless.wps.WPSStreetAddressLookup;
import com.skyhookwireless.wps.XPS;

public class Searching extends Activity {
	
	private Context mContext;
	
	private BroadcastReceiver _refreshReceiver = new BroadcastReceiver(){
	  
	  @Override public void onReceive(Context context, Intent intent) {
	 // stopService(new Intent(mContext,PhotoService.class));
	  //startActivity(new Intent(mContext, near0.class));
		  finish();
	  //Log.e("receiver", "closed by broadcast");
	
	  } };
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getApplicationContext();
		setContentView(R.layout.searching);
		mContext = getApplicationContext();
		
		
		
		
		
		//Log.e("check stat", "shearching");
		 IntentFilter filter = new IntentFilter("photo");
		 this.registerReceiver(_refreshReceiver, filter);
		

	}
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	@Checkpoint("Service start")
	public void onResume() {
		super.onResume();
		
		//Log.e("check stat", "searching5");
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			 setContentView(R.layout.searching);
			 SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
	      if(match.contains("0")||match.contains("1")||match.contains("2")||match.contains("3")||match.contains("4")||match.contains("5")
	    		  ||match.contains("6")||match.contains("7")||match.contains("8")){
	    	  Intent intent = new Intent(mContext, Gift.class);
	    	  intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
	    	   startActivity(intent);
				finish();
	    	  
	    	  
		   	}else{
		   		
		   		ImageView imageButton = (ImageView) findViewById(R.id.search);
				imageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Log.e("click", "time");
					setContentView(R.layout.splash);
					sendBroadcast(new Intent("searching"));
					startService(new Intent(mContext, PhotoService.class));
					
					
				}
				});

		   		
			   	}
		}
	else {
			setContentView(R.layout.wifi);
			ImageView imageButton = (ImageView) findViewById(R.id.wifi);
			Toast.makeText(mContext,
					"WiFi____WiFi", Toast.LENGTH_LONG).show();
			imageButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.splash);
				startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
			});
		}


	}
	 
	 @Override
	    public void onPause() {
	        super.onPause();
	      
	      
	      
	    }
	
	
	    
	@Override
	public void onDestroy() {
		super.onDestroy();
		 this.unregisterReceiver(this._refreshReceiver);

			}
	
	
	
	
}
