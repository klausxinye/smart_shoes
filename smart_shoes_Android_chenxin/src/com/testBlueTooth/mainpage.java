package com.testBlueTooth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class mainpage extends Activity{
	private Button btnConnect,btnSetting,btnExit,btnq,btnHistory,btnWeight;
	private Button btnMore;
	private TextView tvStatus,tvRun,tvWalk;
	private TextView tvnet1;
	private Handler mHandler;
	private ConnectedThread manageThread;
	private refreshThread refreshThread;
	public static boolean isRecording = false;
	private InputStream inStream = null;
	private FileOutputStream fout;
	private OutputStream outPutStream = null;
	private String encodeType="UTF-8";
	private BluetoothServerSocket serverSocket;
	public static user user = new user();
	public userInfo userInfo = new userInfo();
	sqlite sqlite = new sqlite();
	public static boolean refresh = true,allowRun = true;
	int count = 0;
	String url = "";
	String status;
    private BluetoothAdapter btAdapt;
	private BluetoothSocket socket1 = null;
	private BluetoothSocket btSocket1;
	//private AcceptThread serverThread;
	private OnDayChangeThread onDayChangeThread;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.test2);
    btnWeight = (Button) this.findViewById(R.id.btnWeight);
    btnWeight.setOnClickListener(new ClickEvent());
    btnMore = (Button) this.findViewById(R.id.btnMore);
    btnMore.setOnClickListener(new ClickEvent());
    btnSetting = (Button) this.findViewById(R.id.btnSetting);
    btnSetting.setOnClickListener(new ClickEvent());
    btnHistory = (Button) this.findViewById(R.id.btnHistory);
    btnHistory.setOnClickListener(new ClickEvent());
    tvStatus = (TextView)this.findViewById(R.id.tvStatus);
    btAdapt = BluetoothAdapter.getDefaultAdapter();
}
@Override
public void onStart(){
	super.onStart();
	user.username = "klaus";
	url = "http://192.168.104.169:80/smartshoes/save_data.php?";
	Calendar c = Calendar.getInstance();
	int date = c.get(Calendar.DATE);
	SharedPreferences formStore;
	formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
	if(formStore.getInt("date",0) == 0 ||formStore.getInt("date",0)!=date)
		user.initialUser();
	else
	    retrieveUserData();
	//user.initialUser();
//	retrieveSharedPreference();
	user.initial();
    //onDateChange();
	mHandler  = new MyHandler();
	if(testBlueTooth.btSocket == null)
		mHandler.sendEmptyMessage(00);
	if(testBlueTooth.btSocket != null)
	{
	manageThread=new ConnectedThread();
	manageThread.Start();
	//acceptThread=new AcceptThread();
//	manageThread.Start();
	refreshThread=new refreshThread(); 
	refreshThread.Start();
	onDayChangeThread = new OnDayChangeThread();
	onDayChangeThread.Start();
	}
	
}
@Override
protected void onPause() {  
    super.onPause();
    saveUserData();
    saveInSharedPreferences();
//   refreshThread.suspend();
//    serverThread.suspend();
//    //有可能在执行完onPause或onStop后,系统资源紧张将Activity杀死,所以有必要在此保存持久数据  
} 
@Override  
protected void onStop() {  
    // TODO Auto-generated method stub  
    super.onStop();  
    saveUserData();
    saveInSharedPreferences();
}  
//@Override
protected void onResume() {  
    super.onResume(); 
   // retrieveUserData();
	retrieveSharedPreference();
//    refreshThread.resume();
//    serverThread.resume(); 
}  
protected void onDestroy() {  
    super.onDestroy();  
    saveUserData();
    saveInSharedPreferences();
} 
private class ClickEvent implements View.OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnHistory)
		{
			
//			onDateChange();
			Intent intent = new Intent();
			intent.setClass(mainpage.this,historyChartActivity.class);
			startActivity(intent);
		}
		if(v == btnWeight)
		{
//			Log.v("hehe",sqlite.queryHistory(1, "klaus"));
			allowRun = false;
			refresh = false;
			Intent intent = new Intent();
			intent.setClass(mainpage.this,userWeight.class);
			startActivity(intent);
		}
		if(v == btnMore)
		{
			Intent intent = new Intent();
			intent.setClass(mainpage.this,moreActivity.class);
			startActivity(intent);
//			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//	        if (networkInfo != null && networkInfo.isConnected()) {
//	           // textView = (TextView) findViewById(R.id.text);
//		        	 try {
//		        		 new sendDataToPHP().execute(url);
//					} catch (Exception e) {
//	
//						e.printStackTrace();
//					}
//	            //new DownloadWebpageTask().execute(url);
//	        } else {
//	        	//textView.setText("No network connection available.");
//	        
//		}
		}
		if(v == btnSetting)
		{
			allowRun = false;
			refresh = false;
			Intent intent = new Intent();
			intent.setClass(mainpage.this,userInfoActivity.class);
			startActivity(intent);
			
		}
		if(v == btnExit)
		{
			sqlite.insert(user);
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
	        
		}}
		if(v == btnq)
		{
			
			sqlite.query(user);
			sqlite.delete();
			sqlite.closeDatabase();
		}
	}
	
}
public void onActivityResult(int requestCode,int resultCode,Intent data){
	switch(requestCode){
	case 0:
	switch(resultCode){
	case RESULT_OK:
		Bundle bundle=data.getExtras();
		userInfo = (userInfo)data.getSerializableExtra("userInfo");
		saveInSharedPreferences();
		Log.v("sdfasdfsad","dfsadf"+userInfo.targetRunStep);
	}
	break;
	case 1:
		switch(resultCode){
		case RESULT_OK:
			Bundle bundle=data.getExtras();
			
		}
	break;
	}
}
private void setView(user user){
	int rt,wt,rs,ws,trt,twt,trs,tws,e,te;
	//userInfo userInfo = new userInfo();
	//userInfo.initial();
	rt = user.runTime/60;
	wt = user.walkTime/60;
	rs = user.runStep;
	ws = (int)user.walkStep;
	e = (int)user.energy/1000;
	trt = userInfo.targetRunTime;
	twt = userInfo.targetWalkTime;
	trs = userInfo.targetRunStep;
	tws = userInfo.targetWalkStep;
	te = userInfo.targetEnergy;
	TextView tvWalkTime, tvRunStep, tvWalkStep,tvEnergy,tvTargetEnergy,tvRunTime,tvTargetWalkTime, tvTargetRunStep, tvTargetWalkStep,tvTargetRunTime;
	tvWalkTime = (TextView)this.findViewById(R.id.tvWalkTime);
	tvRunTime = (TextView)this.findViewById(R.id.tvRunTime);
	tvWalkStep = (TextView)this.findViewById(R.id.tvWalkStep);
	tvRunStep = (TextView)this.findViewById(R.id.tvRunStep);
	tvEnergy = (TextView)this.findViewById(R.id.tvEnergy);
	tvTargetWalkTime = (TextView)this.findViewById(R.id.tvTargetWalkTime);
	tvTargetRunTime = (TextView)this.findViewById(R.id.tvTargetRunTime);
	tvTargetWalkStep = (TextView)this.findViewById(R.id.tvTargetWalkStep);
	tvTargetRunStep = (TextView)this.findViewById(R.id.tvTargetRunStep);
	tvTargetEnergy = (TextView)this.findViewById(R.id.tvTargetEnergy);
	tvRunTime.setText("跑步"+rt+"分");
	tvWalkTime.setText("步行"+wt+"分");
	tvRunStep.setText("跑步"+rs+"步");
	tvWalkStep.setText("步行"+ws+"步");
	tvEnergy.setText("能量"+e+"千卡");
	tvTargetRunTime.setText("跑步"+trt+"分");
	tvTargetWalkTime.setText("步行"+twt+"分");
	tvTargetRunStep.setText("跑步"+trs+"步");
	tvTargetWalkStep.setText("步行"+tws+"步");
	tvTargetEnergy.setText("能量"+te+"千卡");
	if(user.sitTime > 60*60)
		{
		Toast.makeText(this,"您坐的时间超过一个小时，请起来走走",Toast.LENGTH_SHORT).show();
		user.sitTime = 0;
		}
	
}
class refreshThread extends Thread{
	private Thread thread;
	public refreshThread(){
		thread = new Thread(new refreshrunnable());
	}
	public void Stop() {
		//refresh = false;			
		//thread.stop();
		//State bb = thread.getState();
		}
	
