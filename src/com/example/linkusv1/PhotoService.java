package com.example.linkusv1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.skyhookwireless.wps.WPSAuthentication;
import com.skyhookwireless.wps.WPSContinuation;
import com.skyhookwireless.wps.WPSGeoFence;
import com.skyhookwireless.wps.WPSLocation;
import com.skyhookwireless.wps.WPSPeriodicLocationCallback;
import com.skyhookwireless.wps.WPSReturnCode;
import com.skyhookwireless.wps.WPSStreetAddressLookup;
import com.skyhookwireless.wps.XPS;

public class PhotoService extends Service {

	private static final String WPS_USERNAME ="MikeLin";// "plash";//;//"plash";
	private static final String WPS_REALM ="Sinica";//"iis.sinica.edu.tw";// "Sinica";//"iis.sinica.edu.tw";

	private final static WPSAuthentication _auth = new WPSAuthentication(
			WPS_USERNAME, WPS_REALM);
	private Context mContext;
	private SharedPreferences linkusdata;
	private String TAG = "log";

	private XPS _xps;
	private Boolean near0 =false;
	private Boolean near1 =false;
	private Boolean near2 =false;

	@Override
	public void onCreate() {
		super.onCreate();

		mContext = getApplicationContext();
		_xps = new XPS(mContext);
		_xps.setRegistrationUser(_auth);
		//auth, timeIntervalInSeconds, XPS.EXACT_ACCURACY
		//_xps.getPeriodicLocation(null,
			//	WPSStreetAddressLookup.WPS_NO_STREET_ADDRESS_LOOKUP, 0, 0,
				//_callback);
		
		
	}
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       
            // We will force a new location update by calling getPeriodicLocation() again.
            //
            // IMPORTANT: We will continue to use the geofencing period of 1 hour because
            //            we only need to update the location once. By using the same callback
            //            we do not interrupt location updates for geofencing.
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
		_xps.getPeriodicLocation(null,WPSStreetAddressLookup.WPS_NO_STREET_ADDRESS_LOOKUP, 10000, 0,	_callback);
			//_xps.getXPSLocation(_auth, 10, XPS.EXACT_ACCURACY, _callback);
		Log.e("service", "gogogo");
		Toast.makeText(mContext, "Legen...Wait for it...", Toast.LENGTH_LONG).show();
		}
		else{
			sendBroadcast(new Intent("photo"));
            Intent i = new Intent(mContext,Searching.class);
		    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplication().startActivity(i);

			stopSelf();
		}
       

        return START_STICKY;
    }

	private final WPSPeriodicLocationCallback _callback = new WPSPeriodicLocationCallback() {
		@Override
		public void done() {
			Log.d(TAG, "periodic location done()");
		}

		@Override
		public WPSContinuation handleError(WPSReturnCode error) {
			//Log.d(TAG, "handleError: " + error);
            
			switch (error) { 
			case WPS_ERROR: Log.d("skyhooktest","error Soe other error occurred.");  break;
			case WPS_ERROR_LOCATION_CANNOT_BE_DETERMINED:  Log.d("skyhooktest","error: A location couldn't be determined.");  break;
			case WPS_ERROR_NO_WIFI_IN_RANGE:  Log.d("skyhooktest","error: No Wifi reference points in range.");  break;
			case WPS_ERROR_SERVER_UNAVAILABLE: Log.d("skyhooktest","error: The server is unavailable."); break;
			case WPS_ERROR_UNAUTHORIZED:  Log.d("skyhooktest","error:User authentication failed.");  break;
			case WPS_ERROR_WIFI_NOT_AVAILABLE:  Log.d("skyhooktest","error: No Wifi adapter was detected.");
			                                     sendBroadcast(new Intent("photo"));
			                                     Intent i = new Intent(mContext,nouser.class);
			             						 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			             						 getApplication().startActivity(i);
			                                     stopSelf();
			                                     break;
			case WPS_OK:  Log.d("skyhooktest","error: ok");  break;
			}
			
			//no internet exception
			return WPSContinuation.WPS_CONTINUE;

		}

		@Override
		public WPSContinuation handleWPSPeriodicLocation(WPSLocation location) {
			Log.d(TAG, "handleWPSPeriodicLocation: " + location);
			Log.e("skykooktest", "lat=" + location.getLatitude() + " long="
					+ location.getLongitude());
			if (location.hasHPE())
				Log.e("skykooktest", "accuracy in meters: " + location.getHPE());
			else
				Log.e("skykooktest", "unknown accuracy");
			Log.e("skykooktest", "altitude=" + location.getAltitude()
					+ " number of cell towers used=" + location.getNCell()
					+ " number access pts used: " + location.getNAP());
			Log.e("skykooktest", "speed: " + location.getSpeed()
					+ " bearing (degrees): " + location.getBearing());
			// _isLocationRequestPending = false;
			// sendLocationIntent(location);
			
				linkusdata = PreferenceManager
						.getDefaultSharedPreferences(mContext);
				linkusdata
						.edit()
						.putString("lat",
								String.valueOf(location.getLatitude()))
						.putString("lng",
								String.valueOf(location.getLongitude()))
						.commit();
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
				if (POST() == 200){
					// start searching
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
				    Intent i = new Intent(mContext,marker.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					i.putExtra("near0exisited",near0);
					i.putExtra("near1exisited",near1);
					i.putExtra("near2exisited",near2);
					Log.e("check service", "near0");
					getApplication().startActivity(i);}
					else{
						Intent i = new Intent(mContext,nouser.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						getApplication().startActivity(i);
						
					}  
					sendBroadcast(new Intent("photo"));
					
					}
				}
				else{
					     sendBroadcast(new Intent("photo"));
                         Intent i = new Intent(mContext,Searching.class);
						 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						 getApplication().startActivity(i);

					}
				stopSelf();
			

			return  WPSContinuation.WPS_STOP;
		}
	};

	@Override
	public void onDestroy() {
		Log.e("service", "close service by using broadcast");
		// Make sure to abort any ongoing operations during onDestroy
		Toast.makeText(mContext, "Dary", Toast.LENGTH_SHORT).show();
		_xps.abort();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private Integer POST() {
		// Log.e("upload thread", "post");
		linkusdata = PreferenceManager.getDefaultSharedPreferences(mContext);
		String url = "http://plash2.iis.sinica.edu.tw/api/GetLinkusUser.php";
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
								String etemp = obj.getString("education");
								String education = cast(etemp);
							
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
			} else {
				// connection error
				//Log.e("upload service",						"uplaodtripdata error: connection failed, status code="								+ statusCode);
				httpClient.getConnectionManager().shutdown();
				// return false;
			}
		} catch (Exception e) {
			 //Log.d("InputStream", e.getLocalizedMessage());
			 e.printStackTrace();

		}

		// 11. return result
		//Log.e("uplaod thread", statusCode.toString());
		return statusCode;
	}


	  private String cast(String str){

		String result ="";
		StringTokenizer tokenizer = new StringTokenizer(str,"_");
		while(tokenizer.hasMoreTokens()){
			result = result+tokenizer.nextToken()+"\n";
			//Log.e("stringtest",result);

		}
	
		
		return result;
	}
}