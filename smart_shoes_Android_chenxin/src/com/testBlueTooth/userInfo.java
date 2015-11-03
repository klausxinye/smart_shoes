package com.testBlueTooth;

import java.io.Serializable;

import android.app.Activity;
import android.content.SharedPreferences;

public class userInfo implements Serializable {
public String username;
public float weight;
public int walkTime;
public int runTime;
public int walkStep;
public int runStep;
public int targetWalkTime;
public int targetRunTime;
public int targetWalkStep;
public int targetRunStep;
public int targetEnergy =2300;
public static float adjustFactor;
//static sqliteUserInfo sqlite;
public void initial(){
	//sqlite.createOrOpenDatabase();
	
}
public void saveInSharedPreferences(){
	SharedPreferences formStore;
	//formStore = getSharedPreferences("myPreferences",Activity.MODE_PRIVATE);
}
}
