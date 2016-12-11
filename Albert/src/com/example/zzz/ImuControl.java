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
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import kr.robomation.physical.Albert;

public class ImuControl extends RobotActivity implements SensorEventListener
{
    private TextView textView;
    private SensorManager sensorManager;
    private float[] accelerometer = new float[3];
    private boolean touchDown;
    private Device leftWheelDevice;
    private Device rightWheelDevice;
    private Device leftProximityDevice;
    private Device rightProximityDevice;
    private Device leftEyeDevice;
    private Device rightEyeDevice;
    private Vibrator vibrator;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_imu);

        textView = (TextView)findViewById(R.id.text);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        
        button = (Button)findViewById(R.id.btn);
		
		ButtonListener listener = new ButtonListener();
		button.setOnClickListener(listener);

	}
	
	private class ButtonListener implements OnClickListener
	{
    		@Override
	public void onClick(View v)
    		{
    			if(touchDown==true)
    			touchDown = false;
    			else
    			touchDown = true;
    		}
	}
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
    
	 public void onBackPressed() {
		 Intent intent = new Intent(this, Main.class);
		 startActivity(intent);
	 }
	 
    @Override
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
    public void onSensorChanged(SensorEvent event)
    {
        switch(event.sensor.getType())
        {
        case Sensor.TYPE_ACCELEROMETER:
            accelerometer[0] = event.values[0];
            accelerometer[1] = event.values[1];
            accelerometer[2] = event.values[2];
            textView.setText(event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
            break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
    
    @Override
    public void onExecute()
    {

        int[] green = new int[] { 0, 255, 0 };
        leftEyeDevice.write(green);
        rightEyeDevice.write(green);
        
        if(touchDown == false)
        {
            leftWheelDevice.write(0);
            rightWheelDevice.write(0);
            return;
        }

        int h = (int)(accelerometer[1] * 24);
        if(h > 100) h = 100;
        else if(h < -100) h = -100;

        int v = (int)(accelerometer[0] * 24) - 127;
        if(v > 100) v = 100;
        else if(v < -100) v = -100;

        int leftWheel = -v / 2 + h / 2;
        int rightWheel = -v / 2 - h / 2;

        leftWheelDevice.write(leftWheel);
        rightWheelDevice.write(rightWheel);
        
        if(leftProximityDevice.read() > 50 || rightProximityDevice.read() > 50){
            vibrator.vibrate(50);
            int[] red = new int[] { 255, 0, 0 };
            leftEyeDevice.write(red);
            rightEyeDevice.write(red);
        }

    }
}
