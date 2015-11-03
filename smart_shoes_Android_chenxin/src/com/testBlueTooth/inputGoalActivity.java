package com.testBlueTooth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inputGoalActivity extends Activity {

	EditText tWalkTime,tRunTime,tWalkStep,tRunStep,tEnergy;
	String walkTime,runTime,walkStep,runStep,energy;
	Button btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputgoal);
		tWalkTime = (EditText) this.findViewById(R.id.etTWalkTime);
		tRunTime = (EditText) this.findViewById(R.id.etTRunTime);
		tWalkStep = (EditText) this.findViewById(R.id.etTWalkStep);
		tRunStep = (EditText) this.findViewById(R.id.etTRunStep);
		tEnergy = (EditText) this.findViewById(R.id.etTEnergy);
		btnBack = (Button) this.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new ClickEvent());
		//System.out.println(loadFromSDFile("testfile.txt"));
		//System.Pause("");
	}
	private class ClickEvent implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnBack)
			{
			walkTime = tWalkTime.getText().toString();
			runTime = tRunTime.getText().toString();
			walkStep = tWalkStep.getText().toString();
			runStep = tRunStep.getText().toString();
			energy = tEnergy.getText().toString();
			saveInSharedPreferences();
			Intent intent = new Intent();
			//intent.setClass(inputGoalActivity.this,userInfoActivity.class);
//			intent.putExtra("tWalkTime",walkTime);
//			intent.putExtra("tRunTime",runTime);
//			intent.putExtra("tWalkStep",walkStep);
//			intent.putExtra("tRunStep",runStep);
			intent.setClass(inputGoalActivity.this,userInfoActivity.class);
			startActivity(intent);
			finish();
			}
			
	}
	}
	 public void saveInSharedPreferences(){
			SharedPreferences formStore;
			formStore = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = formStore.edit();
			editor.putInt("targetWalkStep",Integer.parseInt(walkStep));
			editor.putInt("targetRunStep",Integer.parseInt(runStep));
			editor.putInt("targetWalkTime",Integer.parseInt(walkTime));
			editor.putInt("targetRunTime",Integer.parseInt(runTime));
			editor.putInt("targetEnergy",Integer.parseInt(energy));
			//editor.putFloat("adjustFactor", userInfo.adjustFactor);
			editor.commit();
		}
}


