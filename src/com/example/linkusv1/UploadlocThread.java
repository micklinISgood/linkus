package com.example.linkusv1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.Service;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.ContextWrapper.*;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class UploadlocThread extends AsyncTask<Void, Void, Integer> {
	private String json;
	private Context context;
	private SharedPreferences linkusdata;
	private Boolean near0 =false;
	private Boolean near1 =false;
	private Boolean near2 =false;

	public UploadlocThread(Context c) {
        this.context=c;
	
		//Log.e("upload thread", "constructor");
	}
	
	@Override
	protected Integer doInBackground(Void... c) {
		//Log.e("upload thread", "do in background");
		
		return POST();
	}

	private Integer POST() {
		
		linkusdata = PreferenceManager.getDefaultSharedPreferences(context);
//		String url = "http://plash2.iis.sinica.edu.tw/api/GetLinkusUser.php";
		String url = "http://52.90.69.201:7666/GetLinkusUser";
		
		Integer statusCode = 0;
		Log.e("input", linkusdata.getString("Id", ""));
		Log.e("input", linkusdata.getString("lat", ""));
		Log.e("input", linkusdata.getString("lng", ""));
		try {
			HttpPost httpPost = new HttpPost(url);
			// GET DATA FROM DB

			// pair our data with corresponding name
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("id", linkusdata.getString("Id",
					"")));
			param.add(new BasicNameValuePair("lat", linkusdata.getString("lat",
					"")));
			param.add(new BasicNameValuePair("lng", linkusdata.getString("lng",
					"")));

			// encode our data with UTF8
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param,
					HTTP.UTF_8);

			// put our data in the method object
			httpPost.setEntity(entity);

			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			// int timeoutSocket = 5000;
			int timeoutSocket = 5000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			HttpClient httpClient = new DefaultHttpClient(httpParameters);

			Log.e("uplaod thread", httpPost.toString());

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.e("uplaod thread", httpResponse.toString());

			statusCode = httpResponse.getStatusLine().getStatusCode();
			
			if (statusCode == 200) {
				// extrace the return message
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpResponse.getEntity().getContent()));
				String msg = in.readLine();
				in.close();
				if (msg != null) {
					//Log.w("upload service", msg);

					try {
						JSONObject tmp = new JSONObject(msg);
						//String server = tmp.getString("data");
						JSONArray tmp1 = tmp.getJSONArray("data");
						//Log.w("length", String.valueOf(tmp1.length()));
						linkusdata.edit().remove("near0").remove("ed0")
						                 .remove("near1").remove("ed1")
						                 .remove("near2").remove("ed2").commit();
						for (int j = 0; j < tmp1.length(); j++) {

							JSONObject obj = tmp1.getJSONObject(j);
							if (obj.length() > 1) {
								//Log.w("j", String.valueOf(j));
								
								String nearId = obj.getString("nearby_id");
								String education = obj.getString("education");
						
							
								//Log.w("obj", obj.toString());
								//Log.w("obj", nearId);
								//Log.w("obj", education);
								// Log.w("obj",String.valueOf(obj.length()));
								switch(j){
								case 0: 
									linkusdata.edit().putString("near0",nearId).commit();
									linkusdata.edit().putString("ed0",education).commit();
									break;
								case 1:
									linkusdata.edit().putString("near1",nearId).commit();
									linkusdata.edit().putString("ed1",education).commit();
									break;
								case 2:	
									linkusdata.edit().putString("near2",nearId).commit();
									linkusdata.edit().putString("ed2",education).commit();
									break;
								}

							}
						}

						return statusCode;
					} catch (JSONException e) {
						e.printStackTrace();
					}
					httpClient.getConnectionManager().shutdown();
					// return true;
				} else {
					//Log.e("upload service",							"uplaodtripdata error: upload failed, messsage="									+ msg);
					httpClient.getConnectionManager().shutdown();
					// return false;
				}
			} 
			
			
		} catch (Exception e) {
			Log.e("upload wrong", e.getLocalizedMessage());
			return 0;
		}

		// 11. return result
		return statusCode;
	}

	/*
	 * @Override protected void onProgressUpdate(Void... values) {
	 * super.onProgressUpdate(values); diag.setProgress((diag.getProgress() +
	 * 10)); }
	 */
	@Override
	protected void onPostExecute(Integer result) {
		Log.e("upload thread", "on post execute");
		Log.w("upload status", result.toString());
		super.onPostExecute(result);
		
		if(result==200){
			if((linkusdata.getString("near0", "")).length()> 0){
				near0=true;
			 // img0(linkusdata.getString("near0", ""));
			   if((linkusdata.getString("near1", "")).length()>0){
				   near1=true;
			    // img1(linkusdata.getString("near1", ""));
			       if((linkusdata.getString("near2", "")).length()>0){
			    	   near2=true;
			          // img2(linkusdata.getString("near2", ""));
			        }
			     }
			}
			if(near0){
		    Intent i = new Intent(context,marker.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i.putExtra("near0exisited",near0);
			i.putExtra("near1exisited",near1);
			i.putExtra("near2exisited",near2);
			Log.e("check service", "near0");
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	         context.startActivity(i);
			}
			else{
				Intent i = new Intent(context,nouser.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		         context.startActivity(i);
				
			}  
			context.sendBroadcast(new Intent("photo"));
			
			}
		
		else{
				context.sendBroadcast(new Intent("photo"));
                 Intent i = new Intent(context,Searching.class);
				 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 context.startActivity(i);

			}
		
		
		
	}

	
}
