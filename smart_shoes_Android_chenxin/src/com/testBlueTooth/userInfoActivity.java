package com.testBlueTooth;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class userInfoActivity extends Activity {
String inputWeight="10";//输入的重量
int getWeight=30;//压力数值
Button btnAdjust,btnRefresh,btnInputWeight,btnGoal,btnBack,btnConnect;
TextView tvWeight;
ImageView imgView;
private Handler mHandler;
private OutputStream outStream = null;
private InputStream inStream = null;
userInfo user = new userInfo();
String imgUrl = "";
String username = "klaus";
//sqliteUserInfo sqliteUserInfo = new sqliteUserInfo();
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.userinfo);
	btnAdjust = (Button) this.findViewById(R.id.btnAdjust);
    btnAdjust.setOnClickListener(new ClickEvent());
//    btnRefresh = (Button) this.findViewById(R.id.btnRefresh);
//    btnRefresh.setOnClickListener(new ClickEvent());
    btnConnect = (Button) this.findViewById(R.id.btnConnect);
    btnConnect.setOnClickListener(new ClickEvent());
//    btnInputWeight = (Button) this.findViewById(R.id.btnInputWeight);
//    btnInputWeight.setOnClickListener(new ClickEvent());
    btnGoal = (Button) this.findViewById(R.id.btnGoal);
    btnGoal.setOnClickListener(new ClickEvent());
    btnBack = (Button) this.findViewById(R.id.btnBack);
    btnBack.setOnClickListener(new ClickEvent());
    tvWeight = (TextView)this.findViewById(R.id.tvWeight);
    //imgView = (ImageView)this.findViewById(R.id.imgChart);
    retrieveSharedPreferences();
    tvWeight.setText(""+(int)user.weight+"Kg");
    //sqliteUserInfo.createOrOpenDatabase();
    //sendMessage("D");
    
//	btnAdjust =(Button) this.findViewById(R.id.btnAdjust);
}
private class ClickEvent implements View.OnClickListener{

	private InputStream inStream = null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnAdjust)
		{
			inputTitleDialog();
//			mHandler = new MyHandler();
//			mHandler.sendEmptyMessage(02);
//			OutputStream outStream = null;
//			//控制模块
//			
		}
		if(v == btnConnect)
		{
			Intent intent = new Intent();
			intent.setClass(userInfoActivity.this,testBlueTooth.class);
			startActivityForResult(intent,0);
			
		}
		if(v == btnGoal)
		{
			//sqliteUserInfo.delete(0);
			Intent intent=getIntent();
			intent.setClass(userInfoActivity.this,inputGoalActivity.class);
			//Bundle bundle=intent.getExtras();
			startActivity(intent);
//			startActivity(intent);
//			user.targetWalkTime = Integer.parseInt(bundle.getString("tWalkTime"));
//			user.targetRunTime = Integer.parseInt(bundle.getString("tRunTime"));
//			user.targetWalkStep = Integer.parseInt(bundle.getString("tWalkStep"));
//			user.targetRunStep = Integer.parseInt(bundle.getString("tRunStep"));
//			Log.e("dfasdfsad","ok"+user.targetWalkTime);
		}
		if(v == btnRefresh)
		{
//			userInfoActivity.this.sendMessage("W");
//			try {
//		        Thread.sleep(100);//括号里面的5000代表5000毫秒，也就是5秒，可以该成你需要的时间
//		} catch (InterruptedException e) {
//		        e.printStackTrace();
//		}
//			getMessage();
			sendAndReceive();
			Log.d("adjustfactor","ad"+userInfo.adjustFactor);
			user.weight = user.adjustFactor * getWeight;
			Log.d("adjustweight","ad"+user.weight);
			mHandler = new MyHandler();
			mHandler.sendEmptyMessage(02);
			saveInSharedPreferences();
			//sqliteUserInfo.insertWeight(username, user.weight);
			//userInfo.adjustFactor = (float)Integer.parseInt(inputWeight)/getWeight;
		}
		if(v == btnBack)
		{
			mainpage.allowRun = true;
			mainpage.refresh = true;
//			formImg();
//		    //AsyncImageLoader.setImageViewFromUrl(imgUrl, imgView);
//		    new DownloadWebpageTask().execute(imgUrl);
			Intent intent = new Intent();
			intent.setClass(userInfoActivity.this,mainpage.class);
			startActivity(intent);
		}
	}
	
	
}
public void onActivityResult(int requestCode,int resultCode,Intent data){
	switch(requestCode){
	case 0:
	switch(resultCode){
	case RESULT_OK:
		Bundle bundle=data.getExtras();
		user.targetWalkTime = Integer.parseInt(bundle.getString("tWalkTime"));
		user.targetRunTime = Integer.parseInt(bundle.getString("tRunTime"));
		user.targetWalkStep = Integer.parseInt(bundle.getString("tWalkStep"));
		user.targetRunStep = Integer.parseInt(bundle.getString("tRunStep"));
		Log.e("dfasdfsad","ok"+user.targetWalkTime);
	}
	break;
	case 1:
		switch(resultCode){
		case RESULT_OK:
			Bundle bundle=data.getExtras();
			user.targetWalkTime = Integer.parseInt(bundle.getString("tWalkTime"));
			user.targetRunTime = Integer.parseInt(bundle.getString("tRunTime"));
			user.targetWalkStep = Integer.parseInt(bundle.getString("tWalkStep"));
			user.targetRunStep = Integer.parseInt(bundle.getString("tRunStep"));
			Log.e("dfasdfsad","ok"+user.targetWalkTime);
		}
	break;
	}
}
private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
          
        return "s";
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        AsyncImageLoader.setImageViewFromUrl(imgUrl, imgView);
   }
}
 
