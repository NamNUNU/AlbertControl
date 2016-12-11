package com.example.zzz;

import java.util.ArrayList;
import java.util.Locale;

import org.roboid.robot.Device;
import org.roboid.robot.Robot;
import org.smartrobot.android.RobotActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import kr.robomation.physical.Albert;

public class SpeechToText extends RobotActivity  {

	private TextView txtSpeechInput;
	private ImageButton btnSpeak;
    private Device leftWheelDevice;
    private Device rightWheelDevice;
	private final int REQ_CODE_SPEECH_INPUT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_speech);

		txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);


		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});
	}

	 public void onBackPressed() {
		 Intent intent = new Intent(this, Main.class);
		 startActivity(intent);
	 }

	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}
	
    public void onInitialized(Robot robot)
    {
        super.onInitialized(robot);
        leftWheelDevice = robot.findDeviceById(Albert.EFFECTOR_LEFT_WHEEL);
        rightWheelDevice = robot.findDeviceById(Albert.EFFECTOR_RIGHT_WHEEL);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == RESULT_OK && null != data) {

				String comp1="앞으로";
				String comp2="뒤로";
				String comp3="오른쪽 회전";
				String comp4="왼쪽 회전";
				String comp5="정지";
				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				txtSpeechInput.setText(result.get(0));
				if(comp1.equals(result.get(0))){
					leftWheelDevice.write(50);
	                rightWheelDevice.write(50);
				}
				else if(comp2.equals(result.get(0))){
					leftWheelDevice.write(-50);
	                rightWheelDevice.write(-50);
				}
				else if(comp3.equals(result.get(0))){
					leftWheelDevice.write(70);
	                rightWheelDevice.write(0);
				}
				else if(comp4.equals(result.get(0))){
					leftWheelDevice.write(0);
	                rightWheelDevice.write(70);
				}
				else{
					leftWheelDevice.write(0);
	                rightWheelDevice.write(0);
				}
			}
			break;
		}

		}
	}

}
