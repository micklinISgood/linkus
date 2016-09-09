package com.example.linkusv1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class rankThread extends AsyncTask<Void, Void, Integer> {
		private String id;
		private String cand;
		private String status;
		private SharedPreferences match;
		private Context mContext;
		private int distinct;
		
		public rankThread (String argId,String argCand,int argStatus,Context c,int position) {
			this.id = argId;
			this.cand=argCand;
			this.status=String.valueOf(argStatus);
			this.mContext=c;
			this.distinct=position;
			//Log.e("upload thread", "constructor");
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			//Log.e("upload thread", "do in background");
			match=PreferenceManager.getDefaultSharedPreferences(mContext);
			
			
			
			return POST(id,cand,status);
		}
		@Override
		protected void onCancelled(){

		}
		private Integer POST(String id,String cand,String status) {
			//Log.e("upload thread", "post");
            String url ="http://plash2.iis.sinica.edu.tw/api/LinkusUserAct.php";
			Integer statusCode = 0;

			try {
				HttpPost httpPost = new HttpPost(url);
				// GET DATA FROM DB

				// pair our data with corresponding name
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("id", id));
				param.add(new BasicNameValuePair("cand", cand));
				param.add(new BasicNameValuePair("rank", status));

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

				//Log.e("uplaod thread", httpPost.toString());
				
				// 8. Execute POST request to the given URL
				HttpResponse httpResponse = httpClient.execute(httpPost);

				//Log.e("uplaod thread", httpResponse.toString());
				
				
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
							String wait = tmp.getString("matched_id0");
							//Log.w("upload service",String.valueOf(wait.length()));
							if (wait.length() > 2) {
								if(match.contains(String.valueOf(distinct))){
									distinct+=3;
								}
								match.edit().putString(String.valueOf(distinct), wait).commit();
							}
						

							return statusCode;
						} catch (JSONException e) {
							e.printStackTrace();
						}
						httpClient.getConnectionManager().shutdown();
						// return true;
					} else {
						//Log.e("upload service",								"uplaodtripdata error: upload failed, messsage="										+ msg);
						httpClient.getConnectionManager().shutdown();
						// return false;
					}
				} else {
					// connection error
				//	Log.e("upload service",							"uplaodtripdata error: connection failed, status code="									+ statusCode);
					httpClient.getConnectionManager().shutdown();
					// return false;
				}
			} catch (Exception e) {
				// Log.d("InputStream", e.getLocalizedMessage());
				 e.printStackTrace();

			}

			// 11. return result
			//Log.e("uplaod thread", statusCode.toString());
			return statusCode;
		}

		/*
		 * @Override protected void onProgressUpdate(Void... values) {
		 * super.onProgressUpdate(values); diag.setProgress((diag.getProgress() +
		 * 10)); }
		 */
		@Override
		protected void onPostExecute(Integer result) {
			//Log.e("upload thread", "on post execute");
			//Log.w("upload status", result.toString());
			super.onPostExecute(result);
			
			
			
		
			
			
		}

		
	}
