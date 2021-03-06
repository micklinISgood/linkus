package com.example.linkusv1;

import gov.nasa.jpf.annotation.Checkpoint;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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


public class Gift extends Activity {
	private SharedPreferences linkusdata;
	private Context mContext;
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	private int i;


		
	
	@Checkpoint("GiftOnCreate") 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getApplicationContext();
		setContentView(R.layout.gift);
	    linkusdata = PreferenceManager.getDefaultSharedPreferences(mContext);
 
        if (savedInstanceState != null) {
            pendingPublishReauthorization = 
                savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
        }
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
			sendBroadcast(new Intent("searching"));

			for(i=0;i<9;i++){
				if(match.contains(String.valueOf(i))){
					showMatch(i);}
				}
			match.edit().clear().commit();
			
			
			
		}
        ImageView imageButton = (ImageView) findViewById(R.id.gift);
		imageButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//Log.e("click", "gift");
			Toast.makeText(mContext, "Gift will be placed on your timeline,my lord.", Toast.LENGTH_LONG).show();
			startActivity(new Intent(mContext, Searching.class));
			
		}
		});
		 
		

	}
	@Checkpoint("GiftOnStart")
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	public void onResume() {
		super.onResume();
	
		//Log.e("check stat", "searching5");
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			setContentView(R.layout.gift);
			 ImageView imageButton = (ImageView) findViewById(R.id.gift);
				imageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Log.e("click", "gift");
					Toast.makeText(mContext, "Gift will be placed on your timeline,my lord.", Toast.LENGTH_LONG).show();
					startActivity(new Intent(mContext, Searching.class));
					
				}
				});
				 
			
		} else {
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
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	    
	        //Log.e("check","4");
	    }
	    
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	    	super.onSaveInstanceState(outState);
	    	
	    	// Log.e("check","5");
	    }
	   
	
	    
	@Override
	public void onDestroy() {
		super.onDestroy();
	
		
	}
	private void showMatch(int idKey){
		 SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
	     String id = match.getString(String.valueOf(idKey),"");
	     Toast.makeText(mContext,
					id+" wants to know you more.", Toast.LENGTH_LONG).show();
	}
//	private void publishStory(int idKey) {
//	   
//	  
//	    if (session != null){
//
//	        // Check for publish permissions    
//	        List<String> permissions = session.getPermissions();
//	        if (!isSubsetOf(PERMISSIONS, permissions)) {
//	            pendingPublishReauthorization = true;
//	            Session.NewPermissionsRequest newPermissionsRequest = new Session
//	                    .NewPermissionsRequest(this, PERMISSIONS);
//	        session.requestNewPublishPermissions(newPermissionsRequest);
//	            
//	       }
//	        JSONObject privacy = new JSONObject();
//	        //works!!!
//	        try {
//	        privacy.put("value", "SELF");
//	        } catch (JSONException e) {
//	        Log.e("privacy", "Unknown error while preparing params", e);
//	        }
//	        Bundle postParams = new Bundle();
//	        SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
//		    String id = match.getString(String.valueOf(idKey),"");
//	        postParams.putString("message", "\"Friendship is a single soul dwelling in two bodies.\" - Linkus");
//	       // postParams.putString("name", "\"Friendship is a single soul dwelling in two bodies.\" - Linkus");
//	        postParams.putString("privacy", privacy.toString());
//	        //postParams.putString("caption", "Build great social apps and get more installs.");
//	        //postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
//	        postParams.putString("link", "www.facebook.com/"+id);
//	        //postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
//
////	        Request.Callback callback= new Request.Callback() {
////	            public void onCompleted(Response response) {
////	            
////	            	
////	            	 }
////	        };
////	        if (session.isOpened()) { 
////	        	//Log.w("request","POST to feed");
////	        Request request = new Request(session, "me/feed", postParams, 
////	                              HttpMethod.POST, callback);
////	       // Log.w("task",request.toString());
////	        RequestAsyncTask task = new RequestAsyncTask(request);
////	        task.execute();}
////	    }
//
//	}
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
}

