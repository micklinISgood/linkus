package com.example.linkusv1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class UploadJSONObject {
	
	 public void connectServerString(JSONObject obj,String url){
		   // String url= "http://plash2.iis.sinica.edu.tw/api/LinkusUser.php";
         //uploadJSONObject.post(user.getInnerJSONObject(),url);
			HttpPost postRequest = new HttpPost(url);
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			int timeoutSocket = 180000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("data", obj.toString()));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, HTTP.UTF_8);
				postRequest.setEntity(entity);
				HttpResponse response = httpClient.execute(postRequest);
				Integer statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					System.out.println(in.readLine());
					in.close();
				} else {
					Log.e("upload service", "connection error, status code=" + statusCode);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				httpClient.getConnectionManager().shutdown();
			} 
	    }
	    
	    public void connectServer2(JSONObject obj,String url){
	    	try {
	    		 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost(url);
	 
	           
	 
	            // 3. build jsonObject
	           
	 
	            // 4. convert JSONObject to JSON to String
	            String  json = obj.toString();
	 
	            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
	            // ObjectMapper mapper = new ObjectMapper();
	            // json = mapper.writeValueAsString(person); 
	 
	            // 5. set json to StringEntity
	            StringEntity se = new StringEntity(json);
	 
	            // 6. set httpPost Entity
	            httpPost.setEntity(se);
	 
	            // 7. Set some headers to inform server about the type of the content   
	            httpPost.setHeader("data", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	 
	            // 8. Execute POST request to the given URL
	            HttpResponse httpResponse = httpclient.execute(httpPost);
	 
	          /*  // 9. receive response as inputStream
	            inputStream = httpResponse.getEntity().getContent();
	 
	            // 10. convert inputstream to string
	            if(inputStream != null)
	                result = convertInputStreamToString(inputStream);
	            else
	                result = "Did not work!";
	           */
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	    	
	    }
	
	public void post(JSONObject obj, String url) {
		try {
		
			// Log.w("uploadUrl", uploadUrl);
			// the method to be used, with url
			HttpPost postRequest = new HttpPost(url);
			// GET DATA FROM DB
			//JSONObject data = dh.getOneTripData(userid, localTripid, true);
			// pair our data with corresponding name
//			List<NameValuePair> param = new ArrayList<NameValuePair>();
//			param.add(new BasicNameValuePair("trip", data.toString()));
			
			//JSONObject tripinfo = dh.getOneTripInfo(userid, localTripid);
			// Log.e("UploadThread", "uploadtripdata2: tripinfo= " +
			// tripinfo.toString());
//			param.add(new BasicNameValuePair("tripinfo", tripinfo.toString()));
			
//			for (NameValuePair item : param) {
//				Log.e("UploadThread", "uploadtripdata2: param.item= " + item.toString());
//			}
			
			// encode our data with UTF8
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, HTTP.UTF_8);
			
		//	MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			//entity.addPart("data", new StringBody(obj.toString(), Charset.forName("UTF-8")));
			
			
			// put our data in the method object
			//postRequest.setEntity(entity);
			
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used. 
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
//			int timeoutSocket = 5000;
			int timeoutSocket = 0;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			// execute the request and catch the response
			HttpResponse response = httpClient.execute(postRequest); 
			
			//if received 200 ok status
		Integer statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				// extrace the return message
				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String msg = in.readLine();
				in.close();
				if (msg != null && msg.contains("Ok")) {
					Log.w("upload service", "uplaodtripdata result: good");
					
					try {
					
						JSONObject tmp = new JSONObject(new JSONTokener(msg));
						String servertripid = tmp.getString("data");
						JSONArray tmp1 = new JSONArray(new JSONTokener(servertripid));
						Log.w("length",String.valueOf(tmp1.length()));
						for(int j=0;j<tmp1.length();j++){
						        JSONObject data  =tmp1.getJSONObject(j);
						        if(data.length()>1){
						        String nearId= data.getString("nearby_id");
						        String education = data.getString("education");
						        switch(j){
						        case 0:
						        	
						        case 1:
						        case 2:
						        	
						        }
						        
						        Log.w("obj",nearId);
						        
						       // Log.w("obj",education);
						        //Log.w("obj",String.valueOf(obj.length()));
						        Log.w("obj",obj.toString());
						        }
						    }
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					httpClient.getConnectionManager().shutdown();
					//return true;
				} else {
					Log.e("upload service", "uplaodtripdata error: upload failed, messsage=" + msg);
					httpClient.getConnectionManager().shutdown();
					//return false;
				}
			} else {
				// connection error
				Log.e("upload service", "uplaodtripdata error: connection failed, status code=" + statusCode);
				httpClient.getConnectionManager().shutdown();
				//return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.e("upload service", "uplaodtripdata error: exception");
		//return false; 
	   
	} 

}