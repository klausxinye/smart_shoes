package com.testBlueTooth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class moreActivity extends Activity {

	ListView userList;
	Button btnBack;
	String url = "http://192.168.104.85:80/smartshoes/matchuser.php?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		btnBack = (Button) this.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new ClickEvent());
		userList = (ListView) this.findViewById(R.id.list);
		getinfo();
		//System.out.println(loadFromSDFile("testfile.txt"));
		//System.Pause("");
	}
	private class ClickEvent implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnBack)
			{
			Intent intent = new Intent();
			intent.setClass(moreActivity.this,mainpage.class);
			startActivity(intent);
			finish();
			}
			
		}
	}
	private void getinfo(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           // textView = (TextView) findViewById(R.id.text);
	        	 try {
	        		 new sendDataToPHP().execute(url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            //new DownloadWebpageTask().execute(url);
        } else {
        	//textView.setText("No network connection available.");
        
	}
	}
	private class sendDataToPHP extends AsyncTask<String, Void, String> {
	     @Override
	     protected String doInBackground(String... urls) {
	         // params comes from the execute() call: params[0] is the url.
	         try {
	             return translateByUrl(urls[0]);
	         } catch (IOException e) {
	             return "Unable to retrieve web page. URL may be invalid.";
	         }
	     }
	     // onPostExecute displays the results of the AsyncTask.
	     @Override
	     protected void onPostExecute(String result) {
	    	 try { 
	    		 //String[] s= getJsonToStringArray(result);
	    		 Log.v("arr:","s"+result);
	        	 JSONArray arr = new JSONArray(result);
	        	 String[] s = new String[arr.length()];
	        	 Log.v("arr:",""+arr.length());
	             for (int i = 0; i < arr.length(); i++) {  
	        	     s[i] = arr.getString(i);
	        	     //Log.v("string:",data);
        	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(moreActivity.this,android.R.layout.simple_list_item_1,s); 
	    		 userList.setAdapter(new ArrayAdapter<String>(moreActivity.this,android.R.layout.simple_list_item_1,s));
	        	 }
	         } catch (JSONException e) { 
	             System.out.println("Jsons parse error !"); 
	             e.printStackTrace(); 
	         } 
	         //AsyncImageLoader.setImageViewFromUrl(imageUrl, imView);
	    }
	 }
	 private String translateByUrl(String myurl) throws IOException {
	     InputStream is = null;

	     try {
	     	 List<NameValuePair> params = new ArrayList<NameValuePair>();
		         params.add(new BasicNameValuePair("name", "Klaus"));
		         String s1 = "";
				try {
					s1 = getResponseStringWithHttpPost(myurl,params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         return s1;
	     // Makes sure that the InputStream is closed after the app is
	     // finished using it.
	     } finally {
	         if (is != null) {
	             is.close();
	         } 
	     }
	 }
	 public static String getResponseStringWithHttpPost(
			String strRequestBaseUrl, List<NameValuePair> params)
			throws Exception {
			HttpPost httpPost = new HttpPost(strRequestBaseUrl);
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httpPost.setEntity(httpEntity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			}
			return null;//throw new RequestFailExeption(httpResponse.getStatusLine().getStatusCode());
		}
	 public static String[] getJsonToStringArray(String str) {
    	 JSONArray arr = null;
		try {
			arr = new JSONArray(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	 String[] s=new String[arr.length()];
    	 //Log.v("arr:",""+arr.length());
         for (int i = 0; i < arr.length(); i++) {  
    	     try {
				s[i] = arr.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	     //Log.v("string:",data);
    	 }
           return s;
        }
}