private class MyHandler extends Handler{ 
	@Override		    
    public void dispatchMessage(Message msg) { 
		switch(msg.what){
		case 02:
			tvWeight.setText(""+(int)user.weight+"Kg");//setView(user);
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
private void getMessage(){
	        
	       // Log.d("readnum","ad");
			try {					
				inStream = testBlueTooth.btSocket.getInputStream();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), " input stream creation failed.", Toast.LENGTH_SHORT);
				
			}							                      
			byte[] temp = new byte[2];
			int readnum = 0;
			Log.d("readnum","ad"+readnum);
			if (inStream!= null) {
			try{
				int len = inStream.read(temp,0,5);	
				Log.d("len","ad"+len);
				readnum =(int) (temp[0] & 0xff)<<8 | (temp[1] & 0xff);
		        getWeight = 4095 - readnum;
		        Log.d("getWeight","ad"+getWeight);
				}catch (Exception e) {
					e.printStackTrace();
				}				
			}
		
}
public String string2Int(String s)
{
int i = 0;
String tmp = "";
int len = s.length();
	while(!s.substring(i,i+1).equals("\n") && !s.substring(i,i+1).equals("\r") && i < len) 
	{
		tmp = tmp + s.substring(i,i+1);
		i++;
	}
return tmp;
}
private void inputTitleDialog() {

    final EditText inputServer = new EditText(this);
    inputServer.setFocusable(true);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("您的体重").setView(inputServer).setNegativeButton(
            "取消", null);
    builder.setPositiveButton("确定",
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                   inputWeight = inputServer.getText().toString();
                   sendAndReceive();
                   user.adjustFactor = (float)Float.parseFloat(inputWeight)/getWeight;
                   user.weight=Float.parseFloat(inputWeight);
                   Log.d("input Weight","ad"+user.weight);
                   Log.d("adjustfactor","ad"+user.adjustFactor);
                   saveInSharedPreferences();
                   mHandler = new MyHandler();
               	   mHandler.sendEmptyMessage(02);
                }
            });
    builder.show();
    int j = 0;
    j++;
}
public void saveInSharedPreferences(){
	SharedPreferences formStore;
	formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
	SharedPreferences.Editor editor = formStore.edit();
	editor.putFloat("weight",user.weight);
	editor.putFloat("adjustFactor",user.adjustFactor);
	editor.commit();
}
public void retrieveSharedPreferences(){
 SharedPreferences formStore;
	formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
	user.weight = formStore.getFloat("weight",0);
	user.adjustFactor = formStore.getFloat("adjustFactor",0);

}
public void formImg(){
	imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=scale+chart&chts=00A5C6,20,c&chma=30,20,0,0&chds=40,90&chxt=y&chxr=0,40,90,10&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,999999,0.2,BBBBBB,0.2,DDDDDD,0.2,FFFFFF,0.2&chd=t:";
//	for (int i = 0; i < 4; i++) {   
//	     String data = Integer.toString(i*20);
//	     if (i!=0) {
//	    	 imgUrl+=",";
//	     }
//	     imgUrl+=data;
//	 }
	//sqliteUserInfo.delete(0);
	//sqliteUserInfo.insertWeight(username,user.weight);
	//String s = sqliteUserInfo.queryWeight();
	//Log.e("answer",s);
	//imgUrl += s;
}

public void sendAndReceive() {
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
}