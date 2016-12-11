package com.example.zzz;


import org.roboid.robot.Device;
import org.roboid.robot.Robot;
import org.smartrobot.android.RobotActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kr.robomation.physical.Albert;

public class ModeSet extends RobotActivity implements SensorEventListener {
	
    private SensorManager sensorManager;
    private float[] accelerometer = new float[3];
	private Device leftEyeDevice;
    private Device rightEyeDevice;
    private Device leftWheelDevice;
    private Device rightWheelDevice;
    private Device leftProximityDevice;
    private Device rightProximityDevice;
    
    private boolean touchDown1;
    private boolean touchDown2;
    private boolean touchDown3;
    private boolean touchDown4;
    private boolean touchDown5;
    
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_mode);
		
		
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		
		ButtonListener listener = new ButtonListener();
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);
		btn5.setOnClickListener(listener);

	}
	
    public void onSensorChanged(SensorEvent event)
    {
    }
	
	private class ButtonListener implements OnClickListener
	{
		
	public void onClick(View v){
		
				if(v.getId()==R.id.btn1){
	    			touchDown1 = true;
	    			touchDown2 = false;
	    			touchDown3 = false;
	    			touchDown4 = false;
	    			touchDown5 = false;
				}
				else if(v.getId()==R.id.btn2){
					touchDown1 = false;
	    			touchDown2 = true;
	    			touchDown3 = false;
	    			touchDown4 = false;
	    			touchDown5 = false;
				}
				else if(v.getId()==R.id.btn3){
					touchDown1 = false;
	    			touchDown2 = false;
	    			touchDown3 = true;
	    			touchDown4 = false;
	    			touchDown5 = false;				
				}
				else if(v.getId()==R.id.btn4){
					touchDown1 = false;
	    			touchDown2 = false;
	    			touchDown3 = false;
	    			touchDown4 = true;
	    			touchDown5 = false;
				}
				else{
		            touchDown1 = false;
	    			touchDown2 = false;
	    			touchDown3 = false;
	    			touchDown4 = false;
	    			touchDown5 = true;
				}
    		}
	}

	 public void onBackPressed() {
		 Intent intent = new Intent(this, Main.class);
		 startActivity(intent);
	 }

    public void onInitialized(Robot robot)
    {
        super.onInitialized(robot);
        leftWheelDevice = robot.findDeviceById(Albert.EFFECTOR_LEFT_WHEEL);
        rightWheelDevice = robot.findDeviceById(Albert.EFFECTOR_RIGHT_WHEEL);
        leftProximityDevice = robot.findDeviceById(Albert.SENSOR_LEFT_PROXIMITY);
        rightProximityDevice = robot.findDeviceById(Albert.SENSOR_RIGHT_PROXIMITY);
        leftEyeDevice = robot.findDeviceById(Albert.EFFECTOR_LEFT_EYE);
        rightEyeDevice = robot.findDeviceById(Albert.EFFECTOR_RIGHT_EYE);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onExecute()
    {

        if(touchDown1 == true)
        {
        	if(leftProximityDevice.read() > 50){
                leftWheelDevice.write(100);
                rightWheelDevice.write(0);
            }
        	else if(rightProximityDevice.read() > 50){
        		leftWheelDevice.write(0);
                rightWheelDevice.write(100);
        	}
        	else{
        		leftWheelDevice.write(50);
                rightWheelDevice.write(50);
        	}
        }
        else if(touchDown2 == true)
        {
        	if(leftProximityDevice.read() > 50){
                leftWheelDevice.write(0);
                rightWheelDevice.write(100);
            }
        	else if(rightProximityDevice.read() > 50){
        		leftWheelDevice.write(100);
                rightWheelDevice.write(0);
        	}
        	else{
        		leftWheelDevice.write(50);
                rightWheelDevice.write(50);
        	}
        }
        else if(touchDown3 == true)
        {
        	if(leftProximityDevice.read() > 50){
                leftWheelDevice.write(-100);
                rightWheelDevice.write(-0);
            }
        	else if(rightProximityDevice.read() > 50){
        		leftWheelDevice.write(-0);
                rightWheelDevice.write(-100);
        	}
        	else{
        		leftWheelDevice.write(-50);
                rightWheelDevice.write(-50);
        	}
        }
        else if(touchDown4 == true)
        {
        	if(leftProximityDevice.read() > 50){
                leftWheelDevice.write(-0);
                rightWheelDevice.write(-100);
            }
        	else if(rightProximityDevice.read() > 50){
        		leftWheelDevice.write(-100);
                rightWheelDevice.write(-0);
        	}
        	else{
        		leftWheelDevice.write(-50);
                rightWheelDevice.write(-50);
        	}
        }
        else
        {
            leftWheelDevice.write(0);
            rightWheelDevice.write(0);
            return;
        }


    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
