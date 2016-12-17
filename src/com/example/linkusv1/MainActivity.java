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
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;



public class MainActivity extends Activity {
	//UiLifecycleHelper initialize

	private SharedPreferences linkusdata;
	private Context mContext;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	//facebook login callback setting and onComplete function
 

	
	private BroadcastReceiver _refreshReceiver = new BroadcastReceiver(){
		  
		  @Override public void onReceive(Context context, Intent intent) {
		 // stopService(new Intent(mContext,PhotoService.class));
		  //startActivity(new Intent(mContext, near0.class));
			  finish();
		 // Log.e("receiver", "closed by broadcast");
		
		  } };
		 		
	
	//permissions
	private String[] perm = {"user_activities","user_birthday","user_education_history","user_events","user_location"
			,"user_interests","user_photos","user_work_history","email","user_likes","user_status","read_requests"}; 
	private List<String> perms = Arrays.asList(perm);
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = getApplicationContext();
       
        IntentFilter filter = new IntentFilter("upload");
		 this.registerReceiver(_refreshReceiver, filter);
        //______________________________________________
        //insert loading fragment here for login waiting
        //add dialogue for checking internet connection
        //______________________________________________
        
 
        //read permissions with login button
	        final Button button = (Button) findViewById(R.id.login_button);
	        button.setOnClickListener(new View.OnClickListener() {
	        	@Checkpoint("Login") 
	            public void onClick(View v) {
                	new UploadThread(linkusdata.getString("userJson", ""),MainActivity.this.getApplicationContext()).execute();
	        		
	            }
	        });
	    	
	        EditText editText = (EditText) findViewById(R.id.fb_id);
	        editText.setText("123");
	        editText.addTextChangedListener(new TextWatcher() {

	            public void afterTextChanged(Editable s) {
	            	Log.e("check",s.toString());
	            	if(s.length()>0){
	            		JSONObject value = new JSONObject();
	            		
	            		 try {
							value.put("id", s.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		 Editor editor = linkusdata.edit();
	                	 editor.putString("Id",s.toString());
	                	 editor.putString("userJson",value.toString());
	                     editor.commit();
	            		 
	            		button.setEnabled(true); 
	            	}
	            	else{
	            		button.setEnabled(false); 
	            	}
 

	            }

	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            	
	            	
	            }

	            public void onTextChanged(CharSequence s, int start, int before, int count) {
	            	
	            }
	         });
    
//	        linkusdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        	if(linkusdata.getString("Id", "").length()>0){
  	     		
//        		editText.setText(123);
//	         }
     	
     
      
     


      Log.e("check","0");
     
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	
    	//Log.e("check","1");
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
//		 	linkusdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//	     	if(linkusdata.getString("Id", "").length()>0){
////	     		new UploadThread(linkusdata.getString("userJson", ""),MainActivity.this.getApplicationContext()).execute();
//	     		setContentView(R.layout.splash);
//	     		
//	         }
//    	Session session = Session.getActiveSession();
//        //when in login state without button
//    	if(session != null && session.isOpened()) {
//        	//Log.e("check","session");
//        	LoginButton button = (LoginButton) findViewById(R.id.login_button);
//        	button.setVisibility(View.INVISIBLE);
//        	
//        	Toast.makeText(this, "High Five", Toast.LENGTH_SHORT).show();
//          }
//    	//when in logout state with button
//        else{
//        	setContentView(R.layout.activity_main);
//        	LoginButton button = (LoginButton) findViewById(R.id.login_button);
//     		button.setReadPermissions(perms);
//     		//button.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
//        }
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
  
   
//    private void makeMeRequest(final Session session) {
//	    // Make an API call to get user data and define a 
//	    // new callback to handle the response.
//    	
//	    Request request = Request.newMeRequest(session, 
//	            new Request.GraphUserCallback() {
//	        @Override
//	        public void onCompleted(GraphUser user, Response response) {
//	            // If the response is successful
//	            if (session == Session.getActiveSession()) {
//	                if (user != null) {
//	                	//change view to indicate already login
//	                	//LoginButton button = (LoginButton) findViewById(R.id.login_button);
//	                	//button.setEnabled(false);
//	                	//Save User data to Sharedprefrence
//	                	
//	                	
//	                	JSONObject linkus = new JSONObject();
//	                	try {
//							linkus.put("linkusId",user.getId());
//							linkus.put("bday",user.getBirthday());
//							linkus.put("link",user.getLink());
//							//linkus.put("gender",user.getInnerJSONObject().getString("gender"));
//						
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//	             
//	                	
//	                	linkusdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//	                	if(linkusdata.getString("Id", "").length()>0){
//	                		linkusdata.edit().clear().commit();
//		                }
//	                	
//	                	Editor editor = linkusdata.edit();
//	                	editor.putString("Id",user.getId());
//	                	editor.putString("token",session.getAccessToken());
//                        editor.putString("access_expires",session.getExpirationDate().toString());
//                        editor.putString("userJson",user.getInnerJSONObject().toString());
//                        editor.commit();
//	                	/*
//                        ImageView imageButton = (ImageView) findViewById(R.id.search);
//                		imageButton.setOnClickListener(new View.OnClickListener() {
//                		@Override
//                		public void onClick(View v) {
//                			Log.e("click", "time");
//                			publishStory(); 
//                		}
//                		});*/
//	                   
//	                    //Start upload 
//                        
//                		new UploadThread(linkusdata.getString("userJson", ""),MainActivity.this.getApplicationContext()).execute();
//                		
//                		//close Activity,other activity must implement Onpause()
//                		//Once logout clear all sharedpreference,Start this Activity!
//                		//finish();
//	                }
//	                
//	            }
//	              if (response.getError() != null) {
//	            	 
//	                // Handle errors, will do so later.
//	            }
//	        }
//	    });
//	    Request.executeBatchAsync(request);
//	   
//	} 
    
   
    @Override
    public void onResume() {
        super.onResume(); 
       
       
       //Log.e("check","2");
    }

    @Override
    public void onPause() {
        super.onPause();
       
        
        //when move on other activity,close this activity
      //  Session session = Session.getActiveSession();
        //if(session != null && session.isOpened()) {
    		 
     	//	}
       
       // Log.e("check","3");
      
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
        //Log.e("check","4");
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	//outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
    	
    	// Log.e("check","5");
    }
   
    @Override
	public void onDestroy() {
		super.onDestroy();
		
		 this.unregisterReceiver(this._refreshReceiver);
	}
	
   
}
	
	

