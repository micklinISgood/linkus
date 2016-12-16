package com.example.linkusv1;

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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadThread extends AsyncTask<Void, Void, Integer> {
	private String json;
	private Context context;

	public UploadThread(String data,Context c) {
        this.context=c;
	
		this.json = data;
		//Log.e("upload thread", "constructor");
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		//Log.e("upload thread", "do in background");
		
		return POST(json);
	}

	private Integer POST(String json) {
		//Log.e("upload thread", "post");
		String url="http://52.90.69.201:7666/LinkusUser";
        //String url="http://plash2.iis.sinica.edu.tw/api/LinkusUser.php";
		Integer statusCode = 0;

		try {
			HttpPost httpPost = new HttpPost(url);
			// GET DATA FROM DB

			// pair our data with corresponding name
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("data", json));

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
		context.sendBroadcast(new Intent("upload"));
		Toast.makeText(context, "High Two", Toast.LENGTH_SHORT).show();
		if(result==200){
			  //can pass result by intent , go to next activity
			  Intent intent = new Intent(context, Searching.class);
	          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	          context.startActivity(intent);
		}
		else{
			Intent intent = new Intent(context, reRegister.class);
	          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	          intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
	          //context.startActivity(intent);
		}
		
		
		
	}

	
}
