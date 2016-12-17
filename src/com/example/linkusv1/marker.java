package com.example.linkusv1;

import gov.nasa.jpf.annotation.Checkpoint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class marker extends Activity {

	private SharedPreferences linkusdata;
	private Context mContext;
	private Boolean near0 =true;
	private Boolean near1 =false;
	private Boolean near2 =false;
	private int position =0;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private String url0;
	private String url1;
	private String url2;
	
	
	
	 private BroadcastReceiver _refreshReceiver = new BroadcastReceiver(){
	  
	  @Override public void onReceive(Context context, Intent intent) {
	 // stopService(new Intent(mContext,PhotoService.class));
	 // marker=true;
		  finish();
	  Log.e("receiver", "closed by broadcast");
	
	  } };
	@Checkpoint("creatMarker")  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getApplicationContext();
		setContentView(R.layout.evalue4);
		Log.e("check gogo", "near0");
		Intent intent = getIntent();
		near1=intent.getBooleanExtra("near1exisited", false);
		near2=intent.getBooleanExtra("near2exisited", false);
		mRequestQueue = Volley.newRequestQueue(mContext);
		mImageLoader = new ImageLoader(mRequestQueue,  new ImageLoader.ImageCache(){
		    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
		    public void putBitmap(String url, Bitmap bitmap) {
		        mCache.put(url, bitmap);
		    }
		    public Bitmap getBitmap(String url) {
		        return mCache.get(url);
		    }
		});
	
		linkusdata = PreferenceManager.getDefaultSharedPreferences(mContext);
		url0="https://graph.facebook.com/"+linkusdata.getString("near0", "")+"/picture?type=large&width=300";
		url1="https://graph.facebook.com/"+linkusdata.getString("near1", "")+"/picture?type=large&width=300";
		url2="https://graph.facebook.com/"+linkusdata.getString("near2", "")+"/picture?type=large&width=300";
		          
		ImageView right = (ImageView) findViewById(R.id.right);
		right.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e("click", "right");
			   switch(position){
			   case 0:
				   if(near1){   
				   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
				   mImageView.setImageUrl(url1,mImageLoader);
				  

					TextView mTextView = (TextView) findViewById(R.id.education);
			        mTextView.setText(linkusdata.getString("ed1",""));
			        ImageView leftini = (ImageView) findViewById(R.id.left);
				   	leftini.setVisibility(View.VISIBLE);
				   	if(near2){
					   	 ImageView righttini = (ImageView) findViewById(R.id.right);
						   	righttini.setVisibility(View.VISIBLE);
					   	}else{
					   	 ImageView right00 = (ImageView) findViewById(R.id.right);
//						   	right00.setVisibility(View.INVISIBLE);
						   	}
			        position =1;
			        break;
			        }
				   else{
					   if(near2){
						   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
						   mImageView.setImageUrl(url2,mImageLoader);

							TextView mTextView2 = (TextView) findViewById(R.id.education);
					        mTextView2.setText(linkusdata.getString("ed2",""));
					        ImageView leftini = (ImageView) findViewById(R.id.left);
						   	 leftini.setVisibility(View.VISIBLE);
						   	ImageView right00 = (ImageView) findViewById(R.id.right);
						   	right00.setVisibility(View.INVISIBLE);
					        position =2;
					        break;
					        }
						   else{
							    ImageView lefticon = (ImageView) findViewById(R.id.left);
							   	 lefticon.setVisibility(View.INVISIBLE);
							     ImageView righticon = (ImageView) findViewById(R.id.right);
							   	 righticon.setVisibility(View.INVISIBLE);
							   	 position = 0;
						   }
				   }
				   break;
			   case 1: 
				   if(near2){
					   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
					   mImageView.setImageUrl(url2,mImageLoader);

					TextView mTextView2 = (TextView) findViewById(R.id.education);
			        mTextView2.setText(linkusdata.getString("ed2",""));
			        ImageView leftini = (ImageView) findViewById(R.id.left);
				   	leftini.setVisibility(View.VISIBLE);
				   	ImageView righticon = (ImageView) findViewById(R.id.right);
				   	righticon.setVisibility(View.INVISIBLE);
			        position =2;
			        break;
			        }
				   else{
					     ImageView lefticon = (ImageView) findViewById(R.id.left);
					   	 lefticon.setVisibility(View.INVISIBLE);
					     ImageView righticon = (ImageView) findViewById(R.id.right);
					   	 righticon.setVisibility(View.INVISIBLE);
					   	 position = 1;
				   }
			       break;
			   }
		}
		});
		
		ImageView left = (ImageView) findViewById(R.id.left);
		left.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e("click", "left");
			  switch(position){
			   case 1:
				   if(near0){
					   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
					   mImageView.setImageUrl(url0,mImageLoader);

					TextView mTextView = (TextView) findViewById(R.id.education);
			        mTextView.setText(linkusdata.getString("ed0",""));
			        ImageView rightini = (ImageView) findViewById(R.id.right);
				   	rightini.setVisibility(View.VISIBLE);
				    ImageView lefticon = (ImageView) findViewById(R.id.left);
				   	 lefticon.setVisibility(View.INVISIBLE);
			        position =0;
			        break;
			        }
				   else{
					   ImageView rightini = (ImageView) findViewById(R.id.right);
					   	rightini.setVisibility(View.INVISIBLE);
					   ImageView lefticon = (ImageView) findViewById(R.id.left);
					   	 lefticon.setVisibility(View.INVISIBLE);
					     position = 1;
				   }
				   break;
			   case 2:  
				   if(near1){
					   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
					   mImageView.setImageUrl(url1,mImageLoader);

					TextView mTextView2 = (TextView) findViewById(R.id.education);
			        mTextView2.setText(linkusdata.getString("ed1",""));
			        ImageView rightini = (ImageView) findViewById(R.id.right);
				   	rightini.setVisibility(View.VISIBLE);
				 	if(near0){
					   	 ImageView lefttini = (ImageView) findViewById(R.id.left);
						   	lefttini.setVisibility(View.VISIBLE);
					   	}else{
					   	 ImageView left00 = (ImageView) findViewById(R.id.left);
						   	left00.setVisibility(View.INVISIBLE);
						   	}
			        position =1;
			        break;
				   }
				   else{
					   if(near0){
						   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
						   mImageView.setImageUrl(url0,mImageLoader);

							TextView mTextView = (TextView) findViewById(R.id.education);
					        mTextView.setText(linkusdata.getString("ed0",""));
					        ImageView rightini = (ImageView) findViewById(R.id.right);
						   	rightini.setVisibility(View.VISIBLE);
						    ImageView left00 = (ImageView) findViewById(R.id.left);
						   	left00.setVisibility(View.INVISIBLE);
					        position =0;
					        break;
					        }
						   else{
							   ImageView rightini = (ImageView) findViewById(R.id.right);
							   	rightini.setVisibility(View.INVISIBLE);
							   ImageView lefticon = (ImageView) findViewById(R.id.left);
							   	 lefticon.setVisibility(View.INVISIBLE);
							   	 position=2;
						   }
				   }
			       break;
			   }
			  
		}
		});
		
		ImageView yes = (ImageView) findViewById(R.id.yes);
		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("click", "yes");
				 ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isConnected()) {
						
						 switch(position){
						   case 0:
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near0", ""),1,mContext,position).execute();
							   near0= false;
							   if(near1){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url1,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed1",""));
							        ImageView leftini = (ImageView) findViewById(R.id.left);
								   	leftini.setVisibility(View.INVISIBLE);
								   	if(near2){
								   	 ImageView righttini = (ImageView) findViewById(R.id.right);
									   	righttini.setVisibility(View.VISIBLE);
								   	}else{
								   	 ImageView right00 = (ImageView) findViewById(R.id.right);
									   	right00.setVisibility(View.INVISIBLE);
								   	}
							        position =1;
							        break;
							        }
								   else{
									   if(near2){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url2,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed2",""));
									        ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.INVISIBLE);
										   	ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									        position =2;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
							   break;
						   case 1:  
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near1", ""),1,mContext,position).execute();
							   near1= false;
							   if(near0){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url0,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed0",""));
							        ImageView leftini = (ImageView) findViewById(R.id.left);
								   	leftini.setVisibility(View.INVISIBLE);
								   	if(near2){
									   	 ImageView righttini = (ImageView) findViewById(R.id.right);
										   	righttini.setVisibility(View.VISIBLE);
									   	}else{
									   	 ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									   	}
							        position =0;
							        break;
							        }
								   else{
									   if(near2){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url2,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed2",""));
									        ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.INVISIBLE);
										   	ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									        position =2;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
							   
							   break;
						   case 2: 
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near2", ""),1,mContext,position).execute();
							   near2= false;
							   if(near1){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url1,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed1",""));
							        ImageView right00 = (ImageView) findViewById(R.id.right);
//								   	right00.setVisibility(View.INVISIBLE);
									if(near0){
									   	 ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.VISIBLE);
									   	}else{
									   	 ImageView left00 = (ImageView) findViewById(R.id.left);
//										   	left00.setVisibility(View.INVISIBLE);
									   	}
							        position =1;
							        break;
							        }
								   else{
									   if(near0){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url0,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed0",""));
									        ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
										    ImageView left00 = (ImageView) findViewById(R.id.left);
										    left00.setVisibility(View.INVISIBLE);
									        position =0;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
						       break;
						   }
								
						
						
						
					} else {
						 switch(position){
						   case 0:
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							   break;
						   case 1:  
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							   break;
						   case 2: 
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						       break;
						   }
									   
					
					}
			
			}
			});
	   
		ImageView no = (ImageView) findViewById(R.id.no);
		no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("click", "no");
				 ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isConnected()) {
						
						
						 switch(position){
						   case 0:
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near0", ""),0,mContext,position).execute();
							   near0= false;
							   if(near1){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url1,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed1",""));
							        ImageView leftini = (ImageView) findViewById(R.id.left);
								   	leftini.setVisibility(View.INVISIBLE);
								   	if(near2){
								   	 ImageView righttini = (ImageView) findViewById(R.id.right);
									   	righttini.setVisibility(View.VISIBLE);
								   	}else{
								   	 ImageView right00 = (ImageView) findViewById(R.id.right);
									   	right00.setVisibility(View.INVISIBLE);
								   	}
							        position =1;
							        break;
							        }
								   else{
									   if(near2){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url2,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed2",""));
									        ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.INVISIBLE);
										   	ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									        position =2;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
							   break;
						   case 1:  
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near1", ""),0,mContext,position).execute();
							   near1= false;
							   if(near0){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url0,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed0",""));
							        ImageView leftini = (ImageView) findViewById(R.id.left);
								   	leftini.setVisibility(View.INVISIBLE);
								   	if(near2){
									   	 ImageView righttini = (ImageView) findViewById(R.id.right);
										   	righttini.setVisibility(View.VISIBLE);
									   	}else{
									   	 ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									   	}
							        position =0;
							        break;
							        }
								   else{
									   if(near2){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url2,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed2",""));
									        ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.INVISIBLE);
										   	ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
									        position =2;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
							   
							   break;
						   case 2: 
							   new rankThread(linkusdata.getString("Id", ""),linkusdata.getString("near2", ""),0,mContext,position).execute();
							   near2= false;
							   if(near1){
								   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
								   mImageView.setImageUrl(url1,mImageLoader);

									TextView mTextView = (TextView) findViewById(R.id.education);
							        mTextView.setText(linkusdata.getString("ed1",""));
							        ImageView right00 = (ImageView) findViewById(R.id.right);
								   	right00.setVisibility(View.INVISIBLE);
									if(near0){
									   	 ImageView leftini = (ImageView) findViewById(R.id.left);
										   	leftini.setVisibility(View.VISIBLE);
									   	}else{
									   	 ImageView left00 = (ImageView) findViewById(R.id.left);
										   	left00.setVisibility(View.INVISIBLE);
									   	}
							        position =1;
							        break;
							        }
								   else{
									   if(near0){
										   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
										   mImageView.setImageUrl(url0,mImageLoader);

											TextView mTextView2 = (TextView) findViewById(R.id.education);
									        mTextView2.setText(linkusdata.getString("ed0",""));
									        ImageView right00 = (ImageView) findViewById(R.id.right);
										   	right00.setVisibility(View.INVISIBLE);
										    ImageView left00 = (ImageView) findViewById(R.id.left);
										    left00.setVisibility(View.INVISIBLE);
									        position =0;
									        break;
									        }
										   else{
											   Intent i = new Intent(mContext,Searching.class);
											    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
												startActivity(i);
												finish();
										   }
								   }
						       break;
						   }
								
						
						
						
					} else {
						 switch(position){
						   case 0:
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							   break;
						   case 1:  
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							   break;
						   case 2: 
							   startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						       break;
						   }
									   
					
					}
			
			}
			});
		
		 ImageView search = (ImageView) findViewById(R.id.search);
			search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.e("click", "search");
				 Intent i = new Intent(mContext,Searching.class);
				    i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(i);
				
			}
			});  
			
	   NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
			   mImageView.setImageUrl(url0,mImageLoader);

		TextView mTextView = (TextView) findViewById(R.id.education);
		        mTextView.setText(linkusdata.getString("ed0",""));
		        
		        
		 ImageView leftini = (ImageView) findViewById(R.id.left);
		   	 leftini.setVisibility(View.INVISIBLE);
		 if(near1==false && near2==false){
			 ImageView rightini = (ImageView) findViewById(R.id.right);
		   	 rightini.setVisibility(View.INVISIBLE);
		 }
			 
			
		
		//Log.e("check stat", "near0");
		 IntentFilter filter = new IntentFilter("searching");
		 this.registerReceiver(_refreshReceiver, filter);
		

	}
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	public void onResume() {
		super.onResume();
		//Log.e("check stat", "marker");
		if(near0){
			NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
			   mImageView.setImageUrl(url0,mImageLoader);
			   if(near1){
					NetworkImageView  mImageView1 = (NetworkImageView) findViewById(R.id.head);
					   mImageView1.setImageUrl(url1,mImageLoader);
					   if(near2){
							NetworkImageView  mImageView2 = (NetworkImageView) findViewById(R.id.head);
							   mImageView2.setImageUrl(url2,mImageLoader);
							   }	   
			       }
			   }
			      
			NetworkImageView  mImageView = (NetworkImageView) findViewById(R.id.head);
			   mImageView.setImageUrl(url0,mImageLoader);      
	        
	   


	}
	@Override
	public void onPause() {
		super.onPause();
       // SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
		
		//Log.e("checkmatch0", match.getString("0", ""));
		//Log.e("checkmatch1", match.getString("0", ""));
		//Log.e("checkmatch2", match.getString("0", ""));


		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//SharedPreferences match= PreferenceManager.getDefaultSharedPreferences(mContext);
		
		//Log.e("checkmatch", match.getString("0", ""));
		
		 this.unregisterReceiver(this._refreshReceiver);
		
	}
	
	
}
