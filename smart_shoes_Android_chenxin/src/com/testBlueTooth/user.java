package com.testBlueTooth;

import java.util.Calendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.content.SharedPreferences;

public class user {
int	weight=0;
final int num = 20;
final int statusNum = 5;
public int stepNum = 0;
public String username; 
public float walkStep=0;
public int runStep=0;
public int currentDate = 0;
public int statusTrace[] =new int [24*60*60];
public int statusByMinute[] = new int[24*60];
public int fTrace[] = new int [24*60*60*10];
public int fSerial[] = new int[num];
public String statusString = "";
public int statusArray[] = new int [24*60];
public int walkTime = 0;
public int runTime = 0;
public int sitTime = 0;
public int status = -1;
public float energy = 0;
int statusSerial[] = new int[statusNum];
int k = 0;//k is the current indicator of status string
private int fSerialNum = 0;
private int peakThreshold = 10;//to judege whether it is a peak by grad,the num enable mistakes
private int sitThreshold = 1500;
private int walkThreshold = 3500;
private int runThreshold = 3800;
boolean sync = false;
int indicateCurrents,indicateInitials,indicateCurrentf,indicateInitialf;
public void initial(){
	for(int i = 0; i < 24*60*60*10; i++)
		fTrace[i] = 0;
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int date = c.get(Calendar.DATE);
	int hour = c.get(Calendar.HOUR_OF_DAY);
	int minute = c.get(Calendar.MINUTE);
	int second = c.get(Calendar.SECOND);
	int flag =0;
	indicateCurrents = hour*60*60 + minute * 60 + second;
	indicateInitials = indicateCurrents;
	indicateInitialf = indicateInitials * 10;
	indicateCurrentf = indicateInitialf;
	sync = true;
}
public void initialUser(){
	Calendar c = Calendar.getInstance();
	int date = c.get(Calendar.DATE);
	for(int i = 0; i < 24*60*60; i++)
		statusTrace[i] = 0;
	walkTime = 0;
	runTime = 0;
	walkStep = 0;
	runStep = 0;
    currentDate = date;
}
private int getStatus(int curr){
	boolean sitFlag=true,standFlag=true,walkFlag=true,runFlag=true;
	 for(int i = 0; i < num; i++)
	 {
		 if(fTrace[curr - i] > sitThreshold)
			 sitFlag = false;
		 if(fTrace[curr - i] < sitThreshold)
			 standFlag = false;
	 }
	 if(sitFlag == false && standFlag == false)
	 {
		 getEnergy(curr);
		 if(checkrun(curr))
			 walkFlag = false;
	 }
	 int p = 0;
	 int low = 0, high = 0;
	 while(p < 10)
	 {
		 if(fTrace[curr - p] < sitThreshold && p<10)
		 {
			 while(fTrace[curr - p] < sitThreshold && p<10)
		 
			 {
				 p++;
			 }
			 low ++;
		 }
		 if(fTrace[curr - p] > sitThreshold && p<10)
		 {
			 while(fTrace[curr - p] > sitThreshold && p<10)
			 {
				 p++;
			 }
			 high ++;
		 }
	 }
	 float step = low>high? high:low;
	 //step = getStep(curr,20) - getStep(curr - 10, 10);
	 step = getStep(curr);
	 if(sitFlag) 
		 {
			 sitTime++;
			 return 1;
		 }
	 if(standFlag) return 2;
	 if(walkFlag) 
		 {
		     walkTime++;
		     walkStep+=step;
		 	 return 3;
		 }
	 if(runFlag) 
		 {
		     runTime++;
		     runStep+=step;
		     return 4;
		 }
	 return 1;
}
private float getStep(int curr){
	int num = 5;
	boolean sitFlag=true,standFlag=true,walkFlag=true,runFlag=true;
	int n = 0;
	 for(int i = 0; i < num; i++)
	 {
		 if((fTrace[curr - i] - sitThreshold)*(fTrace[curr - i-1] - sitThreshold) < 0)
			 n++;
	 }
     return n;
}

public String showStatus(){
	int status=getStatus(indicateCurrentf - 2);
	Log.v("status","::"+status);
	statusTrace[indicateCurrents++] = status;
	if(status == 1) return "坐";
	if(status == 2) return "站立";
	if(status == 3) return "步行";
	if(status == 4) return "跑步";
	else return "false";
}
private boolean checkStatus(int indicateCurrentf2) {
	// TODO Auto-generated method stub
	return false;
}
public void string2Int(String s)
{
int i = 0;
String tmp = "";
int len = s.length();
s = s + "\n\r";
while(i < len)
	{
	tmp = "";
	while(!s.substring(i,i+1).equals("\n") && !s.substring(i,i+1).equals("\r") && i < len) 
	{
		tmp = tmp + s.substring(i,i+1);
		i++;
	}
	this.fTrace[this.indicateCurrentf++] = Integer.parseInt(tmp);
	//count ++;
	i = i + 2;
	}
}

//public void getString(String s){
//	string2Int(s);//Translate String to int and refresh the Array of F.
//	while(indicateCurrentf - indicateInitialf >= 10)
//	{
//		for(int i = 0; i < 10; i++)
//		{
//		    fSerial[i] = fTrace[this.indicateInitialf + i];	
//		}
//		//monitorStep();
//		indicateInitialf += 10;
//		//this.statusSerial[k] = this.getStatus();
//		statusTrace[indicateCurrents++] = this.statusSerial[k];
//		 k = (k + 1) >= statusNum? k+1-statusNum:k+1;
//	}
//	return;
//}

public void getF(int f){
	this.fTrace[this.indicateCurrentf++] = f;
}
//private int checkStep(int tmpCurr)
//{
//	int flag = -1;
//	for(int i = tmpCurr - num; i < tmpCurr-3; i++)
//		if(checkpoint(i))
//		{
//			if(fTrace[i] > runThreshold)
//				{
//					runStep++;
//					flag = 4;
//					return flag;
//				}
//			
//			else 
//				{
//					walkStep++;
//					flag = 3;
//					return flag;
//				}
//		}
//	return flag;
//}
private boolean checkrun(int tmpCurr){
	int tmp[] =  new int[num];
	int max = 0;
	for(int i = 0;i<num;i++)
		{
		tmp[i] = fTrace[tmpCurr-i];
		if(max <= tmp[i])
			max = tmp[i];
		}
	int diff = 0;
	for(int i=0; i<num-1; i++)
		diff += Math.abs(tmp[i+1]-tmp[i]);
	diff = diff/(num-1);
	if(diff > 1000 & max >5500)
		return true;
	else 
		return false;
}
private void getEnergy(int tmpCurr){
	int max = 0;
	for(int i = 0;i < 10; i++)
		if(fTrace[tmpCurr-i] > max)
			max = fTrace[tmpCurr - i];
	double tmpEnergy = 0.00478*(max/1000)*(max/1000) - 0.1656*(max/1000) + 0.90272;
	energy += tmpEnergy*67.5;
	return;
}
public String formStatusString(){
	for(int i = 0; i < 24*60; i++)
		statusArray[i] = 0;
	String s = "";
	int[] count = new int[5];
	for(int i = 0; i < 24*60; i++)
	{
		for(int offset = 0; offset < 60; offset++)
			count[this.statusTrace[i*60 + offset]]++;
	    int max=0, maxj = 0;
		for(int j = 0; j < 5; j++)
	    	if(count[j] > max)
	    		{
	    		max = count[j];
	    		maxj = j;
	    		}
		statusArray[i] = maxj;
		//s += Integer.toString(maxj);
	}
	for(int i = 0; i < 24*60; i++)
		s += Integer.toString(statusArray[i]);
	return s;
}

public void retrieveStatusArray(){
	for(int i = 0; i < 24*60; i++)
		statusArray[i] = statusString.charAt(i);
	return;
}
}
