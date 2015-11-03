package com.testBlueTooth;

import java.io.IOException;
import java.io.InputStream;
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

import android.os.AsyncTask;

public class translateTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
          
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //textView.setText(result);
        //AsyncImageLoader.setImageViewFromUrl(imageUrl, imView);
   }


private String downloadUrl(String myurl) throws IOException {
    InputStream is = null;

    try {
    	 List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("name", "terry"));
         params.add(new BasicNameValuePair("longitude", "100.252255"));
         params.add(new BasicNameValuePair("latitude", "-15.415121"));
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
}
