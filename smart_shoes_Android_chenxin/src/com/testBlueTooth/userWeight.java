package com.testBlueTooth;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class userWeight extends Activity {

	private Handler mHandler;
	Button btnMeasure,btnBack;
	float weight=0,adjustFactor;
	TextView tvWeight;
	int getWeight;
	int date;
	ImageView imgChart;
	String imgUrl = "";
	sqliteWeight sqlW = new sqliteWeight();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userweight);
		Calendar c = Calendar.getInstance();
		date = c.get(Calendar.DATE);
		retrieveSharedPreferences();
		imgChart = (ImageView) this.findViewById(R.id.imgChart);
		tvWeight = (TextView)this.findViewById(R.id.tvWeight);
		tvWeight.setText(""+(int)weight+"Kg");
		btnMeasure = (Button) this.findViewById(R.id.btnMeasure);
		btnMeasure.setOnClickListener(new ClickEvent());
		btnBack = (Button) this.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new ClickEvent());
		formImg();
		new DownloadWebpageTask().execute(imgUrl);
		//System.out.println(loadFromSDFile("testfile.txt"));
		//System.Pause("");
	}
	private class ClickEvent implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnMeasure)
			{
				String url = "http://192.168.104.162:80/cxtest/adddata_arduino1.php?";
				sendAndReceive();
				sendAndReceive();
				Log.d("adjustfactor","ad"+userInfo.adjustFactor);
				weight = adjustFactor * getWeight;
				Log.d("user's weight","ad"+weight);
				mHandler = new MyHandler();
				mHandler.sendEmptyMessage(02);
				saveInSharedPreferences();
				sqlW.createOrOpenDatabase();
				sqlW.insertWeight("klaus", weight);
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			    if (networkInfo != null && networkInfo.isConnected()) {
			       // textView = (TextView) findViewById(R.id.text);
			        	 try {
			        		 new sendDataToPHP().execute(url);
						} catch (Exception e) {
							e.printStackTrace();
						}
			    } else {
			    
			}
			}
			if(v == btnBack)
			{
				formImg();
				//sqlW.delete();
				//sqlW.delete();
				mainpage.allowRun = true;
				mainpage.refresh = true;
				Intent intent = new Intent();
				intent.setClass(userWeight.this,mainpage.class);
				startActivity(intent);
			}
			
	}
	}
	private class MyHandler extends Handler{ 
		@Override		    
	    public void dispatchMessage(Message msg) { 
			switch(msg.what){
			case 02:
				tvWeight.setText(""+(int)weight+"Kg");//setView(user);
				//userInfoActivity.this.sendMessage("W");
				//getMessage();
				//tvWeight.setText("体重"+getWeight+"Kg");
				//userInfoActivity.this.sendMessage("E");
				break;

	        default:	            
	            break;
			}
			}
	}
	public void formImg(){
		imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=weight/kg&chts=00A5C6,20,c&chma=30,20,0,0&chds=40,90&chxt=y&chxr=0,40,90,10&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,999999,0.2,BBBBBB,0.2,DDDDDD,0.2,FFFFFF,0.2&chd=t:";
		String s = "45,46,45";
		//s += sqlW.queryWeight();
		imgUrl += s;
		Log.v("query s:",s);
	}
	public void saveInSharedPreferences(){
		SharedPreferences formStore;
		formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = formStore.edit();
		editor.putFloat("weight",weight);
		editor.commit();
	}
	public void retrieveSharedPreferences(){
		 SharedPreferences formStore;
			formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
			weight = formStore.getFloat("weight",0);
			adjustFactor = formStore.getFloat("adjustFactor",0);

		}
	public void sendAndReceive() {
		InputStream inStream = null;
		OutputStream outStream = null;
		//控制模块
		try {
			outStream = testBlueTooth.btSocket.getOutputStream();	
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), " Output stream creation failed.", Toast.LENGTH_SHORT);
			
		}
		try {					
			inStream = testBlueTooth.btSocket.getInputStream();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), " input stream creation failed.", Toast.LENGTH_SHORT);
			
		}	
		String  encodeType ="GBK";
		byte[] msgBuffer = null;
		String message = "D";
		try {
			msgBuffer = message.getBytes(encodeType);//编码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			Log.e("write", "Exception during write encoding GBK ", e1);
		}
		for (int i = 0; i< 3;i ++) 
		{
				try {
					outStream.write(msgBuffer);				
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), "发送数据失败", Toast.LENGTH_SHORT);
					
				}					                      
			byte[] temp = new byte[5];
			String tmp = "";
			int readnum = 0;
			if (inStream!= null) {
			try{
					int len = inStream.read(temp,0,5);
					//len = inStream.read(temp,0,1);
					Log.v("len","::"+len);
						if (len > 0) 
						{
							//读编码
				            readnum =(int) (temp[0] & 0xff)<<8 | (temp[1] & 0xff);
				            getWeight = (4095 - readnum);
				            //fout.write((Integer.toString(4095-readnum)+"\n") .getBytes("utf-8"));
				            Log.v("the num read is","::"+readnum);
				           // mHandler.obtainMessage(01,len,-1,tmp).sendToTarget();
				            
						}			             
		             Thread.sleep(100);// 延时一定时间缓冲数据
		             //isRecording = false;
		             //mHandler.obtainMessage(01,len,-1,int2char(4095 - readnum)).sendToTarget();
					}catch (Exception e) {
						// TODO Auto-generated catch block
						//mHandler.sendEmptyMessage(00);
					}				
			
			}
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
	         //tvWeight.setText(result);
	         //AsyncImageLoader.setImageViewFromUrl(imageUrl, imView);
	    }
	 }
	 private String translateByUrl(String myurl) throws IOException {
	     InputStream is = null;

	     try {
	     	 List<NameValuePair> params = new ArrayList<NameValuePair>();
		         params.add(new BasicNameValuePair("name", "Klaus"));
		         params.add(new BasicNameValuePair("weight", Integer.toString((int)weight)));
		         params.add(new BasicNameValuePair("date", Integer.toString(date)));
		         //params.add(new BasicNameValuePair("weight", Integer.toString(user.walkStep)));
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
	 private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
		    @Override
		    protected String doInBackground(String... urls) {
		          
		        return "s";
		    }
		    // onPostExecute displays the results of the AsyncTask.
		    @Override
		    protected void onPostExecute(String result) {
		        AsyncImageLoader.setImageViewFromUrl(imgUrl, imgChart);
		   }
		}
}


