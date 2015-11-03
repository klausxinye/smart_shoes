package com.testBlueTooth;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sqliteWeight {
	SQLiteDatabase sld;
    public void createOrOpenDatabase()
    {
    	try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.testBlueTooth/mydb1", //���ݿ�����·��
	    			null, 								//CursorFactory
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //��д�����������򴴽�
	    	);
	    	appendMessage("���ݿ��Ѿ��ɹ��򿪣�");	
	    	String sql="create table if not exists weight(username varchar(20),weight float)";
	    	sld.execSQL(sql);
	    	//appendMessage("student�Ѿ��ɹ�������");   		
    	}
    	catch(Exception e)
    	{
    		//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
    	}
    }
    
    //�ر����ݿ�ķ���
    public void closeDatabase()
    {
    	try
    	{
	    	sld.close();    
	    	//appendMessage("���ݿ��Ѿ��ɹ��رգ�");    		
    	}
		catch(Exception e)
		{
			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }
    
    //�����¼�ķ���
    public void insertWeight(String username,float weight)
    {
    	try
    	{
        	String sql="insert into weight values('klaus',"+weight+")";
        	sld.execSQL(sql);
        	//appendMessage("�ɹ�����һ����¼��");
        	Log.v("insert Successfully","yes");
    	}
		catch(Exception e)
		{
			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }
    
    //ɾ����¼�ķ���
    public void delete()
    {
    	try
    	{
//        	String sql="delete from student;";
//        	sld.execSQL(sql);
//            sld.delete("student", null, null);
            String DROP_TABLE = "DROP TABLE IF EXISTS weight";
            sld.execSQL(DROP_TABLE);
        	//appendMessage("�ɹ�ɾ�����м�¼��");
    	}
		catch(Exception e)
		{
			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }
    //��ѯ�ķ���
    public String queryWeight()
    {
    	String s="";
    	int i = 0;
    	try
    	{
        	String sql="select * from weight where username == ?";    	
        	Cursor cur=sld.rawQuery(sql, new String[]{"klaus"});
        	//appendMessage("ѧ��\t\t����\t\t����\t�༶");
        	while(cur.moveToNext())
        	{
        		if(i!=0)
        			s += ",";
        		float weight=cur.getFloat(1);
        		s += (int)weight;
        		i++;
        		//Log.d("Age:","sdf"+sage);
        		//appendMessage(sno+"\t"+sname+"\t\t"+sage+"\t"+sclass);
        	}
        	cur.close();		
    	}
		catch(Exception e)
		{
			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    	return s;
    }
    
    //���ı���������ı�
    public void appendMessage(String msg)
    {
    	//EditText et=(EditText)this.findViewById(R.id.EditText01);
    	//et.append(msg+"\n");
    }
}

//package com.testBlueTooth;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//import android.widget.Toast;
//
//public class sqliteUserInfo {
//	SQLiteDatabase sld;
//	public void createOrOpenDatabase(){
//		try{
//			sld=SQLiteDatabase.openDatabase("/data/data/com.testBlueTooth/mydb", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
//		    String sql="create table if not exists weight(username varchar(20),weight integer)";
//		    //String sql1="create table if not exists userhistory(username1 varchar(20),integer Walktime,integet RunTime,integer WalkStep,integer RunStep)";
//			sld.execSQL(sql);
//			//sld.execSQL(sql1);
//			//appendMessage("���ݿ��Ѿ��ɹ�����");
//			Log.d("create","create successfully");
//		}catch(Exception e){
//			Log.d("create",e.toString());
//			//Toast.makeText(this,"���ݿ����:"+e.toString(),Toast.LENGTH_SHORT).show();
//		}
//	}
//	public void closeDatabase(){
//		try{
//			sld.close();
//			Log.d("create","close successfully");
//		}catch(Exception e){
//			//Toast.makeText(this, "���ݿ����"+e.toString(),Toast.LENGTH_SHORT).show();
//			Log.d("close",e.toString());
//		}
//	}
//	public void insertWeight(){
//		try{
//			String sql = "insert into weight('klaus',12)";
//			Log.d("insertIntoDataBase","issert successfully");
//		}catch(Exception e){
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
//			Log.d("insert",e.toString());
//		}
////		try{
////			String sql = "insert into userweight('"+username+"',"+weight+")";
////			Log.d("insertIntoDataBase","issert successfully");
////		}catch(Exception e){
////			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
////			Log.d("insert",e.toString());
////		}
//	}
//	public void insertStatus(user user){
//		try{
//			
//			//String s=int2Str(user.statusTrace);
//			//String s1;
//			//s = int2Str(str2Int(s));
//			//String sql="insert into user values('"+user.username+"',"+user.currentDate+")";
//			String sql="insert into userstatus('"+user.username+"',"+user.runtime+","+user.walktime+","+user.runStep+","+user.walkStep+")";
//			sld.execSQL(sql);
//			Log.d("create","issert successfully");
//		}catch(Exception e){
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
//			Log.d("insert",e.toString());
//		}
//	}
//	public String queryWeight(){
//		String s = "";
//		Log.d("hehe:","I am in");
//		try
//    	{
//        	String sql="select * from weight where username == ?";    	
//        	Cursor cur=sld.rawQuery(sql, new String[]{"klaus"});
//        	//appendMessage("ѧ��\t\t����\t\t����\t�༶");
//        	while(cur.moveToNext())
//        	{
//        		int sage=cur.getInt(1);
//        		Log.d("queryweight","queryweight successfully");
//        		s +=sage;
//        		//appendMessage(sno+"\t"+sname+"\t\t"+sage+"\t"+sclass);
//        	}
//        	cur.close();
//    	}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
//		}
//		Log.d("s is:",s);
//return s;
////		String weight = "..";
////		int i = 0;
////		Log.d("hehe:","I am in");
////		try{
////			String sql="select * from userweight where username == ? ";
////			Cursor cur=sld.rawQuery(sql, new String[]{"klaus"});
////			//appendMessage("ѧ��\t\t����\t\t����\t\t�༶");
////			while(cur.moveToNext()){
////				if(i != 0)
////					weight += ",";
////				weight += cur.getFloat(1);
////				i++;
////				Log.d("create","query successfully");
////			}
////			cur.close();
////		}catch(Exception e){
////			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
////			Log.d("query",e.toString());
////		}
////		Log.d("weight:",weight);
////		return weight;
//	}
//	public String queryHistory(String username,int flag){
//		String runTime = "";
//		String walkTime = "";
//		String runStep = "";
//		String walkStep = "";
//		int i = 0;
//		try{
//			String sql="select * from user where username1 ==? ";
//			Cursor cur=sld.rawQuery(sql, new String[]{username});
//			//appendMessage("ѧ��\t\t����\t\t����\t\t�༶");
//			while(cur.moveToNext()){
//				if(i != 0)
//				{
//					runTime += ",";
//					walkTime +=  ",";
//					runStep += ",";
//					walkStep += ",";
//				
//				}
//				runTime += cur.getInt(2);
//				walkTime +=  cur.getInt(3);
//				runStep += cur.getInt(4);
//				walkStep += cur.getInt(5);
//				i++;
//				Log.d("create","query successfully");
//			}
//			cur.close();
//		}catch(Exception e){
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
//			Log.d("query",e.toString());
//		}
//		if(flag == 1) return runTime;
//		if(flag == 2) return walkTime;
//		if(flag == 3) return runStep;
//		if(flag == 4) return walkStep;
//		else return null;
//	}
//	
//	public void update(user user){
//		try{
//			String sql="update user set runtime = user.runtime,walktime = user.walktime,runstep=user.runStep,walkstep=user.walkStep where username='user.username',date = user.currentDate";
//			sld.execSQL(sql);
//			//appendMessage("�ɹ����¼�¼");
//		}catch(Exception e){
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();
//		}
//	}
//	
//	public void add(user user){
//		int flag =0;
//		try{
//		String sql="select * from user where username == ? and date == ?";
//		Cursor cur=sld.rawQuery(sql, new String[]{user.username,Integer.toString(user.currentDate)});
//		if(cur.moveToNext())
//			{
//			flag = 1;
//			cur.close();
//			update(user);
//			}
////		else
////			insert(user);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}
//    public void delete(int flag)
//    {
//    	try
//    	{
//    		if(flag == 0)
//        	{
//    			String sql="delete from weight;";
//  	        	sld.execSQL(sql);
//        	}
//    		if(flag == 1)
//    		{
//	        	String sql1="delete from userstatus;";
//	        	sld.execSQL(sql1);
//    	    }
//    	}
//		catch(Exception e)
//		{
//			//Toast.makeText(this, "���ݿ����"+e.toString(), Toast.LENGTH_SHORT).show();;
//		}
//    }
//	public String int2Str(int[] numbers){
//		int len =  numbers.length;
//		String s = "";
//		for(int curr=0;curr<len;curr++)
//			s =s + numbers[curr];// +",";
//		return s;
//		
//	}
//	public int[] str2Int(String s){
//		String[] tmp=s.split(",");
//		int[] numbers = new int[20];
//		for(int curr=0;curr<tmp.length;curr++)
//			numbers[curr]=Integer.parseInt(tmp[curr]);
//		return numbers;
//	}
//}
