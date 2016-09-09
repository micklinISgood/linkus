package com.example.linkusv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class nouser extends Activity {


	private Context mContext;
	

	
	
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getApplicationContext();
		setContentView(R.layout.nouser);
	   
		

	}
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	public void onResume() {
		super.onResume();
	
			setContentView(R.layout.nouser);
			ImageView  imageButton = (ImageView) findViewById(R.id.search);
			imageButton.setOnClickListener(new  View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//sendBroadcast(new Intent("searching"));
				startActivity(new Intent(mContext, Searching.class));
				finish();
			}
			});
			
	


	}
	@Override
	public void onPause() {
		super.onPause();
	
		
		//Log.e("check", "nouserdestory");


		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		 
		
	}
}