	public void Start() {
		refresh = true;
		State aa = thread.getState();
		if(aa==State.NEW){
		thread.start();
		}else thread.resume();
	}
	private class refreshrunnable implements Runnable{
		public void run(){
			while(refresh){
				//setView(user);
				try {
					//setView(user);
					status = user.showStatus();
					mHandler.sendEmptyMessage(02);
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
class ConnectedThread extends Thread {
	private InputStream inStream = null;// 蓝牙数据输入流
	private long wait;
	private Thread thread;
	private BluetoothServerSocket serverSocket=null;
	public BluetoothSocket socket = null;
	public ConnectedThread() {
		isRecording = false;
		this.wait=100;
		thread =new Thread(new ReadRunnable());
	}

	public void Stop() {
		isRecording = false;			
		//thread.stop();
		//State bb = thread.getState();
		}
	
	public void Start() {
		isRecording = true;
		State aa = thread.getState();
		if(aa==State.NEW){
		thread.start();
		}else thread.resume();
	}
	int i=0;
private class ReadRunnable implements Runnable {
	//public boolean isRecording = true;
	public void run() {
		if(allowRun){
		while(true)
		{
		int i = 0;
		try {
			 if (testBlueTooth.btSocket != null) 
				 break;
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		OutputStream outStream = null;
		//控制模块
		try {					
			fout = new FileOutputStream("/sdcard/testout.txt");
		} catch (IOException e) {
		}
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
		while (allowRun) {
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
				if (len > 0) 
				{
					//读编码
		            readnum =(int) (temp[0] & 0xff)<<8 | (temp[1] & 0xff);
		            if(4095 - readnum >= 0)
		            {
		            int cal= 300*(4095-readnum)/readnum;
		            user.getF(cal);
		            fout.write((Integer.toString(cal)+"\n") .getBytes("utf-8"));
		            Log.v("the num read is","::"+(4095 - readnum));}
		           // mHandler.obtainMessage(01,len,-1,tmp).sendToTarget();
		            
				}			             
	             Thread.sleep(100);// 延时一定时间缓冲数据
	             //isRecording = false;
	             //mHandler.obtainMessage(01,len,-1,int2char(4095 - readnum)).sendToTarget();
				}catch (Exception e) {
					e.printStackTrace();
					// TODO Auto-generated catch block
					//mHandler.sendEmptyMessage(00);
				}				
			
			}
		}
			}
		}
	}	
}
class OnDayChangeThread extends Thread{
	private Thread thread;
	boolean enable = true;
	public OnDayChangeThread(){
		thread = new Thread(new dayChangeRunnable());
	}
	public void Stop() {
		enable = false;			
		//thread.stop();
		//State bb = thread.getState();
		}
	
	public void Start() {
		enable = true;
		State aa1 = thread.getState();
		if(aa1==State.NEW){
		thread.start();
		}else thread.resume();
	}
	private class dayChangeRunnable implements Runnable{
		public void run(){
			while(enable){
				try {
				Calendar c = Calendar.getInstance();
				int date = c.get(Calendar.DATE);
				//setView(user);
				if(user.currentDate == date)
					enable=true;
				else
					onDateChange();
				Thread.sleep(60*60*1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}

 private class MyHandler extends Handler{ 
    	@Override		    
        public void dispatchMessage(Message msg) { 
    		switch(msg.what){
    		case 00:
    			//isRecording = false;
    			//_txtRead.setText("");
    			tvStatus.setText("未连接");
    			setView(user);
    			break;
    		case 01:
    			//String info=(String) msg.obj;
    			//tvStatus.setText(info);
    			break;  
    		case 02:
    			setView(user);
    			tvStatus.setText(status);
    			break;

            default:	            
                break;
    		}
    		}
 }
 public void sendBluetoothMessage(String message) {
		
		//控制模块
		try {
			outPutStream = testBlueTooth.btSocket.getOutputStream();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			Toast.makeText(getApplicationContext(), " Output stream creation failed.", Toast.LENGTH_SHORT);
			
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
         tvStatus.setText(result);
         //AsyncImageLoader.setImageViewFromUrl(imageUrl, imView);
    }
 }
 private String translateByUrl(String myurl) throws IOException {
     InputStream is = null;

     try {
     	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	         params.add(new BasicNameValuePair("name", "Klaus"));
	         params.add(new BasicNameValuePair("date", Integer.toString(user.currentDate)));
	         params.add(new BasicNameValuePair("status", user.formStatusString()));
	         params.add(new BasicNameValuePair("runtime", Integer.toString(user.runTime)));
	         params.add(new BasicNameValuePair("walktime", Integer.toString(user.walkTime)));
	         params.add(new BasicNameValuePair("runstep", Integer.toString(user.runStep)));
	         params.add(new BasicNameValuePair("walkstep", Integer.toString((int)user.walkStep)));
	         params.add(new BasicNameValuePair("weight", Integer.toString(user.weight)));
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
 public int getMessage() {
		try {					
			inStream = testBlueTooth.btSocket.getInputStream();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), " input stream creation failed.", Toast.LENGTH_SHORT);
			
		}	
		String  encodeType ="GBK";
		byte[] msgBuffer = null;
		String message = "D";
		int readnum = 0;
		try {
			msgBuffer = message.getBytes(encodeType);//编码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			Log.e("write", "Exception during write encoding GBK ", e1);
		}				                      
			byte[] temp = new byte[10];
			String tmp = "";
			if (inStream!= null) {
			try{
				int len = inStream.read(temp,0,10);
				Log.v("string","::"+temp);
				Log.v("length","::"+len);
				byte[] btBuf = new byte[len];
				System.arraycopy(temp, 0, btBuf, 0, btBuf.length);	
				String readStr1 = new String(btBuf,encodeType);
				Log.v("string","::"+readStr1);
				if (len > 0) 
				{
					//读编码
					readnum = (int)(temp[0]<<8 | temp[1]);
		            Log.v("the num read is","::"+readnum);
		           // mHandler.obtainMessage(01,len,-1,tmp).sendToTarget();
		            
				}			             
	             Thread.sleep(1000);// 延时一定时间缓冲数据
	             //isRecording = false;
				}catch (Exception e) {
					// TODO Auto-generated catch block
					mHandler.sendEmptyMessage(00);
				}				
			
			}
			return readnum;
			
}
 public void sendMessage(String message) {
		
		OutputStream outStream = null;
		//控制模块
		try {
			outStream = testBlueTooth.btSocket.getOutputStream();	
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), " Output stream creation failed.", Toast.LENGTH_SHORT);
			
		}
		String  encodeType ="GBK";
		byte[] msgBuffer = null;		
		try {
			msgBuffer = message.getBytes(encodeType);//编码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			Log.e("write", "Exception during write encoding GBK ", e1);
		}		
			try {
				outStream.write(msgBuffer);				
				setTitle("成功发送指令:"+message);
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "发送数据失败", Toast.LENGTH_SHORT);
				
			}
			
		}
 //保存用户的设置信息，包括运动目标和用户体重校验因子
 public void saveInSharedPreferences(){
		SharedPreferences formStore;
		formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = formStore.edit();
		editor.putInt("targetWalkStep",userInfo.targetWalkStep);
		editor.putInt("targetRunStep",userInfo.targetRunStep);
		editor.putInt("targetWalkTime",userInfo.targetWalkTime);
		editor.putInt("targetRunTime",userInfo.targetRunTime);
		editor.putFloat("adjustFactor", userInfo.adjustFactor);
		editor.commit();
	}
 public void retrieveSharedPreference(){
	    SharedPreferences formStore;
		formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
		userInfo.targetWalkStep = formStore.getInt("targetWalkStep",0);
		userInfo.targetRunStep = formStore.getInt("targetRunStep",0);
		userInfo.targetWalkTime = formStore.getInt("targetWalkTime",0);
		userInfo.targetRunTime = formStore.getInt("targetRunTime",0);
		userInfo.targetEnergy = formStore.getInt("targetEnergy",0);
 }
 //保存用户的临时数据，包括运动状态序列和运动情况
 public void saveUserData(){
		SharedPreferences formStore;
		formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = formStore.edit();
		editor.putString("statusTrace", Arrays.toString(user.statusTrace));
		editor.putInt("walkStep",(int)user.walkStep);
		editor.putInt("runStep",user.runStep);
		editor.putInt("walkTime",user.walkTime);
		editor.putInt("runTime",user.runTime);
		editor.putInt("date", user.currentDate);
		editor.commit();
	}
public void retrieveUserData(){
	    SharedPreferences formStore;
		formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
		String s;
		s = formStore.getString("statusTrace",null);
		//Log.v("string",s);
		for(int i = 0; i < s.length()/3;i++)
			user.statusTrace[i] = s.charAt(3*i+1) - '0';
		user.walkStep = formStore.getInt("walkStep",0);
		user.runStep = formStore.getInt("runStep",0);
		user.walkTime = formStore.getInt("walkTime",0);
		user.runTime = formStore.getInt("runTime",0);
}
public void onExit(){
	sqlite.createOrOpenDatabase();
	sqlite.insert(user);
}
public void onDateChange(){
	sqlite.createOrOpenDatabase();
	sqlite.insert(user);
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
private char[] int2char(int a){
	char[] c = new char[4];
	int i = 0;
	for(int j = 0;j < 4; j++)
	{
		c[j] = '0';
	}
	while(a/10 != 0 && a%10 != 0)
	{
		c[3 - i] =(char)(a % 10);
		a = a/10;
		i++;
	}
	return c ;
}
}

