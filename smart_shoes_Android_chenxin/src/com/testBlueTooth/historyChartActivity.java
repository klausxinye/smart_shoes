package com.testBlueTooth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class historyChartActivity extends Activity {

	Button btnWalkTime,btnRunTime,btnWalkStep,btnRunStep;
	String walkTime,runTime,walkStep,runStep;
	Button btnBack;
	ImageView imgChart;
	//sqliteUserInfo sqliteUserInfo=new sqliteUserInfo();
	String imgUrl = "";
	sqlite sqlite =  new sqlite();
	String username = "klaus";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historychart);
	    imgChart = (ImageView) this.findViewById(R.id.imgChart);
		btnWalkTime = (Button) this.findViewById(R.id.btnWalkTime);
		btnWalkTime.setOnClickListener(new ClickEvent());
		btnRunTime = (Button) this.findViewById(R.id.btnRunTime);
		btnRunTime.setOnClickListener(new ClickEvent());
		btnWalkStep = (Button) this.findViewById(R.id.btnWalkStep);
		btnWalkStep.setOnClickListener(new ClickEvent());
		btnRunStep = (Button) this.findViewById(R.id.btnRunStep);
		btnRunStep.setOnClickListener(new ClickEvent());
		sqlite.createOrOpenDatabase();
		formChart(1);
        new DownloadWebpageTask().execute(imgUrl);
		//System.out.println(loadFromSDFile("testfile.txt"));
		//System.Pause("");
	}
	private class ClickEvent implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnRunTime)
			{
            formChart(1);
            new DownloadWebpageTask().execute(imgUrl);
			}
			if(v == btnWalkTime)
			{
            formChart(2);
            new DownloadWebpageTask().execute(imgUrl);
			}
			if(v == btnRunStep)
			{
            formChart(3);
            new DownloadWebpageTask().execute(imgUrl);
			}
			if(v == btnWalkStep)
			{
            formChart(4);
            new DownloadWebpageTask().execute(imgUrl);
			}
			
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
	        AsyncImageLoader.setImageViewFromUrl(imgUrl, imgChart);
	   }
	}
    public void formChart(int flag){
    	if(flag == 1)
    	{
//    		sqlite.delete();
//    		mainpage.user.runTime = 7000;
//    		mainpage.user.walkTime = 6000;
//    		mainpage.user.runStep = 5000;
//    		mainpage.user.walkStep = 4000;
//    		sqlite.insert(mainpage.user);
//    		mainpage.user.runTime = 6000;
//    		mainpage.user.walkTime = 7500;
//    		mainpage.user.runStep = 5500;
//    		mainpage.user.walkStep = 5000;
//    		sqlite.insert(mainpage.user);
//    		mainpage.user.runTime = 8000;
//    		mainpage.user.walkTime = 5500;
//    		mainpage.user.runStep = 5000;
//    		mainpage.user.walkStep = 6000;
//    		sqlite.insert(mainpage.user);
    	imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=Walking+Time/min&chts=00A5C6,20,c&chma=30,20,0,0&chds=0,180&chxt=y&chxr=0,0,180,30&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,DDDDAD,0.2,DDDDCD,0.2,DDDDDD,0.2,DDEEEE,0.2&chd=t:";
    	String s = sqlite.queryHistory(flag, username);
    	s="50,70,65";
        Log.v("output","output"+s);
        imgUrl += s;
    	}
    	if(flag == 2)
    	{
    	imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=Running+Time/min&chts=00A5C6,20,c&chma=30,20,0,0&chds=0,180&chxt=y&chxr=0,0,180,30&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,DDDDAD,0.2,DDDDCD,0.2,DDDDDD,0.2,DDEEEE,0.2&chd=t:";
        String s = sqlite.queryHistory(flag, username);
        s = "73,75,74";
        Log.v("output","output"+s);
        imgUrl += s;
    	}
    	if(flag == 4)
    	{
    	imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=Running+Step&chts=00A5C6,20,c&chma=30,20,0,0&chds=0,10000&chxt=y&chxr=0,0,10000,2000&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,DDDDAD,0.2,DDDDCD,0.2,DDDDDD,0.2,DDEEEE,0.2&chd=t:";
        String s = sqlite.queryHistory(flag, username);
        s="3000,4000,4500";
        Log.v("output","output"+s);
        imgUrl += s;
    	}
    	if(flag == 3)
    	{
    	imgUrl = "http://chart.googleapis.com/chart?chs=600x400&cht=lc&chof=png&chtt=Walking+Step&chts=00A5C6,20,c&chma=30,20,0,0&chds=0,10000&chxt=y&chxr=0,0,10000,2000&chxs=1,0000dd,13,-1,t,FF0000&chf=c,ls,90,DDDDAD,0.2,DDDDCD,0.2,DDDDDD,0.2,DDEEEE,0.2&chd=t:";
        String s = sqlite.queryHistory(flag, username);
        s="4500,4700,6000";
        Log.v("output","output"+s);
        imgUrl += s;
    	}
    	return;
    }
}


