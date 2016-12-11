package com.example.zzz;

import org.roboid.robot.Device;
import org.roboid.robot.Robot;
import org.smartrobot.android.RobotActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import kr.robomation.physical.Albert;

public class Controler extends RobotActivity implements OnTouchListener, OnClickListener
{

   int speed = 40;
   ImageButton leftButton, rightButton, forwardButton, backwardButton, speedup, speeddown;
   Device leftWheelDevice, rightWheelDevice;
   TextView text;
   
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_controler);
      
        leftButton = (ImageButton)findViewById(R.id.left);
        rightButton = (ImageButton)findViewById(R.id.right);
        forwardButton = (ImageButton)findViewById(R.id.forward);
        backwardButton = (ImageButton)findViewById(R.id.backward);
        speedup = (ImageButton)findViewById(R.id.speed_up);
        speeddown = (ImageButton)findViewById(R.id.speed_down);
        text = (TextView)findViewById(R.id.text);

        leftButton.setOnTouchListener(this);
        rightButton.setOnTouchListener(this);
        forwardButton.setOnTouchListener(this);
        backwardButton.setOnTouchListener(this);
           
       speedup.setOnClickListener(this);
       speeddown.setOnClickListener(this);
    }

    @Override
    public void onInitialized(Robot robot)
    {
      super.onInitialized(robot);
      leftWheelDevice = robot.findDeviceById(Albert.EFFECTOR_LEFT_WHEEL);
      rightWheelDevice = robot.findDeviceById(Albert.EFFECTOR_RIGHT_WHEEL);
    }
    
    @Override
   public void onClick(View v)
   {
      if(v.getId() == R.id.speed_up)
      {
         if(speed == 100)
         {
            speed = 100;
            text.setText(R.string.ten);
         }
         else
         {
            if(speed == 80)
            {
               text.setText(R.string.ten);
            }
            else if(speed == 60)
            {
               text.setText(R.string.eight);
            }
            else if(speed == 40)
            {
               text.setText(R.string.six);
            }
            else if(speed == 20)
            {
               text.setText(R.string.four);
            }
            else
            {
               text.setText(R.string.two);
            }
            speed = speed + 20;
         }
      }
         
      else if(v.getId() == R.id.speed_down)
      {
         if(speed == 0)
         {
            speed = 0;
            text.setText(R.string.zero);
         }
         else
         {
            if(speed == 20)
            {
               text.setText(R.string.zero);
            }
            else if(speed == 40)
            {
               text.setText(R.string.two);
            }
            else if(speed == 60)
            {
               text.setText(R.string.four);
            }
            else if(speed == 80)
            {
               text.setText(R.string.six);
            }
            else
            {
               text.setText(R.string.eight);
            }
            speed = speed - 20;
         }
      }
   }   
    
    public void onBackPressed() {
       Intent intent = new Intent(this, Main.class);
       startActivity(intent);
    }
   
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch(event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            {
                switch(v.getId())
                {
                case R.id.left:
                    leftWheelDevice.write(-1*speed);
                    rightWheelDevice.write(speed);
                    break;
                case R.id.right:
                    leftWheelDevice.write(speed);
                    rightWheelDevice.write(-1*speed);
                    break;
                case R.id.forward:
                    leftWheelDevice.write(speed);
                    rightWheelDevice.write(speed);
                    break;
                case R.id.backward:
                    leftWheelDevice.write(-1*speed);
                    rightWheelDevice.write(-1*speed);
                    break;
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            leftWheelDevice.write(0);
            rightWheelDevice.write(0);
            break;
        }
        return false;
    }
}