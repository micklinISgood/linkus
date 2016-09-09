package com.example.linkusv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class reRegister extends Activity {
	private SharedPreferences linkusdata;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	   
	}
	
	public void onResume() {
		super.onResume();
		  ConnectivityManager connMgr = (ConnectivityManager)
					getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isConnected()) {
						linkusdata = PreferenceManager.getDefaultSharedPreferences(this);
						
						new UploadThread(linkusdata.getString("userJson", ""),this).execute();
						finish();
					} else {
						setContentView(R.layout.wifi);
						ImageView imageButton = (ImageView) findViewById(R.id.wifi);
						Toast.makeText(this,
								"WiFi_WiFi-WiFi~", Toast.LENGTH_LONG).show();
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
			//Log.e("destroy", "reRegister");
			
			
		}
}
