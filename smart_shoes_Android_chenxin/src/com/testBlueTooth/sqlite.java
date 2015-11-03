package com.testBlueTooth;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class sqlite {
	SQLiteDatabase sld;
	public void createOrOpenDatabase(){
		try{
			sld=SQLiteDatabase.openDatabase("/data/data/com.testBlueTooth/mydb", null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//appendMessage("数据库已经成功打开");
			//String sql="create table if not exists user(username varchar(20),date integer)";
		    String sql="create table if not exists user(username varchar(20),date integer,runtime integer,walktime integer,runstep integer,walkstep integer)";
			sld.execSQL(sql);
			//appendMessage("数据库已经成功创建");
			Log.d("create","create successfully");
		}catch(Exception e){
			Log.d("create",e.toString());
			//Toast.makeText(this,"数据库错误:"+e.toString(),Toast.LENGTH_SHORT).show();
		}
	}
	public void closeDatabase(){
		try{
			sld.close();
			Log.d("create","close successfully");
		}catch(Exception e){
			//Toast.makeText(this, "数据库错误："+e.toString(),Toast.LENGTH_SHORT).show();
			Log.d("close",e.toString());
		}
	}
	public void insert(user user){
		try{
			
			//String s=int2Str(user.statusTrace);
			//String s1;
			//s = int2Str(str2Int(s));
			//String sql="insert into user values('"+user.username+"',"+user.currentDate+")";
			String sql="insert into user values('"+user.username+"',"+user.currentDate+","+user.runTime+","+user.walkTime+","+user.runStep+","+user.walkStep+")";
			sld.execSQL(sql);
			Log.d("create","issert successfully");
		}catch(Exception e){
			//Toast.makeText(this, "数据库错误"+e.toString(), Toast.LENGTH_SHORT).show();
			Log.d("insert",e.toString());
		}
	}
	public int query(user user){
		int flag = 0;
		try{
			String sql="select * from user where username ==? and date ==?";
			Cursor cur=sld.rawQuery(sql, new String[]{user.username,Integer.toString(user.currentDate)});
			//appendMessage("学号\t\t姓名\t\t年龄\t\t班级");
			while(cur.moveToNext()){
				user.username=cur.getString(0);
				user.currentDate=cur.getInt(1);
				//String ustatus=cur.getString(2);
				user.runTime=cur.getInt(2);
				user.walkTime =  cur.getInt(3);
				user.runStep = cur.getInt(4);
				user.walkStep = cur.getInt(5);
				Log.d("create","query successfully");
				flag =1;
			}
			cur.close();
		}catch(Exception e){
			//Toast.makeText(this, "数据库错误"+e.toString(), Toast.LENGTH_SHORT).show();
			Log.d("query",e.toString());
		}
		return flag;
	}
	public String queryHistory(int flag, String username){
		String s ="";
		int i = 0;
		try{
			String sql="select * from user where username ==? ";
			Cursor cur=sld.rawQuery(sql, new String[]{username});
			//appendMessage("学号\t\t姓名\t\t年龄\t\t班级");
			while(cur.moveToNext()){
				if( i!=0)
					s += ",";
				i++;
				if(flag == 1) 
				s += cur.getInt(2)/60;
				if(flag == 2)
				s +=  cur.getInt(3)/60;
				if(flag == 3)
				s += cur.getInt(4);
				if(flag == 4)
				s += cur.getInt(5);
//				if(flag == 5)
//				s += cur.getInt(6);
				Log.d("create","query successfully");
			}
			cur.close();
		}catch(Exception e){
			//Toast.makeText(this, "数据库错误"+e.toString(), Toast.LENGTH_SHORT).show();
			Log.d("query",e.toString());
		}
		return s;
	}
	public void update(user user){
		try{
			String sql="update user set runtime = user.runtime,walktime = user.walktime,runstep=user.runStep,walkstep=user.walkStep where username='user.username',date = user.currentDate";
			sld.execSQL(sql);
			//appendMessage("成功跟新记录");
		}catch(Exception e){
			//Toast.makeText(this, "数据库错误"+e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void add(user user){
		int flag =0;
		try{
		String sql="select * from user where username == ? and date == ?";
		Cursor cur=sld.rawQuery(sql, new String[]{user.username,Integer.toString(user.currentDate)});
		if(cur.moveToNext())
			{
			flag = 1;
			cur.close();
			update(user);
			}
		else
			insert(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    public void delete()
    {
    	try
    	{
        	String sql="delete from user;";
        	sld.execSQL(sql);
        	//appendMessage("成功删除所有记录！");
    	}
		catch(Exception e)
		{
			//Toast.makeText(this, "数据库错误："+e.toString(), Toast.LENGTH_SHORT).show();;
		}
    }
	public String int2Str(int[] numbers){
		int len =  numbers.length;
		String s = "";
		for(int curr=0;curr<len;curr++)
			s =s + numbers[curr];// +",";
		return s;
		
	}
	public int[] str2Int(String s){
		String[] tmp=s.split(",");
		int[] numbers = new int[20];
		for(int curr=0;curr<tmp.length;curr++)
			numbers[curr]=Integer.parseInt(tmp[curr]);
		return numbers;
	}
}
